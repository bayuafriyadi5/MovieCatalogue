package com.baytech.submission5.StackWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.baytech.submission5.Model.MovieItem;
import com.baytech.submission5.R;
import com.baytech.submission5.db.MovieHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.baytech.submission5.BuildConfig.URL_POSTER;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
    private ArrayList<MovieItem> list;
    private Context mContext;
    private int mAppWidgetId;
    private MovieHelper favoriteHelper;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        favoriteHelper = new MovieHelper(mContext);
        favoriteHelper.open();
        list = new ArrayList<>();
        list.addAll(favoriteHelper.getAll());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (list.size() == 0){
            Log.d("get count" , "list 0");
        }
        return list.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Bitmap bmp = null;
        try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(URL_POSTER+list.get(position).getPosterPath())
                    .error(new ColorDrawable(mContext.getResources().getColor(R.color.colorPrimary)))
                    .into(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("log", list.get(position).getPosterPath());
        rv.setImageViewBitmap(R.id.imageView, bmp);
        rv.setTextViewText(R.id.banner_text, list.get(position).getTitle());

        Bundle bundle = new Bundle();
        bundle.putInt(ImageBannerWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(bundle);
        rv.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
