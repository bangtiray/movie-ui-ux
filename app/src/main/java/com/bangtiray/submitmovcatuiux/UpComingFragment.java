package com.bangtiray.submitmovcatuiux;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bangtiray.submitmovcatuiux.DetailMovie.DetailActivityMovie;
import com.bangtiray.submitmovcatuiux.TMDbAPI.AmbilData;
import com.bangtiray.submitmovcatuiux.TMDbAPI.MovieAsyncTaskLoader;
import com.bangtiray.submitmovcatuiux.adapter.MovieAdapter;
import com.bangtiray.submitmovcatuiux.pojo.ItemFilm;
import com.bangtiray.submitmovcatuiux.support.ItemClickSupport;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<ItemFilm>>, AdapterView.OnItemClickListener{
    private ListView listView;
    private MovieAdapter adapter;

    static final String OPTIONAL= "OPTIONAL";

    Context context;
    public UpComingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listview, container, false);
        context = rootView.getContext();

        adapter = new MovieAdapter(context);
        adapter.notifyDataSetChanged();
        listView=(ListView)rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        Bundle bundle = new Bundle();
        bundle.putString(OPTIONAL,"upcoming");

        getLoaderManager().initLoader(0,bundle,this);
        return rootView;
    }

    @Override
    public Loader<ArrayList<ItemFilm>> onCreateLoader(int id, Bundle args) {
        String option="";
        if(args != null){
            option=args.getString(OPTIONAL);
        }
        return new MovieAsyncTaskLoader(getActivity(),option);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ItemFilm>> loader, ArrayList<ItemFilm> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ItemFilm>> loader) {
        adapter.setData(null);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

        Intent i = new Intent(getActivity(), DetailActivityMovie.class);
        i.putExtra(DetailActivityMovie._KODE_FILM, adapter.getItem(pos).getId());
        i.putExtra(DetailActivityMovie._TITLE_, adapter.getItem(pos).getTitle());
        i.putExtra(DetailActivityMovie._RELEASEDATE_,adapter.getItem(pos).getRelease_date());
        i.putExtra(DetailActivityMovie._OVERVIEW_, adapter.getItem(pos).getOverview());
        i.putExtra(DetailActivityMovie._BACKDROP_PATH, adapter.getItem(pos).getBackdrop_path());
        i.putExtra(DetailActivityMovie._POSTER_PATH, adapter.getItem(pos).getPoster_path());
        i.putExtra(DetailActivityMovie._LANGUAGE_, adapter.getItem(pos).getLanguange());
        i.putExtra(DetailActivityMovie._VOTE_, adapter.getItem(pos).getVote_average());
        getActivity().startActivity(i);

    }
}
