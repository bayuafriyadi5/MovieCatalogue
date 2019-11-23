package com.baytech.submission5.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baytech.submission5.Activity.DetailMovieActivity;
import com.baytech.submission5.Adapter.MovieAdapter;
import com.baytech.submission5.Model.MovieItem;
import com.baytech.submission5.R;
import com.baytech.submission5.Resources.ApiBuilder;
import com.baytech.submission5.Resources.ApiService;
import com.baytech.submission5.Resources.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener{

    private SearchView editsearch;
    private MovieAdapter movieAdapter;

    public SearchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        editsearch = view.findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    private void loadMovie() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        final String input_movie = editsearch.getQuery().toString();

        ApiService apiService = ApiBuilder.getClient(getContext()).create(ApiService.class);

        final RecyclerView recyclerView = getActivity().findViewById(R.id.movie_search);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        Call<MovieResponse> call = apiService.getItemSearch(input_movie);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                final List<MovieItem> movies = response.body().getResults();
                movieAdapter = new MovieAdapter(movies);
                recyclerView.setAdapter(movieAdapter);
                progressDialog.dismiss();
                    movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
                        @Override
                        public void onItemClicked(MovieItem data) {
                            Intent i = new Intent(getActivity(), DetailMovieActivity.class);
                            i.putExtra(DetailMovieActivity.EXTRA_MOVIE,data);
                            startActivity(i);
                        }
                    });

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        loadMovie();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
