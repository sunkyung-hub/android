package com.example.ku;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.Timestamp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Gong5Fragment extends Fragment {

    private RecyclerView mRecyclerView;
    private NoticeAdapter mNoticeAdapter;

    private ArrayList<NoticeItem> mNoticeItems;
    private ProgressBar progressBar; // ProgressBar 추가

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gong1, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar); // ProgressBar 초기화

        // mNoticeItems를 초기화합니다.
        mNoticeItems = new ArrayList<>();
        mNoticeAdapter = new NoticeAdapter(getContext(), mNoticeItems);
        mRecyclerView.setAdapter(mNoticeAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Firebase Firestore 데이터베이스 초기화
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Firestore 컬렉션 경로 설정 (예: "notices")
        String collectionPath = "일반";

        // 데이터를 가져오기 전에 ProgressBar를 표시
        progressBar.setVisibility(View.VISIBLE);

        // Firestore 컬렉션에서 데이터 가져오기
        db.collection(collectionPath)
                .orderBy("date", Query.Direction.DESCENDING)
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
                    // 데이터를 가져온 후 ProgressBar를 숨깁니다.
                    progressBar.setVisibility(View.GONE);
                });

        // Swipe 기능 추가
        // RecyclerView에 Swipe 기능 추가 (보관함 화면임을 나타내는 isArchiveScreen 변수 추가)
        Drawable saveIcon = getResources().getDrawable(R.drawable.ic_bookmark); // 삭제 아이콘 리소스
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeCallback(mNoticeAdapter, saveIcon, false, getContext(), mRecyclerView));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        return view;
    }
}