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

        //퀵 링크 1 클릭 리스너
        Preference quickLink1 = findPreference("link1");
        quickLink1.setOnPreferenceClickListener(preference->{
            String url = "https://www.kku.ac.kr/mbshome/mbs/wwwkr/index.do";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kku.ac.kr/mbshome/mbs/wwwkr/index.do"));
            startActivity(intent);
            return true;
        });


        Preference quickLink2 = findPreference("link2");
        quickLink2.setOnPreferenceClickListener(preference->{
                String url = "https://dorm.kku.ac.kr/main.do";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dorm.kku.ac.kr/main.do"));
                startActivity(intent);
            return true;
        });

        Preference quickLink3 = findPreference("link3");
        quickLink3.setOnPreferenceClickListener(preference->{
                String url = "https://kis.kku.ac.kr/index.do";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://kis.kku.ac.kr/index.do"));
                startActivity(intent);
            return true;
        });

        Preference quickLink4 = findPreference("link4");
        quickLink4.setOnPreferenceClickListener(preference->{
                String url = "https://lib.kku.ac.kr/#/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://lib.kku.ac.kr/#/"));
                startActivity(intent);
            return true;
        });
    }
}
