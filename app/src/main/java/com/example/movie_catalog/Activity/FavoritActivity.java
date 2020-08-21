package com.example.movie_catalog.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.example.movie_catalog.FavoritPagerAdapter;
import com.example.movie_catalog.R;
import com.example.movie_catalog.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FavoritActivity extends AppCompatActivity {

    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);
        setViewPagerFav();
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
        viewPager = findViewById(R.id.view_pager_fav);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_fav);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);
    }

    private void setViewPagerFav(){
        FavoritPagerAdapter favoritPagerAdapter = new FavoritPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager_fav);
        viewPager.setAdapter(favoritPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_fav);
        tabs.setupWithViewPager(viewPager);
        setTitle("FAVORITE");
        getSupportActionBar().setElevation(0);
    }


}
