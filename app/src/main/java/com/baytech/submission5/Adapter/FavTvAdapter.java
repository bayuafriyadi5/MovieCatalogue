package com.baytech.submission5.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baytech.submission5.Activity.DetailTvFavActivity;
import com.baytech.submission5.Fragment.FavTvFragment;
import com.baytech.submission5.Model.MovieItem;
import com.baytech.submission5.R;
import com.baytech.submission5.db.CustomOnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class FavTvAdapter extends RecyclerView.Adapter<FavTvAdapter.MovieViewHolder> {

    private ArrayList<MovieItem> listTVFav = new ArrayList<>();
    private final FavTvFragment activity;

    public FavTvAdapter(FavTvFragment fragment) {
        this.activity = fragment;
    }


    public void setData(ArrayList<MovieItem> items) {
        listTVFav.clear();
        listTVFav.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_fav, viewGroup, false);
        return new FavTvAdapter.MovieViewHolder(mView);
    }


    @Override
    public void onBindViewHolder(@NonNull final FavTvAdapter.MovieViewHolder movieHolder, final int position) {
        final MovieItem p = getAllTvFav().get(position);
        movieHolder.nametv.setText(p.getName());
        Glide.with(movieHolder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185/" + p.getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(movieHolder.img_item_photo);

        movieHolder.itemView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity.getContext(), DetailTvFavActivity.class);
                intent.putExtra(DetailTvFavActivity.EXTRA_POSITION, position);
                intent.putExtra(DetailTvFavActivity.EXTRA_FAV_TV, listTVFav.get(position));
                activity.startActivityForResult(intent, DetailTvFavActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listTVFav.size();
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


    public ArrayList<MovieItem> getAllTvFav() {
        return listTVFav;
    }

    public void setListTVFav(ArrayList<MovieItem> listMoviesFav) {

        if (listMoviesFav.size() > 0) {
            this.listTVFav.clear();
        }
        this.listTVFav.addAll(listMoviesFav);

        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.listTVFav.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listTVFav.size());
    }

}

