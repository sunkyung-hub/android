package com.example.ku;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.LinearLayout; // LinearLayout을 import 추가

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private NoticeAdapter mNoticeAdapter;
    private ArrayList<NoticeItem> mNoticeItems;
    private LinearLayout categoryLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        categoryLayout = view.findViewById(R.id.categoryLayout);
        mNoticeItems = new ArrayList<>();
        mNoticeAdapter = new NoticeAdapter(getContext(), mNoticeItems);
        mRecyclerView.setAdapter(mNoticeAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Firestore 컬렉션 경로 설정
        String[] collectionPaths = {"학사", "장학", "취창업", "국제교류", "일반", "행사"};

        mNoticeItems.clear(); // 기존 데이터 지우기

        // 인덱스 변수를 사용하여 컬렉션 경로 배열을 순차적으로 접근
        int currentIndex = 0;

        // 데이터를 가져오는 메서드 호출
        fetchNoticeData(db, collectionPaths, currentIndex);

        return view;
    }

    private void fetchNoticeData(FirebaseFirestore db, String[] collectionPaths, int currentIndex) {
        if (currentIndex >= collectionPaths.length) {
            // 모든 컬렉션의 데이터를 가져왔을 때 RecyclerView 업데이트
            mNoticeAdapter.setNoticeList(mNoticeItems);
            return;
        }

        String collectionPath = collectionPaths[currentIndex];
        // Firestore 컬렉션에서 데이터 가져오기
        db.collection(collectionPath)
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            NoticeItem noticeItem = document.toObject(NoticeItem.class);
                            if (document.contains("date")) {
                                String dateString = document.getString("date");
                                noticeItem.setDate(dateString);
                            }
                            mNoticeItems.add(noticeItem);
                        }
                        // 다음 컬렉션 경로로 이동
                        fetchNoticeData(db, collectionPaths, currentIndex + 1);
                    } else {
                        Log.e("Firestore", "데이터 가져오기 실패", task.getException());
                    }
                });
    }
}
