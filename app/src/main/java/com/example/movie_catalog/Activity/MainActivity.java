package com.example.movie_catalog.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.movie_catalog.FavoritPagerAdapter;
import com.example.movie_catalog.R;
import com.example.movie_catalog.SectionsPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.bottom_menu);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setViewPagerHome();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        else if(item.getItemId() == R.id.notification_setting){
            Intent mIntent = new Intent(this, ReminderMenuActivity.class);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setViewPagerHome(){
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);
    }

    private void setViewPagerFav(){
        FavoritPagerAdapter favoritPagerAdapter = new FavoritPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(favoritPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        setTitle("FAVORITE");
        getSupportActionBar().setElevation(0);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Toast.makeText(getApplicationContext(), "????", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_fav:

                    Intent intent = new Intent(getApplicationContext(), FavoritActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_searchall:
                    Intent intent_s = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(intent_s);
                    return true;
            }
            return false;
        }
    };
}
