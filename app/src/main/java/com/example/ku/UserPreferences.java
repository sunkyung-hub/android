package com.example.ku;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Set;

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

        //MultiSelectlistPrefernce 푸시알림설정 | 변경 사항 감지
        MultiSelectListPreference pushPreference = findPreference("push");
        if (pushPreference != null) {
            pushPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    // newValue에는 선택한 카테고리 목록이 포함됩니다.
                    Set<String> selectedCategories = (Set<String>) newValue;

                    if (selectedCategories != null && !selectedCategories.isEmpty()) {
                        // 여기에서 푸시 알림 서비스를 호출하고 알림을 표시하세요.
                        displayPushNotification(selectedCategories);
                    }

                    return true; // 변경 사항을 저장하려면 true를 반환합니다.
                }
            });
        }
    }

    private void displayPushNotification(Set<String> selectedCategories) {
        // 여기에서 실제 푸시 알림을 표시하는 코드를 작성하세요.
        // NotificationManager 및 NotificationCompat을 사용하여 알림을 만들고 표시합니다.
    }
}

