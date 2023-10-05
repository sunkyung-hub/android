package com.example.ku;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>{

    private ArrayList<NoticeItem> mNoticeList;
    private Context mContext;

    public NoticeAdapter(Context context) {
        mContext = context;
        mNoticeList = new ArrayList<>();
    }

    public void setNoticeList(ArrayList<NoticeItem> list) {
        this.mNoticeList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mNoticeList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition(); // getAdapterPosition()을 사용하여 현재 위치 가져오기
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

    @Override
    public int getItemCount() {
        return mNoticeList == null ? 0 : mNoticeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView date;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.item_notice_title);
            date = itemView.findViewById(R.id.item_notice_date);
        }

        void onBind(NoticeItem item){
            title.setText(item.getTitle());

            if (item.getDate() != null) {
                String dateString = item.getDate(); // String 형태의 날짜 정보 가져오기
                date.setText(dateString);
            } else {
                date.setText("날짜 없음");
            }
        }
    }

}
