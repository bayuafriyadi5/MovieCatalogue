package com.baytech.submission5.Fragment;

import androidx.fragment.app.Fragment;

import com.baytech.submission5.Model.MovieItem;
import com.baytech.submission5.Resources.MovieDataSources;
import com.baytech.submission5.Resources.MovieResponse;

import java.util.ArrayList;

public abstract class BaseFragment extends Fragment {

    public static final String KEY_MOVIES = "movies";

    public MovieDataSources getMovieDataSources() {
        return new MovieDataSources();
    }

    public abstract void onFailed(String error);

    public abstract void preExecute();

    public abstract void postExecute(ArrayList<MovieItem> movies);

    public abstract void onSuccess(MovieResponse movieResponse);
}
