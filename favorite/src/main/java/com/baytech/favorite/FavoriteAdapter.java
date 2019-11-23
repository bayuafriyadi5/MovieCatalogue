package com.baytech.favorite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static com.baytech.favorite.DatabaseContract.MovieColumns.MOVIE_ID;
import static com.baytech.favorite.DatabaseContract.MovieColumns.POSTER;
import static com.baytech.favorite.DatabaseContract.MovieColumns.TITLE;
import static com.baytech.favorite.DatabaseContract.getColumnString;

public class FavoriteAdapter extends CursorAdapter {
    private Context mContex;

    public FavoriteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.mContex = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_content_provider, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View itemView, Context context, Cursor cursor) {
        if (cursor != null){
            TextView nametv = itemView.findViewById(R.id.name);
            ImageView img_item_photo = itemView.findViewById(R.id.img_item_photo);

            nametv.setText(getColumnString(cursor,TITLE));
            Glide.with(context).load("http://image.tmdb.org/t/p/w185/"+getColumnString(cursor,POSTER)).into(img_item_photo);
        }
    }
}
