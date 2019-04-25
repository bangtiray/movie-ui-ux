package com.bangtiray.submitmovcatuiux.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

import java.util.LinkedList;



/**
 * Created by Ahmad Satiri on 1/25/2018.
 */

public class MovieRecyclerviewAdapter_BACK extends RecyclerView.Adapter<MovieRecyclerviewAdapter_BACK.ViewHolder> {
    private Activity activity;
    private Cursor lists;


    public MovieRecyclerviewAdapter_BACK(Activity activity) {
        this.activity = activity;
    }
//    public LinkedList<ItemFilm> getLists(){
//        return lists;
//    }
//
    public void setList(Cursor lists){
        this.lists=lists;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_movies, parent, false);
        return new ViewHolder(view);
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ItemFilm itemFilm = getItem(position);
        holder._judul.setText(itemFilm.getTitle());
        holder._txt_deskripsi.setText(itemFilm.getOverview());
        holder._txt_release.setText("Release Date " + itemFilm.getRelease_date());
        Picasso.with(activity)
                .load(BuildConfig.API_THUMB + itemFilm.getBackdrop_path())
                .placeholder(R.color.white)
                .into(holder._backdrop);

        Picasso.with(activity)
                .load(BuildConfig.API_THUMB + itemFilm.getPoster_path())
                .placeholder(R.color.white)
                .into(holder._poster);
    }

    private ItemFilm getItem(int position) {
        if(!lists.moveToPosition(position)){
            throw new IllegalStateException("Ivalid Position");

        }
        return new ItemFilm(lists);
    }

    @Override
    public int getItemCount() {
       if(lists == null)return 0;
       return lists.getCount();
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
