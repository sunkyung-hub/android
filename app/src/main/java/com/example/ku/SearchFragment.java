package com.example.ku;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<NoticeItem> mNoticeList;
    private NoticeAdapter mnoticeAdapter;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recyclerView);

        mNoticeList = new ArrayList<>();
        mnoticeAdapter = new NoticeAdapter(getContext(), mNoticeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mnoticeAdapter);

        db = FirebaseFirestore.getInstance();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 공지사항 리스트를 초기화합니다.
                mNoticeList.clear();

                // 검색어가 비어 있는 경우, 빈 화면을 유지하도록 합니다.
                if (newText.isEmpty()) {
                    mnoticeAdapter.notifyDataSetChanged();
                    return true;
                }

                // 검색어를 공백으로 분리합니다.
                String[] keywords = newText.split(" ");

                // 각 컬렉션에서 검색어를 포함하는 데이터를 가져와서 리스트에 추가합니다.
                for (String keyword : keywords) {
                    searchCollection("학사", keyword);
                    searchCollection("장학", keyword);
                    searchCollection("취창업", keyword);
                    searchCollection("국제교류", keyword);
                    searchCollection("일반", keyword);
                }

                // 어댑터에 데이터 변경을 알립니다.
                mnoticeAdapter.notifyDataSetChanged();

                return true;
            }
        });

        return view;
    }

    private void searchCollection(String collectionPath, String keyword) {
        db.collection(collectionPath)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Firestore 문서를 NoticeItem 객체로 변환하여 리스트에 추가
                                NoticeItem noticeItem = document.toObject(NoticeItem.class);
                                // 공지사항의 제목에 키워드가 포함되어 있는 경우에만 추가
                                if (noticeItem.getTitle().contains(keyword)) {
                                    mNoticeList.add(noticeItem);
                                }
                            }
                            mnoticeAdapter.setNoticeList(mNoticeList); // RecyclerView 업데이트
                            Log.d("Firestore", "검색 결과: " + mNoticeList.size() + "개 항목");
                        } else {
                            // 검색 결과가 없거나 오류가 발생한 경우
                            Toast.makeText(getContext(), "검색 결과가 없거나 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                            Log.e("Firestore", "데이터 가져오기 실패", task.getException());
                        }
                    }
                });
    }

}
