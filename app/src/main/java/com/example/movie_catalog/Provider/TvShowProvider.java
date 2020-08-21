package com.example.movie_catalog.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.movie_catalog.Database.TvShowHelper;

import static com.example.movie_catalog.Database.MovieDbContract.MV_AUTHORITY;
import static com.example.movie_catalog.Database.TvShowDbContract.TV_AUTHORITY;

public class TvShowProvider extends ContentProvider {
    private static final int TV=1;
    private static final int TV_ID=2;
    private TvShowHelper tvHelper;
    private static final String TV_SHOW_TABLE_NAME="tvshowdb";

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.dicoding.picodiploma.mynotesapp/note
        sUriMatcher.addURI(TV_AUTHORITY, TV_SHOW_TABLE_NAME, TV );
        // content://com.dicoding.picodiploma.mynotesapp/note/id
        sUriMatcher.addURI(MV_AUTHORITY,
                TV_SHOW_TABLE_NAME + "/#",
                TV_ID);
    }

    public TvShowProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        tvHelper=TvShowHelper.getInstance(getContext());
        tvHelper.open();
        return true;    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case TV:
                cursor=tvHelper.queryAll();
                break;
            default:
                cursor=null;
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
