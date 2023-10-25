package com.example.ku;

import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SwipeCallback extends ItemTouchHelper.SimpleCallback {
    private NoticeAdapter mAdapter;


    public SwipeCallback(NoticeAdapter adapter) {
        super(0, ItemTouchHelper.LEFT);
        mAdapter = adapter;
    }

    //drag&drop -> 설정 x
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        NoticeItem item = mAdapter.getNoticeItem(position);

        if (item != null) {
            // Firestore에서 공지사항을 복사하여 보관함으로 추가
            copyItemToArchive(item);

        }

    }

    public void onChildDraw(
            @NonNull Canvas c,
            @NonNull RecyclerView recyclerView,
            @NonNull RecyclerView.ViewHolder viewHolder,
            float dX, float dY,
            int actionState, boolean isCurrentlyActive
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // Swipe 된 거리가 일정 값 이상인 경우에만 저장표시를 보여줌

            if (Math.abs(dX) > thresholdValue) {
                // 저장표시 보여주는 로직 구현
                // 레이아웃 파일을 inflate하고 표시
                // '저장' 텍스트나 아이콘을 표시
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder){

    }

//     Firestore에서 공지사항을 복사하여 보관함으로 추가하는 코드
    private void copyItemToArchive(NoticeItem item) {
        // 'notices' 컬렉션에서 항목을 복사하여 'archives' 컬렉션에 추가
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // 복사할 데이터 생성
        Map<String, Object> archiveData = new HashMap<>();
        archiveData.put("title", item.getTitle());
        archiveData.put("date", item.getDate());
        archiveData.put("homepageUrl", item.getHomepageUrl());

        // 'archives' 컬렉션에 추가
        db.collection("보관함")
                .add(archiveData)
                .addOnSuccessListener(documentReference -> {
                    // 보관함에 추가한 후의 처리
                })
                .addOnFailureListener(e -> {
                    // 'archives' 컬렉션에 추가하지 못한 경우의 처리
                });
    }
}
