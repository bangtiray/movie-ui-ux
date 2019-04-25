package com.bangtiray.submitmovcatuiux.pojo;

/**
 * Created by Ahmad Satiri on 1/24/2018.
 */


import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.bangtiray.submitmovcatuiux.db.DatabaseContract;

import org.json.JSONObject;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import com.bangtiray.submitmovcatuiux.db.DatabaseContract.FavColumns;
import static com.bangtiray.submitmovcatuiux.db.DatabaseContract.getColumnString;

public class ItemFilm implements Parcelable {
    private String id;
    private String title;
    private String release_date;
    private String overview;
    private String backdrop_path;
    private String poster_path;
    private String languange;
    private String vote_average;

    public ItemFilm(JSONObject jo) {
        try {
            String id = jo.getString("id");
            String vote = jo.getString("vote_average");
            String Title = jo.getString("title");
            String Release_date = jo.getString("release_date");
            String Overview = jo.getString("overview");
            String Backdrop = jo.getString("backdrop_path");
            String Poster = jo.getString("poster_path");
            String Lang = jo.getString("original_language");
            this.id = id;
            this.title = Title;
            this.release_date = Release_date;
            this.overview = Overview;
            this.backdrop_path = Backdrop;
            this.poster_path = Poster;
            this.languange = Lang;
            this.vote_average = vote;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getLanguange() {
        return languange;
    }

    public void setLanguange(String languange) {
        this.languange = languange;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.release_date);
        dest.writeString(this.overview);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.poster_path);
        dest.writeString(this.languange);
        dest.writeString(this.vote_average);
    }

    public ItemFilm() {

    }

    public ItemFilm(Cursor c){
        this.id = getColumnString(c, _ID);
        this.title=getColumnString(c, FavColumns.TITLE);
        this.release_date=getColumnString(c, FavColumns.RELEASE_DATE);
        this.overview=getColumnString(c, FavColumns.OVERVIEW);
        this.backdrop_path=getColumnString(c, FavColumns.BACKDROP_PATH);
        this.poster_path=getColumnString(c, FavColumns.POSTER_PATH);
        this.languange=getColumnString(c, FavColumns.LANGUAGE);
        this.vote_average=getColumnString(c, FavColumns.VOTE_AVERAGE);

    }
    protected ItemFilm(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.release_date = in.readString();
        this.overview = in.readString();
        this.backdrop_path = in.readString();
        this.poster_path = in.readString();
        this.languange = in.readString();
        this.vote_average = in.readString();
    }



    public static final Parcelable.Creator<ItemFilm> CREATOR = new Parcelable.Creator<ItemFilm>() {
        @Override
        public ItemFilm createFromParcel(Parcel source) {
            return new ItemFilm(source);
        }

        @Override
        public ItemFilm[] newArray(int size) {
            return new ItemFilm[size];
        }
    };
}


