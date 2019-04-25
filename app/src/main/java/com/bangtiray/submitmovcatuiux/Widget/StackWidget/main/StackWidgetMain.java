package com.bangtiray.submitmovcatuiux.Widget.StackWidget.main;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bangtiray.submitmovcatuiux.DetailMovie.DetailActivityMovie;
import com.bangtiray.submitmovcatuiux.R;
import com.bangtiray.submitmovcatuiux.Widget.StackWidget.services.StackWidgetServices;

/**
 * Implementation of App Widget functionality.
 */
public class StackWidgetMain extends AppWidgetProvider {

    public static final String ACTION = "com.bangtiray.submitmovcatuiux.ACTION";
    public static final String EXTRAS = "com.bangtiray.submitmovcatuiux.EXTRAS";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetServices.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        // CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.stack_widget_main);

        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent toastIntent = new Intent(context, StackWidgetMain.class);
        toastIntent.setAction(StackWidgetMain.ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        //appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.s);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(ACTION)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(EXTRAS, 0);

            Toast.makeText(context, "Now You Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(DetailActivityMovie.UPDATE)) {
            int[] ids = mgr.getAppWidgetIds(new ComponentName(context, StackWidgetMain.class));
            for (int appWidgetId : ids) {

                Intent i = new Intent(context, StackWidgetServices.class);
                i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                i.setData(Uri.parse(i.toUri(Intent.URI_INTENT_SCHEME)));

                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.stack_widget_main);
                views.setRemoteAdapter(R.id.stack_view, i);
                views.setEmptyView(R.id.stack_view, R.id.empty_view);

                mgr.notifyAppWidgetViewDataChanged(ids, R.id.stack_view);
                mgr.updateAppWidget(appWidgetId, views);

            }
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
