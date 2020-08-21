package com.example.movie_catalog.Widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movie_catalog.Database.MovieHelper;
import com.example.movie_catalog.Database.TvShowHelper;
import com.example.movie_catalog.Model.WidgetModel;
import com.example.movie_catalog.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.movie_catalog.Database.TvShowDbContract.TvShowColumns.POSTER_PATH;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final ArrayList<WidgetModel> widgetModels=new ArrayList<>();
    private final Context mContext;
    private MovieHelper movieHelper;
    private TvShowHelper tvShowHelper;
    private static final String IMG_URL="https://image.tmdb.org/t/p/w185";

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        movieHelper=MovieHelper.getInstance(mContext);
        tvShowHelper=TvShowHelper.getInstance(mContext);
        movieHelper.open();
        movieHelper.open();


    }

    @Override
    public void onDataSetChanged() {
        Cursor mv_cursor=movieHelper.queryAll();
        mv_cursor.moveToFirst();
        WidgetModel result;
        if (mv_cursor.getCount() > 0) {
            do {
                result = new WidgetModel(mv_cursor.getString(mv_cursor.getColumnIndexOrThrow(POSTER_PATH)));
                widgetModels.add(result);
                mv_cursor.moveToNext();

            } while (!mv_cursor.isAfterLast());
        }
        mv_cursor.close();

//        Cursor tv_cursor=tvShowHelper.queryAll();
//        if (tv_cursor.getCount() > 0) {
//            do {
//                result = new WidgetModel(tv_cursor.getString(tv_cursor.getColumnIndexOrThrow(POSTER_PATH_STRING)));
//                widgetModels.add(result);
//                tv_cursor.moveToNext();
//
//            } while (!tv_cursor.isAfterLast());
//        }
//        tv_cursor.close();
    }

    @Override
    public void onDestroy() {
        movieHelper.close();
        tvShowHelper.close();
    }

    @Override
    public int getCount() {
        return widgetModels.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(IMG_URL + widgetModels.get(position).getPosterPath())
                    .apply(new RequestOptions().fitCenter())
                    .submit(800, 550)
                    .get();

            rv.setImageViewBitmap(R.id.imageViewWidget, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(FavFilmWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageViewWidget, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
