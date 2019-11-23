package com.baytech.submission5.Resources;

import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.baytech.submission5.BuildConfig;


public class MovieDataSources {
     public static final String MOVIE = BuildConfig.BASE_URL_TMDB + "now_playing?api_key={apiKey}&language=en-US&page=1";
    public static final String TV = BuildConfig.BASE_URL_TMDB_TV + "popular?api_key={apiKey}&language=en-US&page=1";

    public void getMovies(String movieEndpoint, final MovieDataSourcesCallback callback) {
        AndroidNetworking.get(movieEndpoint)
                .addPathParameter("apiKey", BuildConfig.TMDB_API_KEY)
                .setTag(MovieDataSources.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(MovieResponse.class, new ParsedRequestListener<MovieResponse>() {
                    @Override
                    public void onResponse(MovieResponse response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR", "onError: ", anError);
                        callback.onFailed("Terjadi kesalahan saat menghubungi server");
                    }
                });
    }
}
