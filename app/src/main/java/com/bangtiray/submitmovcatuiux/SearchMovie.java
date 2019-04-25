package com.bangtiray.submitmovcatuiux;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bangtiray.submitmovcatuiux.TMDbAPI.AmbilData;
import com.bangtiray.submitmovcatuiux.TMDbAPI.AmbilDataNoProgress;

public class SearchMovie extends AppCompatActivity {
    RecyclerView rv;
    SearchView sv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        rv = (RecyclerView) findViewById(R.id.rcv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        sv = (SearchView) findViewById(R.id.sv);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                AmbilDataNoProgress Ad = new AmbilDataNoProgress(SearchMovie.this, BuildConfig.API_URL_SEARCH + BuildConfig.API_KEY + BuildConfig.API_LANG + BuildConfig.API_QUERY + query, rv);
                Ad.execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                AmbilDataNoProgress Ad = new AmbilDataNoProgress(SearchMovie.this, BuildConfig.API_URL_SEARCH + BuildConfig.API_KEY + BuildConfig.API_LANG + BuildConfig.API_QUERY + newText, rv);
                Ad.execute();
                return false;
            }
        });

    }

}
