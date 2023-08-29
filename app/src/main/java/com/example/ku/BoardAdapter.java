package com.example.ku;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder>{

    //데이터 리스트
    private List<Board> dataList;

    public BoardAdapter(List<Board> dataList){
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //ViewHolder 객체 생성 후 리턴.
        //작성한 list_item.xml 를 생성하는 부분이라고 생각하시면 됩니다.

        return new BoardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {

        // ViewHolder 가 재활용 될 때 사용되는 메소드
        Board data = dataList.get(position);
        holder.title.setText(data.getTitle());
        holder.contents.setText(data.getContents());
        holder.time.setText(data.getTime());
        holder.writer.setText(data.getWriter());
    }

    @Override
    public int getItemCount() {

        // 전체 데이터의 개수 조회
        return dataList.size();
    }

    public class BoardViewHolder extends RecyclerView.ViewHolder {
        //ViewHolder 에 필요한 데이터들
        private TextView title;
        private TextView contents;
        private TextView time;
        private TextView writer;

        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_board_title);
            contents = itemView.findViewById(R.id.item_board_content);
            time = itemView.findViewById(R.id.item_board_time);
            writer = itemView.findViewById(R.id.item_board_writer);


        }
    }
}