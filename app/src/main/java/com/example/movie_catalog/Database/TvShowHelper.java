package com.example.movie_catalog.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.movie_catalog.Model.TvShowDB.Result;
import com.example.movie_catalog.Utils.MappingTvHelper;

import java.util.ArrayList;

import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.BACKDROP_PATH;
import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.FIRST_AIR_DATE;
import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.ID;
import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.NAME;
import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.OVERVIEW;
import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.POSTER_PATH;
import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.TV_SHOW_TABLE_NAME;
import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.VOTE_AVERAGE;

public class TvShowHelper {
    private static final String DATABASE_TABLE = TV_SHOW_TABLE_NAME;
    private static TvShowDbHelper tvShowDatabaseHelper;
    private static TvShowHelper INSTANCE;

    private static SQLiteDatabase database;

    private TvShowHelper(Context context) {
        tvShowDatabaseHelper = new TvShowDbHelper(context);
    }

    public static TvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = tvShowDatabaseHelper.getWritableDatabase();
    }

    public void close() {
        tvShowDatabaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll(){
        return database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                ID + " ASC",
                null);
    }

    public ArrayList<Result> getAllTvShows() {
        ArrayList<Result> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                ID + " ASC",
                null);
        arrayList=MappingTvHelper.mapCursorToArrayListTvShow(cursor);
        cursor.close();
        return arrayList;
    }

    public long insertTvShow(Result result) {
        ContentValues args = new ContentValues();
        args.put(ID, result.getId());
        args.put(NAME, result.getName());
        args.put(OVERVIEW, result.getOverview());
        args.put(FIRST_AIR_DATE, result.getFirstAirDate());
        args.put(VOTE_AVERAGE, result.getVoteAverage());
        args.put(POSTER_PATH, result.getPosterPath());
        args.put(BACKDROP_PATH, result.getBackdropPath());

        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteTvShow(int id) {
        return database.delete(TV_SHOW_TABLE_NAME, ID + " = '" + id + "'", null);
    }
}
