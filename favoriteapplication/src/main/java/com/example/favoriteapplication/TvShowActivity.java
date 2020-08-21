package com.example.favoriteapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.favoriteapplication.adapter.TvShowDbAdapter;
import com.example.favoriteapplication.db.TvShowContract;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TvShowActivity extends AppCompatActivity implements LoadTvCallback {
    private ProgressBar progressBar;
    private RecyclerView rvNotes;
    private TvShowDbAdapter adapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show);

        BottomNavigationView navView = findViewById(R.id.tv_bottom_menu);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        progressBar = findViewById(R.id.tv_progressbar);
        rvNotes = findViewById(R.id.tv_recyclerView);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setHasFixedSize(true);
        adapter = new TvShowDbAdapter();
        rvNotes.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("TvObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler,this);
        getContentResolver().registerContentObserver(TvShowContract.TvShowColumns.TV_CONTENT_URI, true, myObserver);

        if (savedInstanceState == null) {
            new LoadNoteAsync(this, this).execute();
        } else {
            ArrayList<TvShow> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setData(list);
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListNotes());
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<TvShow> notes) {
        progressBar.setVisibility(View.INVISIBLE);
        if (notes.size() > 0) {
            adapter.setData(notes);
        } else {
            adapter.setData(new ArrayList<TvShow>());

        }
    }

    private static class LoadNoteAsync extends AsyncTask<Void, Void, ArrayList<TvShow>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadTvCallback> weakCallback;

        private LoadNoteAsync(Context context, LoadTvCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<TvShow> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(TvShowContract.TvShowColumns.TV_CONTENT_URI, null, null, null, null);
            return MappingTvHelper.mapCursorToArrayListTvShow(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<TvShow> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadNoteAsync(context, (LoadTvCallback) context).execute();

        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
            }
            return false;
        }
    };
}


interface LoadTvCallback {
    void preExecute();
    void postExecute(ArrayList<TvShow> tvshow);
}


