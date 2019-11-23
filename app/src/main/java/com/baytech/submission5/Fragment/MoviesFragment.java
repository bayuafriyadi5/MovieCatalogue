package com.baytech.submission5.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baytech.submission5.Activity.DetailMovieActivity;
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
public class MoviesFragment extends BaseFragment implements MovieDataSourcesCallback {

    private ArrayList<MovieItem> movies = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private ProgressDialog progressDialog;


    public MoviesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieAdapter = new MovieAdapter(movies);

        RecyclerView movieList = view.findViewById(R.id.recylerview);
        movieList.setHasFixedSize(true);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Load Movie...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        movieList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        movieList.setAdapter(movieAdapter);
        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieItem data) {
                Intent i = new Intent(getContext(), DetailMovieActivity.class);
                i.putExtra(DetailMovieActivity.EXTRA_MOVIE,data);
                startActivity(i);
            }
        });

        if (savedInstanceState == null) {
            getMovieDataSources().getMovies(MovieDataSources.MOVIE, this);
        } else {
            movies = savedInstanceState.getParcelableArrayList(KEY_MOVIES);
            movieAdapter.refill(movies);
        }

    }
    @Override
    public void onSuccess(MovieResponse movieResponse) {
        movies = movieResponse.getResults();
        movieAdapter.refill(movies);
        progressDialog.dismiss();
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(getContext() , error, Toast.LENGTH_SHORT).show();
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
