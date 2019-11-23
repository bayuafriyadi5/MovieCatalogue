package com.baytech.submission5.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.baytech.submission5.Model.MovieItem;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.baytech.submission5.db.DatabaseContract.TvColumns.DESCRIPTION;
import static com.baytech.submission5.db.DatabaseContract.TvColumns.POSTER;
import static com.baytech.submission5.db.DatabaseContract.TvColumns.RATING;
import static com.baytech.submission5.db.DatabaseContract.TABLE_TV;
import static com.baytech.submission5.db.DatabaseContract.TvColumns.TV_ID;
import static com.baytech.submission5.db.DatabaseContract.TvColumns.YEAR;
import static com.baytech.submission5.db.DatabaseContract.TvColumns.NAME;

public class TvHelper {
    private static final String DATABASE_TABLE = TABLE_TV;
    private static DatabaseHelper dataBaseHelper;
    private static TvHelper INSTANCE;
    private static SQLiteDatabase database;

    private TvHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static TvHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<MovieItem> getAll() {
        ArrayList<MovieItem> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                TV_ID + " ASC",
                null);
        cursor.moveToFirst();
        MovieItem movieItem;
        if (cursor.getCount() > 0) {
            do {
                movieItem = new MovieItem();
                movieItem.setId(cursor.getString(cursor.getColumnIndexOrThrow(TV_ID)));
                movieItem.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                movieItem.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movieItem.setVoteAverage(cursor.getString(cursor.getColumnIndexOrThrow(RATING)));
                movieItem.setFirst_air_date(cursor.getString(cursor.getColumnIndexOrThrow(YEAR)));
                movieItem.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                arrayList.add(movieItem);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTv (MovieItem movieItem) {
        ContentValues args = new ContentValues();
        args.put(TV_ID, movieItem.getId());
        args.put(NAME, movieItem.getName());
        args.put(DESCRIPTION, movieItem.getOverview());
        args.put(RATING, movieItem.getVoteAverage());
        args.put(YEAR, movieItem.getFirst_air_date());
        args.put(POSTER, movieItem.getPosterPath());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteTv(int id) {
        return database.delete(TABLE_TV, TV_ID + " = '" + id + "'", null);
    }
}
