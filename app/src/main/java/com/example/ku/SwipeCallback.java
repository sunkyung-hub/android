package com.example.ku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class SwipeCallback extends ItemTouchHelper.SimpleCallback {
    private NoticeAdapter mAdapter;
    private Drawable icon; // 표시할 아이콘을 저장
    private int iconSize; // 아이콘의 크기
    private boolean isArchiveScreen; // 화면이 보관함 화면인지 나타내는 변수
    private Context context;
    private RecyclerView recyclerView;
    private void undoDeleteItemFromArchive(NoticeItem item, int position) {
        // 아이템을 삭제된 상태에서 실행 취소
        copyItemToArchive(item);
        mAdapter.notifyItemChanged(position);
    }

    private void undoCopyItemToArchive(NoticeItem item, int position) {
        // 아이템을 저장된 상태에서 실행 취소
        deleteItemFromArchive(item);
        mAdapter.notifyItemChanged(position);
    }


    public SwipeCallback(NoticeAdapter adapter, Drawable icon, boolean isArchiveScreen, Context context, RecyclerView recyclerView) {
        super(0, ItemTouchHelper.LEFT);
        mAdapter = adapter;
        this.icon = icon;
        this.iconSize = icon.getIntrinsicWidth(); // 아이콘의 크기 설정
        this.isArchiveScreen = isArchiveScreen; // isArchiveScreen 변수 초기화
        this.context = context;
        this.recyclerView = recyclerView;
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
            // Firestore에서 공지사항을 복사하여 보관함으로 추가 또는 삭제
            if (isArchiveScreen) {
                // 보관함 화면인 경우, 삭제 동작 수행
                deleteItemFromArchive(item);
            } else {
                // 공지사항 화면인 경우, 저장 동작 수행
                copyItemToArchive(item);
            }

            // 스와이프를 되돌립니다.
            mAdapter.notifyItemChanged(position);

            // SnackBar 또는 Toast 메시지 표시
            if (isArchiveScreen) {
                Snackbar.make(recyclerView, "아이템이 삭제되었습니다.", Snackbar.LENGTH_LONG)
                        .setAction("실행 취소", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // 삭제 작업 실행 취소
                                undoDeleteItemFromArchive(item, position);
                            }
                        }).show();
            } else {
                Snackbar.make(recyclerView, "아이템이 저장되었습니다.", Snackbar.LENGTH_LONG)
                        .setAction("실행 취소", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // 저장 작업 실행 취소
                                undoCopyItemToArchive(item, position);
                            }
                        }).show();
            }
        }
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < -iconSize * 2) {
                dX = -iconSize * 2; // Swipe 거리 제한
            }

            View itemView = viewHolder.itemView;
            int itemHeight = itemView.getBottom() - itemView.getTop();

            if (dX < 0) {
                // 스와이프 왼쪽으로 갈 때 아이콘을 그립니다.
                // 스와이프 왼쪽으로 갈 때 아이콘을 스와이프 크기의 중간에 표시합니다.
                int iconWidth = icon.getIntrinsicWidth();
                int iconLeft = itemView.getRight() - iconWidth / 2 - iconSize;
                int iconRight = iconLeft + iconSize;
                int iconTop = itemView.getTop() + (itemHeight - icon.getIntrinsicHeight()) / 2;
                int iconBottom = iconTop + icon.getIntrinsicHeight();
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            } else {
                // 스와이프 오른쪽으로 갈 때 아이콘을 그리지 않습니다.
                icon.setBounds(0, 0, 0, 0);
            }

            icon.draw(c);
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
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

        // "order" 필드를 추가하여 저장 순서를 나타냅니다.
        // order는 현재 시간을 사용하도록 수정
        archiveData.put("order", System.currentTimeMillis());

        // 'archives' 컬렉션에서 중복 확인
        db.collection("archives")
                .whereEqualTo("title", item.getTitle())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        // 중복이 없는 경우에만 추가
                        db.collection("archives")
                                .add(archiveData)
                                .addOnSuccessListener(documentReference -> {
                                    // 보관함에 추가한 후의 처리
                                })
                                .addOnFailureListener(e -> {
                                    // 'archives' 컬렉션에 추가하지 못한 경우의 처리
                                });
                    } else {
                        // 중복이 있을 경우 처리
                        Toast.makeText(context, "이미 저장된 공지입니다", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // 중복 확인 실패 시 처리
                });
    }

    // Firestore에서 보관함에서 항목을 삭제하는 코드
    private void deleteItemFromArchive(NoticeItem item) {
        // 'archives' 컬렉션에서 항목을 삭제
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("archives")
                .whereEqualTo("title", item.getTitle()) // 삭제할 항목 식별
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                        snapshot.getReference().delete(); // 항목 삭제
                    }
                })
                .addOnFailureListener(e -> {
                    // 항목 삭제 실패 시 처리
                });
    }
}
