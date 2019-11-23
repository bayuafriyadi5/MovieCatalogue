package com.baytech.submission5.Resources;

import com.baytech.submission5.BuildConfig;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search/multi?api_key="+ BuildConfig.TMDB_API_KEY)
    Call<MovieResponse> getItemSearch(@Query("query") String multi_search);

    @GET("discover/movie")
    Call<MovieResponse> getRelease(@Query("api_key") String apiKey,
                                          @Query("primary_release_date.gte") String dateGte,
                                          @Query("primary_release_date.lte") String dateLte);
}
