package com.example.favoriteapplication;

import android.database.Cursor;

import java.util.ArrayList;

import static com.example.favoriteapplication.db.TvShowContract.TvShowColumns.BACKDROP_PATH;
import static com.example.favoriteapplication.db.TvShowContract.TvShowColumns.ID;
import static com.example.favoriteapplication.db.TvShowContract.TvShowColumns.OVERVIEW;
import static com.example.favoriteapplication.db.TvShowContract.TvShowColumns.POSTER_PATH;
import static com.example.favoriteapplication.db.TvShowContract.TvShowColumns.FIRST_AIR_DATE;
import static com.example.favoriteapplication.db.TvShowContract.TvShowColumns.NAME;
import static com.example.favoriteapplication.db.TvShowContract.TvShowColumns.VOTE_AVERAGE;

public class MappingTvHelper {
    public static ArrayList<TvShow> mapCursorToArrayListTvShow(Cursor notesCursor) {
        ArrayList<TvShow> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(NAME));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(OVERVIEW));
            String date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(FIRST_AIR_DATE));
            String vote = notesCursor.getString(notesCursor.getColumnIndexOrThrow(VOTE_AVERAGE));
            String poster = notesCursor.getString(notesCursor.getColumnIndexOrThrow(POSTER_PATH));
            String backdrop = notesCursor.getString(notesCursor.getColumnIndexOrThrow(BACKDROP_PATH));
            notesList.add(new TvShow(id, title, description, date,vote,poster,backdrop));
        }

        return notesList;
    }
}
