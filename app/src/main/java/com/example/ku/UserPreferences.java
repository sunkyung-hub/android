package com.example.ku;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class UserPreferences extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.user_preferences, rootKey);

        // 퀵 링크 1 클릭 리스너
        Preference quickLink1 = findPreference("link1");
        quickLink1.setOnPreferenceClickListener(preference -> {
            openWebPage("https://www.kku.ac.kr/mbshome/mbs/wwwkr/index.do");
            return true;
        });

        Preference quickLink2 = findPreference("link2");
        quickLink2.setOnPreferenceClickListener(preference -> {
            openWebPage("https://dorm.kku.ac.kr/main.do");
            return true;
        });

        Preference quickLink3 = findPreference("link3");
        quickLink3.setOnPreferenceClickListener(preference -> {
            openWebPage("https://kis.kku.ac.kr/index.do");
            return true;
        });

        Preference quickLink4 = findPreference("link4");
        quickLink4.setOnPreferenceClickListener(preference -> {
            openWebPage("https://lib.kku.ac.kr/#/");
            return true;
        });
    }

    private void openWebPage(String url) {
        // 웹 페이지 열기
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
