package com.example.movie_catalog.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.TV_SHOW_TABLE_NAME;
import com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns;

public class TvShowDbHelper extends SQLiteOpenHelper{

        private static final String TV_SHOW_DATABASE_NAME = "dbtvshow";

        private static final int TV_SHOW_DATABASE_VERSION = 1;

        private static final String SQL_CREATE_TABLE_TV_SHOW = String.format("CREATE TABLE %s" +
                        " (%s TEXT NULL," +
                        " %s TEXT NULL," +
                        " %s TEXT NULL," +
                        " %s TEXT NULL," +
                        " %s TEXT NULL," +
                        " %s TEXT NULL," +
                        " %s TEXT NULL)",
                TV_SHOW_TABLE_NAME,
                TvShowColumns.ID,
                TvShowColumns.NAME,
                TvShowColumns.OVERVIEW,
                TvShowColumns.FIRST_AIR_DATE,
                TvShowColumns.VOTE_AVERAGE,
                TvShowColumns.POSTER_PATH,
                TvShowColumns.BACKDROP_PATH
        );

        TvShowDbHelper(Context context) {
        super(context, TV_SHOW_DATABASE_NAME, null, TV_SHOW_DATABASE_VERSION);
    }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TV_SHOW);
    }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
        */
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TV_SHOW_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
