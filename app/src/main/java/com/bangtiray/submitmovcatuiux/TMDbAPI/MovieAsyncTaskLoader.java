package com.bangtiray.submitmovcatuiux.TMDbAPI;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.bangtiray.submitmovcatuiux.BuildConfig;
import com.bangtiray.submitmovcatuiux.pojo.ItemFilm;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by humaira on 2/18/2018.
 */

public class MovieAsyncTaskLoader extends AsyncTaskLoader<ArrayList<ItemFilm>> {

    private ArrayList<ItemFilm> mData;
    private boolean Results = false;

    private String Option;

    public MovieAsyncTaskLoader(final Context context, String option) {
        super(context);
        onContentChanged();
        Option = option;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) forceLoad();
        else if (Results) deliverResult(mData);
    }

    @Override
    public void deliverResult(ArrayList<ItemFilm> data) {
        mData = data;
        Results = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (Results) {
            onReleaseResources(mData);
            mData = null;
            Results = false;
        }

    }

    private void onReleaseResources(ArrayList<ItemFilm> mData) {
    }

    @Override
    public ArrayList<ItemFilm> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<ItemFilm> itemFilms = new ArrayList<>();
        String url = BuildConfig.API_URL_OPTION + Option + "?api_key=" + BuildConfig.API_KEY + BuildConfig.API_LANG;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String res = new String(responseBody);
                    JSONObject jo = new JSONObject(res);
                    JSONArray ja = jo.getJSONArray("results");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject joo = ja.getJSONObject(i);
                        ItemFilm f = new ItemFilm(joo);
                        itemFilms.add(f);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return itemFilms;
    }
}
