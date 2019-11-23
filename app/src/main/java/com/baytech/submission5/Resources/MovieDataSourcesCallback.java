package com.baytech.submission5.Resources;

public interface MovieDataSourcesCallback {
    void onSuccess(MovieResponse movieResponse);

    void onFailed(String error);
}
