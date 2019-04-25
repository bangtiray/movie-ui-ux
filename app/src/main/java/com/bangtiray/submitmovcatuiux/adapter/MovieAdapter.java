package com.bangtiray.submitmovcatuiux.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangtiray.submitmovcatuiux.BuildConfig;
import com.bangtiray.submitmovcatuiux.R;
import com.bangtiray.submitmovcatuiux.pojo.ItemFilm;
import com.bangtiray.submitmovcatuiux.support.DateTime;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Created by humaira on 2/18/2018.
 */

public class MovieAdapter extends
        BaseAdapter {
    private ArrayList<ItemFilm> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<ItemFilm> items) {
        mData = items;
        notifyDataSetChanged();
    }

    public void addItem(final ItemFilm item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    @Override
    public ItemFilm getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_cardview_movies, null);
            holder.backdrop = (ImageView) view.findViewById(R.id.backdrop);
            holder.poster = (ImageView) view.findViewById(R.id.poster);
            holder.title = (TextView) view.findViewById(R.id.judul);
            holder.overview = (TextView) view.findViewById(R.id.txt_deskripsi);
            holder.releaseDate = (TextView) view.findViewById(R.id.txt_release);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.title.setText(mData.get(i).getTitle());
        holder.overview.setText(mData.get(i).getOverview());
        holder.releaseDate.setText(DateTime.getLongDate(mData.get(i).getRelease_date()));
        Glide.with(context)
                .load(BuildConfig.API_THUMB + mData.get(i).getBackdrop_path())
                .apply(new RequestOptions()
                        .placeholder(R.color.colorPrimary)
                        .centerCrop()
                )
                .into(holder.backdrop);

        Glide.with(context)
                .load(BuildConfig.API_THUMB + mData.get(i).getPoster_path())
                .apply(new RequestOptions()
                        .placeholder(R.color.colorPrimary)
                        .centerCrop()
                )
                .into(holder.poster);
        return view;
    }

    private static class ViewHolder {
        ImageView backdrop;
        ImageView poster;
        TextView title;
        TextView overview;
        TextView releaseDate;
    }
}
