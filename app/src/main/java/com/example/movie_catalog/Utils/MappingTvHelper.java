package com.example.movie_catalog.Utils;

import android.database.Cursor;

import com.example.movie_catalog.Model.TvShowDB.Result;

import java.util.ArrayList;

import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.BACKDROP_PATH;
import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.FIRST_AIR_DATE;
import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.ID;
import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.NAME;
import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.OVERVIEW;
import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.POSTER_PATH;
import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.VOTE_AVERAGE;

public class MappingTvHelper {
    public static ArrayList<Result> mapCursorToArrayListTvShow(Cursor notesCursor) {
        ArrayList<Result> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(NAME));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(OVERVIEW));
            String date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(FIRST_AIR_DATE));
            String vote = notesCursor.getString(notesCursor.getColumnIndexOrThrow(VOTE_AVERAGE));
            String poster = notesCursor.getString(notesCursor.getColumnIndexOrThrow(POSTER_PATH));
            String backdrop = notesCursor.getString(notesCursor.getColumnIndexOrThrow(BACKDROP_PATH));
            notesList.add(new Result(id, title, description, date,vote,poster,backdrop));
        }

        return notesList;
    }
}