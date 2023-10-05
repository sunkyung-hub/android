package com.example.ku;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<NoticeItem> mNoticeList;

    private NoticeAdapter noticeAdapter;

    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recyclerView);

        mNoticeList = new ArrayList<>();
        noticeAdapter = new NoticeAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(noticeAdapter);

        db = FirebaseFirestore.getInstance();

        // 검색어 입력 이벤트 리스너 추가
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 검색어를 전달하여 검색 수행
                searchNotices(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 검색어가 변경될 때마다 호출되는 부분
                return false;
            }
        });

        return view;
    }

    private void searchNotices(String keyword) {
        mNoticeList.clear(); // 검색 결과를 초기화

        // 각 컬렉션에서 검색어를 포함하는 데이터를 가져와서 리스트에 추가
        searchCollection("학사", keyword);
        searchCollection("장학", keyword);
        searchCollection("취창업", keyword);
        searchCollection("국제교류", keyword);
        searchCollection("일반", keyword);
    }

    private void searchCollection(String collectionPath, String keyword) {
        db.collection(collectionPath)
                .whereGreaterThanOrEqualTo("title", keyword) // "title" 필드와 검색어가 포함하는 경우 필터링
                .whereLessThanOrEqualTo("title", keyword + "\uf8ff") // "title" 필드와 검색어가 포함하는 경우 필터링
                .orderBy("title") // "title" 필드를 첫 번째로 정렬
                .orderBy("date", Query.Direction.DESCENDING) // 그 다음에 "date" 필드를 정렬
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NoticeItem noticeItem = document.toObject(NoticeItem.class);
                                mNoticeList.add(noticeItem);
                            }
                            noticeAdapter.notifyDataSetChanged();
                            // Log를 추가하여 검색 결과를 확인합니다.
                            Log.d("Firestore", "검색 결과: " + mNoticeList.size() + "개 항목");
                        } else {
                            Log.e("Firestore", "데이터 가져오기 실패", task.getException());
                        }
                    }
                });

    }



}
