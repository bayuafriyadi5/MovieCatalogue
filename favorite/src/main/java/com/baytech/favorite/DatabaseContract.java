package com.baytech.favorite;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_MOVIE = "movie";
    static final class MovieColumns implements BaseColumns {
        public static String MOVIE_ID = "_id";
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String YEAR = "date";
        public static String RATING = "rating";
        public static String POSTER = "poster";
    }

    public static final String AUTHORITY = "com.baytech.submission5";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
}
