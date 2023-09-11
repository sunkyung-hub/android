package com.example.ku;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Gong1Fragment extends Fragment {

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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gong1);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }



}