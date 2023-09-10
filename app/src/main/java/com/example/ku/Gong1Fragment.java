package com.example.ku;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Gong1Fragment extends Fragment {

    private static final String TAG = "Gong1Fragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("IpHakFragment", "onCreateView: Fragment created");
        return inflater.inflate(R.layout.fragment_gong1, container, false);
    }

    private class WebCrawlingTask extends AsyncTask<Void,Void, String> {

        @Override
        protected String doInBackground(Void... voids){
            try {
                //웹 페이지에서 데이터 크롤링
                Document document = Jsoup.connect("https://www.kku.ac.kr/user/boardList.do?boardId=1489&siteId=wwwkr&id=wwwkr_070102000000").get();
                //필요한 데이터 추출
                String extractedData = document.select(".m_table").text();


                return extractedData;

            } catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            if(result != null){
                //크롤링 결과를 처리하거나 UI에 표시
                Log.d(TAG, "Crawling result: " +result);
            } else {
                Log.e(TAG, "Failed to crawl data.");
            }
        }
    }
}
