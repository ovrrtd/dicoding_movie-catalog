package com.example.movie_catalog.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.movie_catalog.Model.MovieDB.Result;
import com.example.movie_catalog.Utils.MappingMovieHelper;

import java.util.ArrayList;

import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.BACKDROP_PATH;
import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.ID;
import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.MOVIE_TABLE_NAME;
import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.OVERVIEW;
import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.POSTER_PATH;
import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.RELEASE_DATE;
import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.TITLE;
import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.VOTE_AVERAGE;

public class MovieHelper {
    private static final String DATABASE_TABLE = MOVIE_TABLE_NAME;
    private static MovieDbHelper movieDbHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

    private MovieHelper(Context context) {
        movieDbHelper = new MovieDbHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = movieDbHelper.getWritableDatabase();
    }

    public void close() {
        movieDbHelper.close();

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

    public ArrayList<Result> getAllMovies() {
        ArrayList<Result> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                ID + " ASC",
                null);
        arrayList=MappingMovieHelper.mapCursorToArrayList(cursor);
        cursor.close();
        return arrayList;
    }

    public long insertMovie(Result result) {
        ContentValues args = new ContentValues();
        args.put(ID, result.getId());
        args.put(TITLE, result.getTitle());
        args.put(OVERVIEW, result.getOverview());
        args.put(RELEASE_DATE, result.getReleaseDate());
        args.put(VOTE_AVERAGE, result.getVoteAverage());
        args.put(POSTER_PATH, result.getPosterPath());
        args.put(BACKDROP_PATH, result.getBackdropPath());

        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteMovie(int id) {
        return database.delete(MOVIE_TABLE_NAME, ID + " = '" + id + "'", null);
    }
}
