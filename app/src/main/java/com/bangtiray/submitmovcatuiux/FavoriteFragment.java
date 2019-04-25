package com.bangtiray.submitmovcatuiux;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bangtiray.submitmovcatuiux.adapter.MovieRecyclerviewAdapter_BACK;
import com.bangtiray.submitmovcatuiux.db.FavoriteHelper;
import com.bangtiray.submitmovcatuiux.pojo.ItemFilm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

import static com.bangtiray.submitmovcatuiux.db.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private RecyclerView rvFavorite;
   // private LinkedList<ItemFilm> list;
    private Cursor list;
    private MovieRecyclerviewAdapter_BACK adapter;
    //private FavoriteHelper favoriteHelper;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout._recyclerview, container, false);
        rvFavorite = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        rvFavorite.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavorite.setItemAnimator(new SlideInLeftAnimator());

        rvFavorite.getItemAnimator().setAddDuration(1000);
        rvFavorite.getItemAnimator().setRemoveDuration(1000);
        rvFavorite.getItemAnimator().setMoveDuration(1000);
        rvFavorite.getItemAnimator().setChangeDuration(1000);


//        favoriteHelper=new FavoriteHelper(getActivity());
//        try {
//            favoriteHelper.open();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

       // list= new LinkedList<>();

        adapter= new MovieRecyclerviewAdapter_BACK(getActivity());
        adapter.setList(list);

        rvFavorite.setAdapter(adapter);

        new LoadAsycn().execute();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class LoadAsycn extends AsyncTask<Void, Void,Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            return getActivity().getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressBar.setVisibility(View.VISIBLE);

//            if (list.size() > 0){
//                list.clear();
//            }
        }

//        @Override
//        protected ArrayList<ItemFilm> doInBackground(Void... voids) {
//            return favoriteHelper.query();
//        }


        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            list = cursor;
            adapter.setList(list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if(favoriteHelper !=null){
//            favoriteHelper.close();
//        }
    }
}
