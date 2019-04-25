package com.bangtiray.submitmovcatuiux;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bangtiray.submitmovcatuiux.adapter.MovieRecyclerviewAdapter;
import com.bangtiray.submitmovcatuiux.db.FavoriteHelper;
import com.bangtiray.submitmovcatuiux.pojo.ItemFilm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private static final String HEADER = "HEADER";
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private LinkedList<ItemFilm> list;
    private MovieRecyclerviewAdapter adapter;
    private FavoriteHelper favoriteHelper;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_overflow);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.nav_drawer);

        favoriteHelper = new FavoriteHelper(this);
        try {
            favoriteHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        navigationView.getMenu().getItem(0).setChecked(true);
        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.view_container, new SearchFragment()).commit();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
//                if (item.getItemId() == R.id.nav_item_home) {
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.view_container, new SearchFragment()).commit();
//                    toolbar.setTitle(getResources().getString(R.string.home));
//                }
                if (item.getItemId() == R.id.nav_item_now) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_container, new NowPlayingFragment()).commit();
                    toolbar.setTitle(getResources().getString(R.string.now_playing));
                }
                if (item.getItemId() == R.id.nav_item_upcoming) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_container, new UpComingFragment()).commit();
                    toolbar.setTitle(getResources().getString(R.string.upcoming));
                }
                if (item.getItemId() == R.id.nav_item_search) {


                    Intent i = new Intent(getApplicationContext(), SearchMovie.class);
                    startActivity(i);

                }
                if (item.getItemId() == R.id.action_change_lang) {
                    Intent i = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                    startActivity(i);
                }

                if (item.getItemId() == R.id.action_baca) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view_container, new FavoriteFragment()).commit();
                    toolbar.setTitle(getResources().getString(R.string.test_baca));
                }


                return false;
            }
        });

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        drawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
        // new LoadFavoriteAsync().execute();

        if (savedInstanceState != null) {
            toolbar.setTitle(savedInstanceState.getString(HEADER));
        }
    }

    private class LoadFavoriteAsync extends AsyncTask<Void, Void, ArrayList<ItemFilm>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (list.size() > 0) {
                list.clear();
            }

        }

        @Override
        protected ArrayList<ItemFilm> doInBackground(Void... voids) {
            return favoriteHelper.query();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SetupCatMov.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(HEADER, toolbar.getTitle().toString());
        super.onSaveInstanceState(outState);
    }
}
