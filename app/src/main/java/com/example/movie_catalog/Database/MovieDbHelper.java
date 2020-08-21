package com.example.movie_catalog.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.MOVIE_TABLE_NAME;

public class MovieDbHelper extends SQLiteOpenHelper {
    private static final String MOVIE_DATABASE_NAME = "dbmovie";

    private static final int MOVIE_DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s" +
                    " (%s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL," +
                    " %s TEXT NULL)",
            MOVIE_TABLE_NAME,
            MovieDbContract.MovieColumns.ID,
            MovieDbContract.MovieColumns.TITLE,
            MovieDbContract.MovieColumns.OVERVIEW,
            MovieDbContract.MovieColumns.RELEASE_DATE,
            MovieDbContract.MovieColumns.VOTE_AVERAGE,
            MovieDbContract.MovieColumns.POSTER_PATH,
            MovieDbContract.MovieColumns.BACKDROP_PATH
    );

    MovieDbHelper(Context context) {
        super(context, MOVIE_DATABASE_NAME, null, MOVIE_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
