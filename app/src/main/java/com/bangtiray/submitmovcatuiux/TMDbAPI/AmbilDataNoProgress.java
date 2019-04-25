package com.bangtiray.submitmovcatuiux.TMDbAPI;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bangtiray.submitmovcatuiux.DetailMovie.DetailActivityMovie;
import com.bangtiray.submitmovcatuiux.R;
import com.bangtiray.submitmovcatuiux.adapter.MovieRecyclerviewAdapter;
import com.bangtiray.submitmovcatuiux.pojo.ItemFilm;
import com.bangtiray.submitmovcatuiux.support.ItemClickSupport;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * Created by Ahmad Satiri on 1/24/2018.
 */

public class AmbilDataNoProgress extends AsyncTask<Void, Void, String> {

    Context c;
    String URL_API;
    RecyclerView rv;
    ArrayList<ItemFilm> lists;


    public AmbilDataNoProgress(Context c, String URL_API, RecyclerView rv) {
        this.c = c;
        this.URL_API = URL_API;
        this.rv = rv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s == null) {
            Toast.makeText(c, c.getResources().getString(R.string.pesan_error), Toast.LENGTH_LONG).show();
        } else {


            MovieRecyclerviewAdapter adapter = new MovieRecyclerviewAdapter(c, lists);
            rv.setAdapter(adapter);
            ItemClickSupport.addTo(rv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    // Toast.makeText(c,lists.get(position).getId() , Toast.LENGTH_LONG).show();
                    Intent i = new Intent(c, DetailActivityMovie.class);
                    i.putExtra(DetailActivityMovie._KODE_FILM, lists.get(position).getId());
                    i.putExtra(DetailActivityMovie._TITLE_, lists.get(position).getTitle());
                    i.putExtra(DetailActivityMovie._RELEASEDATE_,lists.get(position).getRelease_date());
                    i.putExtra(DetailActivityMovie._OVERVIEW_, lists.get(position).getOverview());
                    i.putExtra(DetailActivityMovie._BACKDROP_PATH, lists.get(position).getBackdrop_path());
                    i.putExtra(DetailActivityMovie._POSTER_PATH, lists.get(position).getPoster_path());
                    i.putExtra(DetailActivityMovie._LANGUAGE_, lists.get(position).getLanguange());
                    i.putExtra(DetailActivityMovie._VOTE_, lists.get(position).getVote_average());
                    c.startActivity(i);
                }
            });

        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.Ambil();
    }

    private String Ambil() {

        lists = new ArrayList<>();
        SyncHttpClient syncHttpClient = new SyncHttpClient();
        syncHttpClient.get(URL_API, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String res = new String(responseBody);
                    JSONObject jo = new JSONObject(res);
                    JSONArray ja = jo.getJSONArray("results");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo_if = ja.getJSONObject(i);
                        ItemFilm itFilms = new ItemFilm(jo_if);
                        lists.add(itFilms);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return String.valueOf(lists);
    }
}
