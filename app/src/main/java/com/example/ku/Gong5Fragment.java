package com.example.ku;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.Timestamp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Gong5Fragment extends Fragment {

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        Log.d("HaksaFragment", "onCreateView: Fragment created");
//        return inflater.inflate(R.layout.fragment_gong1, container, false);
//    }

    private RecyclerView mRecyclerView;
    private NoticeAdapter mNoticeAdapter;
    private ArrayList<NoticeItem> mNoticeItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gong5, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mNoticeAdapter = new NoticeAdapter(getContext());
        mRecyclerView.setAdapter(mNoticeAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // DividerItemDecoration 추가
        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        // Firebase Firestore 데이터베이스 초기화
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Firestore 컬렉션 경로 설정 (예: "notices")
        String collectionPath = "일반";

        // Firestore 컬렉션에서 데이터 가져오기
        db.collection(collectionPath)
                .orderBy("order")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mNoticeItems.clear(); // 기존 데이터 지우기
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Firestore 문서를 NoticeItem 객체로 변환하여 리스트에 추가
                            NoticeItem noticeItem = document.toObject(NoticeItem.class);
                            if (document.contains("date")) {
                                String dateString = document.getString("date"); // 문자열로 저장된 날짜 가져오기
                                noticeItem.setDate(dateString);
                            }
                            mNoticeItems.add(noticeItem);
                        }
                        mNoticeAdapter.setNoticeList(mNoticeItems); // RecyclerView 업데이트
                    } else {
                        Log.e("Firestore", "데이터 가져오기 실패", task.getException());
                    }
                });

        // mNoticeItems를 초기화해야 합니다.
        mNoticeItems = new ArrayList<>();

        mNoticeAdapter.setNoticeList(mNoticeItems);

        return view;
    }
}