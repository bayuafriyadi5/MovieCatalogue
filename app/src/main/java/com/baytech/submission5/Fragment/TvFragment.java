package com.baytech.submission5.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baytech.submission5.Activity.DetailTvActivity;
import com.baytech.submission5.Adapter.MovieAdapter;
import com.baytech.submission5.Resources.MovieDataSources;
import com.baytech.submission5.Resources.MovieDataSourcesCallback;
import com.baytech.submission5.Model.MovieItem;
import com.baytech.submission5.Resources.MovieResponse;
import com.baytech.submission5.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends BaseFragment implements MovieDataSourcesCallback {

    private ArrayList<MovieItem> movies = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private ProgressBar loading;


    public TvFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieAdapter = new MovieAdapter(movies);
        loading = view.findViewById(R.id.progressBar);
        RecyclerView movieList = view.findViewById(R.id.recylerview);
        movieList.setHasFixedSize(true);
        loading.setVisibility(View.VISIBLE);
        movieList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        movieList.setAdapter(movieAdapter);
        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieItem data) {
                Intent i = new Intent(getContext(), DetailTvActivity.class);
                i.putExtra(DetailTvActivity.EXTRA_TV,data);
                startActivity(i);
            }
        });

        if (savedInstanceState == null) {
            getMovieDataSources().getMovies(MovieDataSources.TV, this);
        } else {
            movies = savedInstanceState.getParcelableArrayList(KEY_MOVIES);
            movieAdapter.refill(movies);
        }
    }
    @Override
    public void onSuccess(MovieResponse movieResponse) {
        loading.setVisibility(View.INVISIBLE);
        movies = movieResponse.getResults();
        movieAdapter.refill(movies);
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIES, movies);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(ArrayList<MovieItem> movies) {

    }
}
