package com.example.ku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>{

    private ArrayList<NoticeItem> mNoticeList;

    @NonNull
    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.ViewHolder holder, int position) {
        holder.onBind(mNoticeList.get(position));
    }

    public void setNoticeList(ArrayList<NoticeItem> list){
        this.mNoticeList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNoticeList.size();
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
            title.setText(item.getTv_title());
            date.setText(item.getTv_date());
        }
    }

}
