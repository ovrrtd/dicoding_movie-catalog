package com.example.movie_catalog.Database;

import android.net.Uri;
import android.provider.BaseColumns;

public final class TvShowDbContract {
    public static final String TV_AUTHORITY = "com.example.dicodingsubmissiondua.tvshow";
    private static final String SCHEME = "content";


    public static final class TvShowColumns implements BaseColumns {
        public static final String TV_SHOW_TABLE_NAME = "tvshowdb";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String OVERVIEW = "overview";
        public static final String FIRST_AIR_DATE = "first_air_date";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String POSTER_PATH = "poster_path";
        public static final String BACKDROP_PATH= "backdrop_path";

        public static final Uri TV_CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(TV_AUTHORITY)
                .appendPath(TV_SHOW_TABLE_NAME)
                .build();

    }
}
