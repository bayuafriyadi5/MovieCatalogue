package com.baytech.submission5.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baytech.submission5.Activity.DetailMovieFavActivity;
import com.baytech.submission5.Fragment.FavMovieFragment;
import com.baytech.submission5.Model.MovieItem;
import com.baytech.submission5.R;
import com.baytech.submission5.db.CustomOnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.MovieViewHolder> {

    private ArrayList<MovieItem> listMoviesFav = new ArrayList<>();
    private final FavMovieFragment activity;

    public FavAdapter(FavMovieFragment fragment) {
        this.activity = fragment;
    }


    public void setData(ArrayList<MovieItem> items) {
        listMoviesFav.clear();
        listMoviesFav.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_fav, viewGroup, false);
        return new FavAdapter.MovieViewHolder(mView);
    }


    @Override
    public void onBindViewHolder(@NonNull final FavAdapter.MovieViewHolder movieHolder, final int position) {
        final MovieItem p = getAllMoviesFav().get(position);
        String url_image =  "https://image.tmdb.org/t/p/w185/" + p.getPosterPath();
                Glide.with(movieHolder.itemView.getContext())
                        .load(url_image)
                        .apply(new RequestOptions().override(350, 550))
                        .into(movieHolder.img_item_photo);
        movieHolder.nametv.setText(p.getTitle());

       movieHolder.itemView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
           @Override
           public void onItemClicked(View view, int position) {
               Intent intent = new Intent(activity.getContext(), DetailMovieFavActivity.class);
               intent.putExtra(DetailMovieFavActivity.EXTRA_POSITION, position);
               intent.putExtra(DetailMovieFavActivity.EXTRA_FAV, listMoviesFav.get(position));
               activity.startActivityForResult(intent, DetailMovieFavActivity.REQUEST_UPDATE);
           }
       }));
    }

    @Override
    public int getItemCount() {
        return listMoviesFav.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView img_item_photo;
        TextView nametv;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            nametv = itemView.findViewById(R.id.name);
            img_item_photo = itemView.findViewById(R.id.img_item_photo);
        }
    }


    public ArrayList<MovieItem> getAllMoviesFav() {
        return listMoviesFav;
    }

    public void setListMoviesFav(ArrayList<MovieItem> listMoviesFav) {

        if (listMoviesFav.size() > 0) {
            this.listMoviesFav.clear();
        }
        this.listMoviesFav.addAll(listMoviesFav);

        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.listMoviesFav.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listMoviesFav.size());
    }

}

