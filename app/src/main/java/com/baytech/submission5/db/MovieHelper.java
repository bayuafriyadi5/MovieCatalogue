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
import static com.baytech.submission5.db.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.baytech.submission5.db.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.baytech.submission5.db.DatabaseContract.MovieColumns.POSTER;
import static com.baytech.submission5.db.DatabaseContract.MovieColumns.RATING;
import static com.baytech.submission5.db.DatabaseContract.MovieColumns.TITLE;
import static com.baytech.submission5.db.DatabaseContract.MovieColumns.YEAR;
import static com.baytech.submission5.db.DatabaseContract.TABLE_MOVIE;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    public MovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
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
                MOVIE_ID + "",
                null);
        cursor.moveToFirst();
        MovieItem movieItem;
        if (cursor.getCount() > 0) {
            do {
                movieItem = new MovieItem();
                movieItem.setId(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_ID)));
                movieItem.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movieItem.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movieItem.setVoteAverage(cursor.getString(cursor.getColumnIndexOrThrow(RATING)));
                movieItem.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(YEAR)));
                movieItem.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                arrayList.add(movieItem);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(MovieItem movieItem) {
        ContentValues args = new ContentValues();
        args.put(MOVIE_ID, movieItem.getId());
        args.put(TITLE, movieItem.getTitle());
        args.put(DESCRIPTION, movieItem.getOverview());
        args.put(RATING, movieItem.getVoteAverage());
        args.put(YEAR, movieItem.getReleaseDate());
        args.put(POSTER, movieItem.getPosterPath());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteMovie(int id) {
        return database.delete(TABLE_MOVIE, MOVIE_ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,MOVIE_ID + " = ?"
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
                ,MOVIE_ID + "");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,MOVIE_ID + " = ?", new String[]{id});
    }

}
