package com.example.movie_catalog.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.movie_catalog.Fragment.SearchMovieFragment;
import com.example.movie_catalog.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView_search);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setTitle(R.string.bottom_movie);
        }
        if (savedInstanceState == null) {
            fragmentContainer(new SearchMovieFragment());
        }
    }

    private void fragmentContainer(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSearch, fragment);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_mvsearch:
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle(R.string.bottom_movie);
                    fragment=new SearchMovieFragment();
                    fragmentContainer(fragment);
                    return true;
                case R.id.navigation_tvsearch:
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle(R.string.bottom_tv);
                    fragment = new SearchMovieFragment();
                    fragmentContainer(fragment);
                    return true;
            }
            return false;
        }
    };

}
