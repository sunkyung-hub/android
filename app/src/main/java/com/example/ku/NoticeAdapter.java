package com.example.ku;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    private ArrayList<NoticeItem> mNoticeList;
    private ArrayList<NoticeItem> mNoticeListFull; // 전체 공지사항 목록을 유지하기 위한 리스트
    private Context mContext;

    public NoticeItem getNoticeItem(int position) {
        if (position >= 0 && position < mNoticeList.size()) {
            return mNoticeList.get(position);
        }
        return null;
    }

    public NoticeAdapter(Context context, ArrayList<NoticeItem> noticeList) {
        mContext = context;
        mNoticeList = new ArrayList<>();
        mNoticeListFull = new ArrayList<>(noticeList); // 전체 공지사항 목록 초기화
    }

    public void addItem(NoticeItem item) {
        mNoticeList.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(NoticeItem item) {
        mNoticeList.remove(item);
        notifyDataSetChanged();
    }

    public void setNoticeList(ArrayList<NoticeItem> list) {
        this.mNoticeList = list;
        notifyDataSetChanged();
    }

    //RecyclerView에 표시될 각 item 뷰를 위한 ViewHolder 객체 생성, 초기화
    //xml레이아웃 파일 list_item.xml을 사용하여 뷰 생성
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    //ViewHolder 객체와 데이터를 바인딩하여 각 아이템 뷰에 데이터 표시
    //클릭 이벤트를 처리하여 아이템을 클릭했을 때 브라우저를 열어 homepageUrl로 이동
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mNoticeList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    String homepageUrl = mNoticeList.get(clickedPosition).getHomepageUrl();

                    if (homepageUrl != null && !homepageUrl.isEmpty()) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(homepageUrl));
                        mContext.startActivity(browserIntent);
                    }
                }
            }
        });
    }

    //RecyclerView에 표시할 아이템의 총 개수를 반환. mNoticeList의 크기를 반환. 0일경우 아무것도 표시되지 않음
    @Override
    public int getItemCount() {
        return mNoticeList == null ? 0 : mNoticeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_notice_title);
            date = itemView.findViewById(R.id.item_notice_date);
        }

        void onBind(NoticeItem item) {
            title.setText(item.getTitle());

            if (item.getDate() != null) {
                String dateString = item.getDate();
                date.setText(dateString);
            } else {
                date.setText("날짜 없음");
            }
        }
    }

    //getFilter메소드, Filter 클래스는 검색필터를 구현하는 부분.
    public Filter getFilter() {
        return noticeFilter;
    }

    private Filter noticeFilter = new Filter() {
        @Override
        //사용자가 검색어를 입력하면 performFiltering 메서드가 호출되어 검색 결과를 반환
        protected FilterResults performFiltering(CharSequence constraint) {
            List<NoticeItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                // 검색어가 비어 있거나 null인 경우, 전체 목록 반환
                filteredList.addAll(mNoticeListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (NoticeItem item : mNoticeListFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        //publishResults 메서드를 통해 RecyclerView에 업데이트
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mNoticeList.clear();
            mNoticeList.addAll((List<NoticeItem>) results.values);
            notifyDataSetChanged();
        }
    };
}