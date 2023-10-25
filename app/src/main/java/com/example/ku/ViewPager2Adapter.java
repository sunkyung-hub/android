package com.example.ku;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class ViewPager2Adapter extends FragmentStateAdapter {

    public ViewPager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Dan1Fragment();
            case 1:
                return new Dan2Fragment();
            case 2:
                return new Dan3Fragment();
            case 3:
                return new Dan4Fragment();
            case 4:
                return new Dan5Fragment();


            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 5;       // 페이지 수
    }
}