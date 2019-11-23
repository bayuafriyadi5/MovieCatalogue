package com.baytech.submission5.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baytech.submission5.Model.MovieItem;
import com.baytech.submission5.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private List<MovieItem> items;
    private int rowLayout;
    private Context context;


    public MovieAdapter(List<MovieItem> items) {
        this.items = items;
    }

    public MovieAdapter(List<MovieItem> movies, int rowLayout, Context context) {
        this.items = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    public void refill(List<MovieItem> items) {
        this.items = new ArrayList<>();
        this.items.addAll(items);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new MovieHolder(v);
    }

    private OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieHolder movieHolder, final int i) {
        Glide.with(movieHolder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185/" + items.get(i).getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(movieHolder.img_item_photo);

        movieHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(items.get(movieHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        ImageView img_item_photo;

        MovieHolder(@NonNull View itemView) {
            super(itemView);
            img_item_photo = itemView.findViewById(R.id.img_item_photo);
        }

    }

    public interface OnItemClickCallback {
        void onItemClicked(MovieItem data);
    }
}

