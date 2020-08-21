package com.example.favoriteapplication;

import android.database.Cursor;

import java.util.ArrayList;

import static com.example.favoriteapplication.db.DatabaseContract.MovieColumns.BACKDROP_PATH;
import static com.example.favoriteapplication.db.DatabaseContract.MovieColumns.ID;
import static com.example.favoriteapplication.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.example.favoriteapplication.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.example.favoriteapplication.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.example.favoriteapplication.db.DatabaseContract.MovieColumns.TITLE;
import static com.example.favoriteapplication.db.DatabaseContract.MovieColumns.VOTE_AVERAGE;

public class MappingHelper {

    public static ArrayList<Movie> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Movie> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TITLE));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(OVERVIEW));
            String date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(RELEASE_DATE));
            String vote = notesCursor.getString(notesCursor.getColumnIndexOrThrow(VOTE_AVERAGE));
            String poster = notesCursor.getString(notesCursor.getColumnIndexOrThrow(POSTER_PATH));
            String backdrop = notesCursor.getString(notesCursor.getColumnIndexOrThrow(BACKDROP_PATH));
            notesList.add(new Movie(id, title, description, date,vote,poster,backdrop));
        }

        return notesList;
    }

}

