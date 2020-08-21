package com.example.movie_catalog.Database;

import android.net.Uri;
import android.provider.BaseColumns;

public final class MovieDbContract {
    public static final String MV_AUTHORITY = "com.example.dicodingsubmissiondua.movie";

    private static final String SCHEME = "content";

    public static final class MovieColumns implements BaseColumns {
        static final String MOVIE_TABLE_NAME = "moviedb";

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE= "release_date";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String POSTER_PATH = "poster_path";
        public static final String BACKDROP_PATH = "backdrop_path";

        public static final Uri MV_CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(MV_AUTHORITY)
                .appendPath(MOVIE_TABLE_NAME)
                .build();
    }
}
