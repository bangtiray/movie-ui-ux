package com.bangtiray.submitmovcatuiux.DetailMovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangtiray.submitmovcatuiux.BuildConfig;
import com.bangtiray.submitmovcatuiux.R;
import com.bangtiray.submitmovcatuiux.TMDbAPI.GlobalConnection;
import com.bangtiray.submitmovcatuiux.pojo.CallItemDetailByID;
import com.bangtiray.submitmovcatuiux.pojo.ItemDetailByID;
import com.bangtiray.submitmovcatuiux.pojo.ItemFilm;
import com.bangtiray.submitmovcatuiux.support.DateTime;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.NumberFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {
    public static final String PARAM = "param";
    Call<ItemDetailByID> DetailCall;
    private GlobalConnection globalConnection = new GlobalConnection();
    private ItemFilm itemFilm;
    private Gson gson = new Gson();
    ImageView backdrop, poster;
    TextView judul, release_date, overview, popularity, vote_average, vote_count, budget, revenue;
    private static final String JUDUL = "judul";
    private static final String RELEASE_DATE = "release_date";
    private static final String OVERVIEW = "overview";
    private static final String POPULARITY = "popularity";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String VOTE_COUNT= "vote_count";
    private static final String BUDGET = "budget";
    private static final String REVENUE = "revenue";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        backdrop = (ImageView) findViewById(R.id.backdrop);
        poster = (ImageView) findViewById(R.id.poster);
        judul=(TextView)findViewById(R.id.judul);
        release_date=(TextView)findViewById(R.id.release_date);
        overview = (TextView)findViewById(R.id.overview);
        popularity=(TextView)findViewById(R.id.popularity);
        vote_average=(TextView)findViewById(R.id.popularity);
        vote_count=(TextView)findViewById(R.id.vote_count);
        budget=(TextView)findViewById(R.id.budget);
        revenue=(TextView)findViewById(R.id.revenue);
        String json = getIntent().getStringExtra(PARAM);
        itemFilm = gson.fromJson(json, ItemFilm.class);

        getData();
        if (savedInstanceState != null) {
            judul.setText(savedInstanceState.getString(JUDUL));
            release_date.setText(savedInstanceState.getString(RELEASE_DATE));
            overview.setText(savedInstanceState.getString(OVERVIEW));
            popularity.setText(savedInstanceState.getString(POPULARITY));
            vote_average.setText(savedInstanceState.getString(VOTE_AVERAGE));
            vote_count.setText(savedInstanceState.getString(VOTE_COUNT));
            budget.setText(savedInstanceState.getString(BUDGET));
            revenue.setText(savedInstanceState.getString(REVENUE));
        } else {
            getDataFromServer();
        }
    }
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(JUDUL, judul.getText().toString());
        outState.putString(RELEASE_DATE, release_date.getText().toString());
        outState.putString(OVERVIEW, overview.getText().toString());
        outState.putString(POPULARITY, popularity.getText().toString());
        outState.putString(VOTE_AVERAGE, vote_average.getText().toString());
        outState.putString(VOTE_COUNT, vote_count.getText().toString());
        outState.putString(BUDGET, budget.getText().toString());
        outState.putString(REVENUE, revenue.getText().toString());
        super.onSaveInstanceState(outState);
    }
    private void getDataFromServer() {
        loadFromTMDb(String.valueOf(itemFilm.getId()));
    }

    private void loadFromTMDb(String s) {
        DetailCall = globalConnection.getAPI().getDetailById(s,"en-US");
        DetailCall.enqueue(new Callback<ItemDetailByID>() {
            @Override
            public void onResponse(Call<ItemDetailByID> call, Response<ItemDetailByID> response) {
                ItemDetailByID itemDetailByID = response.body();


                judul.setText(itemDetailByID.getTitle());
                release_date.setText(DateTime.getLongDate(itemDetailByID.getRelease_date()));
                overview.setText(itemDetailByID.getOverview());
                popularity.setText(NumberFormat.getInstance().format(itemDetailByID.getPopularity()));
                vote_average.setText(NumberFormat.getInstance().format(itemDetailByID.getVote_average()));
                vote_count.setText(NumberFormat.getInstance().format(itemDetailByID.getVote_count()));
                budget.setText(NumberFormat.getInstance().format(itemDetailByID.getBudget()));
                revenue.setText(NumberFormat.getInstance().format(itemDetailByID.getRevenue()));
            }

            @Override
            public void onFailure(Call<ItemDetailByID> call, Throwable t) {

            }
        });

    }

    private void getData() {
        getSupportActionBar().setTitle(itemFilm.getTitle());
        Glide.with(getApplicationContext())

                .load(BuildConfig.API_THUMB + itemFilm.getBackdrop_path())
                .into(backdrop);
        Glide.with(getApplicationContext())

                .load(BuildConfig.API_THUMB + itemFilm.getPoster_path())
                .into(poster);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

