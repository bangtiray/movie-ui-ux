package com.bangtiray.submitmovcatuiux.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ahmad Satiri on 2/2/2018.
 */

public class DatabaseContract {
    public static String TABLE_FAVORITE = "favorite";

    public static final class FavColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String RELEASE_DATE = "release_date";
        public static String OVERVIEW = "overview";
        public static String BACKDROP_PATH = "backdrop_path";
        public static String POSTER_PATH = "poster_path";
        public static String LANGUAGE = "languange";
        public static String VOTE_AVERAGE = "vote_average";
    }

    public static final String AUTHORITY = "com.bangtiray.submitmovcatuiux";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
