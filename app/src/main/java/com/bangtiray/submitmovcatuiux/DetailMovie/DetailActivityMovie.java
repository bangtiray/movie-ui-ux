package com.bangtiray.submitmovcatuiux.DetailMovie;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bangtiray.submitmovcatuiux.BuildConfig;
import com.bangtiray.submitmovcatuiux.R;
import com.bangtiray.submitmovcatuiux.Widget.StackWidget.main.StackWidgetMain;
import com.bangtiray.submitmovcatuiux.db.FavoriteHelper;
import com.bangtiray.submitmovcatuiux.pojo.ItemFilm;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivityMovie extends AppCompatActivity {
    public static String UPDATE = "com.bangtiray.submitmovcatuiux.UPDATE";
    public static String _KODE_FILM = "_kode_film";
    public static String _POSTER_PATH = "_poster_path";
    public static String _BACKDROP_PATH = "_back_drop";
    public static String _OVERVIEW_ = "_overview_";
    public static String _TITLE_ = "_title_";
    public static String _RELEASEDATE_ = "_release_";
    public static String _VOTE_ = "_vote_";
    public static String _LANGUAGE_ = "_language_";
    ImageView _backdrop, _poster;
    TextView _judul, _overview;
    private FavoriteHelper favoriteHelper;
    private ItemFilm itemFilm;
    MaterialFavoriteButton favorite;
    private boolean isSave = false;
    String _TITLE, _RELEASE, _OVERVIEW, _BACKDROP_PATH1, _POSTER_PATH1, _LANGUAGE, _VOTE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _backdrop = (ImageView) findViewById(R.id.backdrop);
        _poster = (ImageView) findViewById(R.id.poster);
        _judul = (TextView) findViewById(R.id.judul);
        _overview = (TextView) findViewById(R.id.overview);

        String Kode_film = getIntent().getStringExtra(_KODE_FILM);
        //Toast.makeText(this, Kode_film, Toast.LENGTH_LONG).show();
        _judul.setText(getIntent().getStringExtra(_TITLE_));
        _overview.setText(getIntent().getStringExtra(_OVERVIEW_));
        _TITLE = getIntent().getStringExtra(_TITLE_);
        _OVERVIEW = getIntent().getStringExtra(_OVERVIEW_);
        _RELEASE = getIntent().getStringExtra(_RELEASEDATE_);
        _BACKDROP_PATH1 = getIntent().getStringExtra(_BACKDROP_PATH);
        _POSTER_PATH1 = getIntent().getStringExtra(_POSTER_PATH);
        _LANGUAGE = getIntent().getStringExtra(_LANGUAGE_);
        _VOTE = getIntent().getStringExtra(_VOTE_);
        Picasso.with(this)
                .load(BuildConfig.API_THUMB + getIntent().getStringExtra(_BACKDROP_PATH))
                .placeholder(R.color.white)
                .into(_backdrop);

        Picasso.with(this)
                .load(BuildConfig.API_THUMB + getIntent().getStringExtra(_POSTER_PATH))
                .placeholder(R.color.white)
                .into(_poster);

        favoriteHelper = new FavoriteHelper(this);
        try {
            favoriteHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        favorite = (MaterialFavoriteButton) findViewById(R.id.fav_now);
        favorite.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {

//                ShowConfirmationMesssage();
//                if (isSave) {
//                    isSave = false;
                    ItemFilm favItemFilm = new ItemFilm();
                    favItemFilm.setTitle(_TITLE);
                    favItemFilm.setRelease_date(_RELEASE);
                    favItemFilm.setOverview(_OVERVIEW);
                    favItemFilm.setBackdrop_path(_BACKDROP_PATH1);
                    favItemFilm.setPoster_path(_POSTER_PATH1);
                    favItemFilm.setLanguange(_LANGUAGE);
                    favItemFilm.setVote_average(_VOTE);

                    favoriteHelper.insert(favItemFilm);
                    Toast.makeText(DetailActivityMovie.this, "Berhasil ditambahkan ke List Favorite Anda", Toast.LENGTH_SHORT).show();
                    Intent updateWidgetIntent = new Intent(DetailActivityMovie.this, StackWidgetMain.class);
                    updateWidgetIntent.setAction(UPDATE);
                    sendBroadcast(updateWidgetIntent);
                   // finish();
//                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (favoriteHelper != null) {
            favoriteHelper.close();
        }
    }


    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        return dateFormat.format(date);
    }

    private void ShowConfirmationMesssage() {
        String dialogMessage = "Apakah anda yakin ingin Menambahkan Film ini Ke list Favorite Anda?";
        String dialogTitle = "Konfirmasi";

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isSave = true;


                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
