package com.example.ku;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
    private List<Task<QuerySnapshot>> taskList;

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
        taskList = new ArrayList<>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                handleSearchTextChange(query);

                // 검색 버튼을 누르면 키보드를 숨깁니다
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false; // 빈 값을 반환하여 입력 중에는 아무 동작도 하지 않도록 함
            }
        });

        return view;
    }
    private void handleSearchTextChange(String newText) {
        mNoticeList.clear();
        mnoticeAdapter.notifyDataSetChanged();

        if (newText.isEmpty()) {
            mnoticeAdapter.notifyDataSetChanged();
            return;
        }

        String[] keywords = newText.split(" ");

        for (String keyword : keywords) {
            searchCollection("학사", keyword);
            searchCollection("장학", keyword);
            searchCollection("취창업", keyword);
            searchCollection("국제교류", keyword);
            searchCollection("일반", keyword);
            searchCollection("행사", keyword);
            searchCollection("메카트로닉스", keyword);
            searchCollection("컴퓨터공학", keyword);
            searchCollection("바이오메디컬", keyword);
            searchCollection("녹색기술융합", keyword);
            searchCollection("응용화학", keyword);
        }

        mnoticeAdapter.notifyDataSetChanged();
    }

    private void searchCollection(String collectionPath, String keyword) {
        Task<QuerySnapshot> task = db.collection(collectionPath)
                .orderBy("date", Query.Direction.DESCENDING)
                .get();

        task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        NoticeItem noticeItem = document.toObject(NoticeItem.class);
                        if (noticeItem.getTitle().contains(keyword)) {
                            mNoticeList.add(noticeItem);
                        }
                    }
                    mnoticeAdapter.setNoticeList(mNoticeList); // RecyclerView 업데이트
                    Log.d("Firestore", "검색 결과: " + mNoticeList.size() + "개 항목");
                } else {
                    Toast.makeText(getContext(), "검색 결과가 없거나 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("Firestore", "데이터 가져오기 실패", task.getException());
                }
            }
        });

        taskList.add(task);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
