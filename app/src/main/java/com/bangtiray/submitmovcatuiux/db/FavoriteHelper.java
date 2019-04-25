package com.bangtiray.submitmovcatuiux.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bangtiray.submitmovcatuiux.pojo.ItemFilm;

import java.sql.SQLException;
import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.bangtiray.submitmovcatuiux.db.DatabaseContract.FavColumns.BACKDROP_PATH;
import static com.bangtiray.submitmovcatuiux.db.DatabaseContract.FavColumns.LANGUAGE;
import static com.bangtiray.submitmovcatuiux.db.DatabaseContract.FavColumns.OVERVIEW;
import static com.bangtiray.submitmovcatuiux.db.DatabaseContract.FavColumns.POSTER_PATH;
import static com.bangtiray.submitmovcatuiux.db.DatabaseContract.FavColumns.RELEASE_DATE;
import static com.bangtiray.submitmovcatuiux.db.DatabaseContract.FavColumns.TITLE;
import static com.bangtiray.submitmovcatuiux.db.DatabaseContract.FavColumns.VOTE_AVERAGE;
import static com.bangtiray.submitmovcatuiux.db.DatabaseContract.TABLE_FAVORITE;

/**
 * Created by Ahmad Satiri on 2/2/2018.
 */

public class FavoriteHelper {
    private static String DATABASE_TABLE = TABLE_FAVORITE;
    private Context c;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context c) {
        this.c = c;
    }
    public FavoriteHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(c);
        database = databaseHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        databaseHelper.close();
    }

    public ArrayList<ItemFilm> query(){
        ArrayList<ItemFilm> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null,_ID +" DESC",null);
        cursor.moveToFirst();
        ItemFilm itemFilm;
        if(cursor.getCount()>0){
            do{
                itemFilm = new ItemFilm();
                itemFilm.setId(cursor.getString(cursor.getColumnIndexOrThrow(_ID)));
                itemFilm.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                itemFilm.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                itemFilm.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                itemFilm.setBackdrop_path(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_PATH)));
                itemFilm.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                itemFilm.setLanguange(cursor.getString(cursor.getColumnIndexOrThrow(LANGUAGE)));
                itemFilm.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));

                arrayList.add(itemFilm);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(ItemFilm itemFilm){
        ContentValues initialValues= new ContentValues();
        initialValues.put(TITLE, itemFilm.getTitle());
        initialValues.put(RELEASE_DATE, itemFilm.getRelease_date());
        initialValues.put(OVERVIEW, itemFilm.getOverview());
        initialValues.put(BACKDROP_PATH, itemFilm.getBackdrop_path());
        initialValues.put(POSTER_PATH, itemFilm.getPoster_path());
        initialValues.put(LANGUAGE, itemFilm.getLanguange());
        initialValues.put(VOTE_AVERAGE, itemFilm.getVote_average());

        return database.insert(DATABASE_TABLE, null,initialValues);
    }
//    public delete(String id){
//        return database.delete(TABLE_FAVORITE, _ID + " = '"+ id+"'", null);
//    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}
