package com.example.ku;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Gong7Fragment extends Fragment {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gong7, container, false);

        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tabLayout);

        // ViewPager2 어댑터 생성
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(getChildFragmentManager(), getLifecycle());
        viewPager.setAdapter(viewPager2Adapter);

        // ViewPager2에서 swipe 비활성화
        viewPager.setUserInputEnabled(false); // Swipe 비활성화

        // TabLayout에 ViewPager2를 연결
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    // 탭의 텍스트 설정
                    switch (position) {
                        case 0:
                            tab.setText("메카트로닉스공학과");
                            break;
                        case 1:
                            tab.setText("컴퓨터공학과");
                            break;
                        case 2:
                            tab.setText("바이오메디컬공학과");
                            break;
                        case 3:
                            tab.setText("녹색기술융합학과");
                            break;
                        case 4:
                            tab.setText("응용화학과");
                            break;
                        // 다른 탭 추가
                    }
                }
        ).attach();

        return view;
    }
}
