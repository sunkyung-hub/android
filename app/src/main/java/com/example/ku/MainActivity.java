package com.example.ku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView mPostRecyclerView;
    private BoardAdapter mAdapter;
    private List<Board> mDatas;

    HomeFragment homeFragment;
    SearchFragment searchFragment;
    //    SettingFragment settingFragment;
    MenuFragment menuFragment;
    SettingFragment settingFragment;
    Gong1Fragment gong1Fragment;
    Gong2Fragment gong2Fragment;
    Gong3Fragment gong3Fragment;
    Gong4Fragment gong4Fragment;
    Gong5Fragment gong5Fragment;
    Gong6Fragment gong6Fragment;
    Gong7Fragment gong7Fragment;
    Gong8Fragment gong8Fragment;
    Gong9Fragment gong9Fragment;
    Gong10Fragment gong10Fragment;
    UserPreferences userPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);

        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        settingFragment = new SettingFragment();
        menuFragment = new MenuFragment();
        settingFragment = new SettingFragment();

        gong1Fragment = new Gong1Fragment();
        gong2Fragment = new Gong2Fragment();
        gong3Fragment = new Gong3Fragment();
        gong4Fragment = new Gong4Fragment();
        gong5Fragment = new Gong5Fragment();
        gong6Fragment = new Gong6Fragment();
        gong7Fragment = new Gong7Fragment();
        gong8Fragment = new Gong8Fragment();
        gong9Fragment = new Gong9Fragment();
        gong10Fragment = new Gong10Fragment();
        userPreferences = new UserPreferences();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containers, homeFragment)
                .commit();

        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
                switch(menuItem.getItemId()){
                    case R.id.nav_one:
                        menuItem.setChecked(true);
                        displayMessage("학사 selected");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong1Fragment)
                                .commit();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_two:
                        menuItem.setChecked(true);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong2Fragment)
                                .commit();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_three:
                        menuItem.setChecked(true);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong3Fragment)
                                .commit();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_four:
                        menuItem.setChecked(true);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong4Fragment)
                                .commit();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_five:
                        menuItem.setChecked(true);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong5Fragment)
                                .commit();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_six:
                        menuItem.setChecked(true);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong6Fragment)
                                .commit();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_seven:
                        menuItem.setChecked(true);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong7Fragment)
                                .commit();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_eight:
                        menuItem.setChecked(true);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong8Fragment)
                                .commit();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_nine:
                        menuItem.setChecked(true);
                        displayMessage("단과대학 selected");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong9Fragment)
                                .commit();
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_ten:
                        menuItem.setChecked(true);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containers, gong10Fragment)
                                .commit();
                        drawerLayout.closeDrawers();
                        return true;

                }
                return false;
            }
        });

        NavigationBarView navigationBarView = findViewById(R.id.bottomNavigationView);
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

        //list_rv is recyclerview id in fragment_gong1
        mPostRecyclerView = findViewById(R.id.list_rv);
        mDatas = new ArrayList<>();
        mDatas.add(new Board("title1", "contents1", "time1", "writer1"));
        mDatas.add(new Board("title2", "contents2", "time2", "writer2"));
        mDatas.add(new Board("title3", "contents3", "time3", "writer3"));
        mDatas.add(new Board("title4", "contents4", "time4", "writer4"));

        mAdapter = new BoardAdapter(mDatas);
        mPostRecyclerView.setAdapter(mAdapter);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void displayMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}