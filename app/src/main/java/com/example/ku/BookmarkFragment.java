package com.example.ku;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class BookmarkFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private NoticeAdapter mNoticeAdapter;
    private ArrayList<NoticeItem> mBookmarkItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mBookmarkItems = new ArrayList<>();
        mNoticeAdapter = new NoticeAdapter(getContext(), mBookmarkItems);
        mRecyclerView.setAdapter(mNoticeAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Firestore 초기화
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Firestore 컬렉션 경로 (예: "archives")
        String collectionPath = "archives";

        // Firestore에서 저장된 공지사항을 검색하는 쿼리 설정
        db.collection(collectionPath)
                .orderBy("order", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mBookmarkItems.clear(); // 기존 데이터 초기화
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            NoticeItem noticeItem = document.toObject(NoticeItem.class);
                            // 검색된 항목을 목록에 추가
                            mBookmarkItems.add(noticeItem);
                        }
                        mNoticeAdapter.setNoticeList(mBookmarkItems); // RecyclerView 업데이트
                    } else {
                        Log.e("Firestore", "데이터 가져오기 실패", task.getException());
                    }
                    // 데이터가 없을 때 사용자에게 알림 표시
                    if (mBookmarkItems.isEmpty()) {
                        // TextView를 표시하고 RecyclerView와 ProgressBar를 숨깁니다.
                        view.findViewById(R.id.noDataTextView).setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                        view.findViewById(R.id.progressBar).setVisibility(View.GONE);
                    } else {
                        // 데이터가 있는 경우 RecyclerView 표시
                        view.findViewById(R.id.noDataTextView).setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        view.findViewById(R.id.progressBar).setVisibility(View.GONE);
                    }
                });

        // RecyclerView에 Swipe 기능 추가 (보관함 화면임을 나타내는 isArchiveScreen 변수 추가)
        Drawable saveIcon = getResources().getDrawable(R.drawable.ic_delete); // 삭제 아이콘 리소스
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeCallback(mNoticeAdapter, saveIcon, true, getContext(), mRecyclerView));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        return view;
    }
}
