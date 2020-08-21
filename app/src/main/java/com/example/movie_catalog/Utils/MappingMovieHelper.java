package com.example.movie_catalog.Utils;

import android.database.Cursor;

import com.example.movie_catalog.Model.MovieDB.Result;

import java.util.ArrayList;
import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.BACKDROP_PATH;
import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.ID;

import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.OVERVIEW;
import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.POSTER_PATH;
import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.RELEASE_DATE;
import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.TITLE;
import static com.example.movie_catalog.Database.MovieDbContract.MovieColumns.VOTE_AVERAGE;

public class MappingMovieHelper {

    public static ArrayList<Result> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Result> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TITLE));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(OVERVIEW));
            String date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(RELEASE_DATE));
            String vote = notesCursor.getString(notesCursor.getColumnIndexOrThrow(VOTE_AVERAGE));
            String poster = notesCursor.getString(notesCursor.getColumnIndexOrThrow(POSTER_PATH));
            String backdrop = notesCursor.getString(notesCursor.getColumnIndexOrThrow(BACKDROP_PATH));
            notesList.add(new Result(id, title, description, date,vote,poster,backdrop));
        }

        return notesList;
    }
}
