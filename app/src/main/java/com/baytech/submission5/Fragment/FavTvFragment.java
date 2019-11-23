package com.baytech.submission5.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baytech.submission5.Activity.DetailTvFavActivity;
import com.baytech.submission5.Adapter.FavTvAdapter;
import com.baytech.submission5.Model.MovieItem;
import com.baytech.submission5.R;
import com.baytech.submission5.Resources.MovieResponse;
import com.baytech.submission5.db.TvHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.baytech.submission5.Activity.DetailMovieFavActivity.REQUEST_UPDATE;


public class FavTvFragment extends BaseFragment implements LoadMoviesFavCallback {

    private RecyclerView favlist;
    private ProgressBar loading;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private FavTvAdapter favTvAdapter;
    private TvHelper tvHelper;
    private ArrayList<MovieItem> movies = new ArrayList<>();

    public FavTvFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav_tv, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favTvAdapter = new FavTvAdapter(this);
        favlist = view.findViewById(R.id.recylerview);
        favlist.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        favlist.setHasFixedSize(true);

        final TvHelper tvHelper = TvHelper.getInstance(getContext());
        tvHelper.open();

        loading = view.findViewById(R.id.progressBar);

        favlist.setAdapter(favTvAdapter);

        if (savedInstanceState == null) {
            new LoadMovieAsync(tvHelper, this).execute();
        } else {
            ArrayList<MovieItem> movies = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            favTvAdapter.setData(movies);
        }

        if (savedInstanceState == null) {
            new LoadMovieAsync(tvHelper, this).execute();
        } else {
            ArrayList<MovieItem> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                favTvAdapter.setListTVFav(list);
            }
        }

    }
    @Override
    public void onSuccess(MovieResponse movieResponse) {
        loading.setVisibility(View.INVISIBLE);
        movies = movieResponse.getResults();
        favTvAdapter.setListTVFav(movies);
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(getContext() , error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, favTvAdapter.getAllTvFav());
    }

    @Override
    public void preExecute() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<MovieItem> movieFavorites) {

        loading.setVisibility(View.INVISIBLE);
        favTvAdapter.setListTVFav(movieFavorites);
    }



    private static class LoadMovieAsync
            extends AsyncTask<Void, Void, ArrayList<MovieItem>> {

        private final WeakReference<TvHelper> weakReference;
        private final WeakReference<LoadMoviesFavCallback> weakCallback;

        private LoadMovieAsync(TvHelper tvFavoriteHelper,
                               LoadMoviesFavCallback callback) {
            weakReference = new WeakReference<>(tvFavoriteHelper);
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

                if (resultCode == DetailTvFavActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(DetailTvFavActivity.EXTRA_POSITION, 0);
                    favTvAdapter.removeItem(position);
                }
            }
        }
    }

}
