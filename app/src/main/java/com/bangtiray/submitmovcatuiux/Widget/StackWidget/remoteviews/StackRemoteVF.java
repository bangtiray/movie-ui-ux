package com.bangtiray.submitmovcatuiux.Widget.StackWidget.remoteviews;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bangtiray.submitmovcatuiux.BuildConfig;
import com.bangtiray.submitmovcatuiux.R;
import com.bangtiray.submitmovcatuiux.Widget.StackWidget.main.StackWidgetMain;
import com.bangtiray.submitmovcatuiux.db.FavoriteHelper;
import com.bangtiray.submitmovcatuiux.pojo.ItemFilm;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.bangtiray.submitmovcatuiux.db.DatabaseContract.CONTENT_URI;

/**
 * Created by Ahmad Satiri on 2/14/2018.
 */

public class StackRemoteVF implements RemoteViewsService.RemoteViewsFactory {
    private Context c;
    RemoteViews remoteViews;
    private int mAppWidgetId;
    Bitmap bitmap = null;
    private Cursor list;
    ItemFilm film;
    List<ItemFilm> itemFilms = new ArrayList<>();
    private FavoriteHelper database;

    public StackRemoteVF(Context c, Intent i) {
        this.c = c;
        mAppWidgetId = i.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
//       getFavorite();
    }

    private void getFavorite() {
        list = c.getContentResolver().query(CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onDataSetChanged() {
        database = new FavoriteHelper(c);
        //getFavorite();
//        Log.e("StackRemoteViewsFactory", "onDataSetChanged");
//        getFavorite();
        try {
            database.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        itemFilms.addAll(database.query());
        database.close();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return itemFilms.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        film = itemFilms.get(i);
        remoteViews = new RemoteViews(c.getPackageName(), R.layout.item_stack_widget);

        try {
            bitmap = Glide.with(c)
                    .asBitmap()
                    .load(BuildConfig.API_THUMB + film.getBackdrop_path())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        remoteViews.setImageViewBitmap(R.id.backdrop, bitmap);
        remoteViews.setTextViewText(R.id.judul, film.getTitle());
        Bundle bundle = new Bundle();
        bundle.putInt(StackWidgetMain.EXTRAS, i);
        Intent intent = new Intent();
        intent.putExtras(bundle);

        remoteViews.setOnClickFillInIntent(R.id.backdrop, intent);
        return remoteViews;
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
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private ItemFilm getItemFilm(int i) {
        if (!list.moveToPosition(i)) {
            throw new IllegalStateException("Invalid Positions");
        }
        return new ItemFilm(list);
    }
}
