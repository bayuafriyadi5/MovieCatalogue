package com.baytech.submission5.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baytech.submission5.Activity.DetailMovieFavActivity;
import com.baytech.submission5.Adapter.FavAdapter;
import com.baytech.submission5.Model.MovieItem;
import com.baytech.submission5.R;
import com.baytech.submission5.Resources.MovieResponse;
import com.baytech.submission5.db.MovieHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.baytech.submission5.Activity.DetailMovieFavActivity.REQUEST_UPDATE;



public class FavMovieFragment extends BaseFragment implements LoadMoviesFavCallback {


    private ProgressBar loading;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private FavAdapter favAdapter;


    public FavMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_movie, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView favlist;
        favAdapter = new FavAdapter(this);
        favlist = view.findViewById(R.id.recylerview);
        favlist.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        favlist.setHasFixedSize(true);


        final MovieHelper movieHelper = MovieHelper.getInstance(getContext());
        movieHelper.open();

        loading = view.findViewById(R.id.progressBar);

        favlist.setAdapter(favAdapter);

        if (savedInstanceState == null) {
            new LoadMovieAsync(movieHelper, this).execute();
        } else {
            ArrayList<MovieItem> movies = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (movies != null) {
                favAdapter.setListMoviesFav(movies);
            }
        }

        if (savedInstanceState == null) {
            new LoadMovieAsync(movieHelper, this).execute();
        } else {
            ArrayList<MovieItem> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                favAdapter.setListMoviesFav(list);
            }
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, favAdapter.getAllMoviesFav());
    }

    @Override
    public void onFailed(String error) {

    }

    @Override
    public void preExecute() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<MovieItem> movieFavorites) {

        loading.setVisibility(View.INVISIBLE);
        favAdapter.setListMoviesFav(movieFavorites);
    }

    @Override
    public void onSuccess(MovieResponse movieResponse) {

    }


    public static class LoadMovieAsync
            extends AsyncTask<Void, Void, ArrayList<MovieItem>>{

        private final WeakReference<MovieHelper> weakReference;
        private final WeakReference<LoadMoviesFavCallback> weakCallback;

        LoadMovieAsync(MovieHelper movieFavoriteHelper,
                       LoadMoviesFavCallback callback) {
            weakReference = new WeakReference<>(movieFavoriteHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<MovieItem> doInBackground(Void... voids) {
            return weakReference.get().getAll();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItem> movieFavorites) {
            super.onPostExecute(movieFavorites);
            weakCallback.get().postExecute(movieFavorites);
        }

    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {

            if (requestCode == REQUEST_UPDATE) {

                if (resultCode == DetailMovieFavActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(DetailMovieFavActivity.EXTRA_POSITION, 0);
                    favAdapter.removeItem(position);
                }
            }
        }
    }

}
