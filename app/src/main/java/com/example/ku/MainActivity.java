package com.example.ku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
//    private MenuItem selectedItem;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment;
    SearchFragment searchFragment;
    Gong1Fragment gong1Fragment;
    Gong2Fragment gong2Fragment;
    Gong3Fragment gong3Fragment;
    Gong4Fragment gong4Fragment;
    Gong5Fragment gong5Fragment;
    Gong6Fragment gong6Fragment;
    Gong7Fragment gong7Fragment;
    UserPreferences userPreferences;
    BookmarkFragment bookmarkFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("KURIMI"); // 초기 화면 타이틀 설정


        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();

        gong1Fragment = new Gong1Fragment();
        gong2Fragment = new Gong2Fragment();
        gong3Fragment = new Gong3Fragment();
        gong4Fragment = new Gong4Fragment();
        gong5Fragment = new Gong5Fragment();
        gong6Fragment = new Gong6Fragment();
        gong7Fragment = new Gong7Fragment();
        userPreferences = new UserPreferences();

        bookmarkFragment = new BookmarkFragment();

        // 시작화면을 홈화면으로 설정
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containers, homeFragment)
                .commit();

        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
                // 모든 메뉴 아이템의 체크를 초기화
                // Menu 객체를 사용하여 모든 아이템 순회 setChecked(false)로 설정
                Menu menu = navigationView.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    menu.getItem(i).setChecked(false);
                }

                // 선택한 메뉴 아이템의 배경 색상 변경
                // 메뉴 아이템의 setTitle() 호출, SpannalbeString을 사용하여 배경 색상 설정
                // 아이템 선택시, 정해놓은 색상으로 변하되 볼드체가 됨, 다른 아이템을 고르면 볼드체 해제되며 정해놓은 색상됨.
                SpannableString s = new SpannableString(menuItem.getTitle());
                s.setSpan(new ForegroundColorSpan(Color.BLACK), 0, s.length(), 0);
                menuItem.setTitle(s);

                switch(menuItem.getItemId()){
                    case R.id.nav_one:
                        menuItem.setChecked(true);
                        displayMessage("학사 selected");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong1Fragment)
                                .commit();
                        getSupportActionBar().setTitle("학사");
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_two:
                        menuItem.setChecked(true);
                        displayMessage("장학 selected");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong2Fragment)
                                .commit();
                        getSupportActionBar().setTitle("장학");
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_three:
                        menuItem.setChecked(true);
                        displayMessage("취창업 selected");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong3Fragment)
                                .commit();
                        getSupportActionBar().setTitle("취창업");
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_four:
                        menuItem.setChecked(true);
                        displayMessage("국제교류 selected");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong4Fragment)
                                .commit();
                        getSupportActionBar().setTitle("국제교류");
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_five:
                        menuItem.setChecked(true);
                        displayMessage("일반 selected");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong5Fragment)
                                .commit();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_six:
                        menuItem.setChecked(true);
                        displayMessage("행사 selected");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong6Fragment)
                                .commit();
                    getSupportActionBar().setTitle("행사");
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_seven:
                        menuItem.setChecked(true);
                        displayMessage("과학기술대학 selected");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong7Fragment)
                                .commit();
                        getSupportActionBar().setTitle("과학기술대학");
                        drawerLayout.closeDrawers();
                        return true;

                }
                return false;
            }
        });

        NavigationBarView navigationBarView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home); // "Home" 버튼 선택

        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.menu:
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.containers, menuFragment)
//                                .commit();
                        drawerLayout.openDrawer(GravityCompat.START);
                        return true;
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, homeFragment)
                                .commit();
                        getSupportActionBar().setTitle("KURIMI");
                        return true;
                    case R.id.search:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, searchFragment)
                                .commit();
                        return true;
                    case R.id.setting:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, userPreferences)
                                .commit();
                        return true;
                }
                return false;
            }
        });

    }
    private void displayMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //toolbar 홈화면에 보이게
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //보관함 아이콘 클릭하면 해당 fragment로 이동가능
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.saveButton:
                    getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containers, bookmarkFragment)
                    .commit();
                getSupportActionBar().setTitle("KURIMI");
                    return true;
        }
        return false;
    }
}

