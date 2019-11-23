package com.baytech.submission5.Resources;

import java.util.ArrayList;

import com.baytech.submission5.Model.MovieItem;
import com.google.gson.annotations.SerializedName;

public class MovieResponse {

    @SerializedName("results")
    private ArrayList<MovieItem> results;

    public ArrayList<MovieItem> getResults(){
        return results;
    }
}
