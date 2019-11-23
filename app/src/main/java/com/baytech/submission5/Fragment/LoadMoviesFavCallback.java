package com.baytech.submission5.Fragment;

import com.baytech.submission5.Model.MovieItem;

import java.util.ArrayList;

interface LoadMoviesFavCallback {
    void preExecute();

    void postExecute(ArrayList<MovieItem> movieFavorites);
}
