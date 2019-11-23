package com.baytech.submission5.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_MOVIE = "movie";
    public static String TABLE_TV = "tv";
    static final class MovieColumns implements BaseColumns {
        public static String MOVIE_ID = "_id";
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String YEAR = "date";
        public static String RATING = "rating";
        public static String POSTER = "poster";
    }
    static final class TvColumns implements BaseColumns {
        public static String TV_ID = "tv_id";
        public static String NAME = "name";
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

}
