package com.bangtiray.submitmovcatuiux.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangtiray.submitmovcatuiux.BuildConfig;
import com.bangtiray.submitmovcatuiux.R;
import com.bangtiray.submitmovcatuiux.pojo.ItemFilm;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Ahmad Satiri on 1/25/2018.
 */

public class MovieRecyclerviewAdapter extends RecyclerView.Adapter<MovieRecyclerviewAdapter.ViewHolder> {
    Context c;
    private Activity activity;
    private ArrayList<ItemFilm> lists;


    public MovieRecyclerviewAdapter(Activity activity) {
        this.activity = activity;
    }

    public MovieRecyclerviewAdapter(Context c, ArrayList<ItemFilm> lists) {
        this.lists = lists;
        this.c=c;
    }

    public void setList(ArrayList<ItemFilm> lists){
            this.lists=lists;
    }

    public ArrayList<ItemFilm>getLists(){return lists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_movies, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder._judul.setText(getLists().get(position).getTitle());
        holder._txt_deskripsi.setText(getLists().get(position).getOverview());
        holder._txt_release.setText("Release Date " + getLists().get(position).getRelease_date());
        Picasso.with(c)
                .load(BuildConfig.API_THUMB + getLists().get(position).getBackdrop_path())
                .placeholder(R.color.white)
                .into(holder._backdrop);

        Picasso.with(c)
                .load(BuildConfig.API_THUMB + getLists().get(position).getPoster_path())
                .placeholder(R.color.white)
                .into(holder._poster);
    }

    @Override
    public int getItemCount() {
        return getLists().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView _backdrop, _poster;
        TextView _judul, _txt_deskripsi, _txt_release;

        public ViewHolder(View itemView) {
            super(itemView);

            _backdrop = (ImageView) itemView.findViewById(R.id.backdrop);
            _poster = (ImageView) itemView.findViewById(R.id.poster);
            _judul = (TextView) itemView.findViewById(R.id.judul);
            _txt_deskripsi = (TextView) itemView.findViewById(R.id.txt_deskripsi);
            _txt_release = (TextView) itemView.findViewById(R.id.txt_release);
        }

    }
}
