package com.baytech.submission5.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.baytech.submission5.Model.MovieItem;
import com.baytech.submission5.R;
import com.baytech.submission5.db.MovieHelper;
import com.bumptech.glide.Glide;


public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "";
    private MovieHelper movieHelper;
    public static final String EXTRA_FAV = "extra_movie_favorite";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int RESULT_ADD = 101;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        final TextView tvtitle = findViewById(R.id.name);
        final TextView tvrating = findViewById(R.id.rating);
        final TextView tvyear = findViewById(R.id.year);
        final TextView tvdesc = findViewById(R.id.desc);
        final ImageView tvposter = findViewById(R.id.poster);
        ProgressBar loading = findViewById(R.id.progressBar);
        Button favbtn = findViewById(R.id.addFav);
        position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        loading.setVisibility(View.VISIBLE);

        final MovieItem movies = getIntent().getParcelableExtra(EXTRA_MOVIE);
        final String nametv = movies.getTitle();
        final String desctv = movies.getOverview();
        final String ratingtv = movies.getVoteAverage();
        final String yeartv = movies.getReleaseDate();
        final String postertv = "https://image.tmdb.org/t/p/w185/" + movies.getPosterPath();

        LoadData();

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        tvtitle.setText(nametv);
        tvdesc.setText(desctv);
        tvrating.setText(ratingtv);
        tvyear.setText(yeartv);
        Glide.with(this)
                .load(postertv)
                .into(tvposter);

        loading.setVisibility(View.INVISIBLE);

        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nametv = movies.getTitle();
                final String desctv = movies.getOverview();
                final String ratingtv = movies.getVoteAverage();
                final String yeartv = movies.getReleaseDate();
                final String postertv = "https://image.tmdb.org/t/p/w185/" + movies.getPosterPath();

                tvtitle.setText(nametv);
                tvdesc.setText(desctv);
                tvrating.setText(ratingtv);
                tvyear.setText(yeartv);
                Glide.with(getApplicationContext())
                        .load(postertv)
                        .into(tvposter);

                Intent intent = new Intent();
                intent.putExtra(EXTRA_FAV, movies);
                intent.putExtra(EXTRA_POSITION, position);

                    final long result = movieHelper.insertMovie(movies);
                    if (result > 0) {
                        movies.setId(String.valueOf((int) result));
                        setResult(RESULT_ADD, intent);
                        Toast.makeText(DetailMovieActivity.this, "Data Berhasil di tambah ke Favorite!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(DetailMovieActivity.this, "Data Gagal di tambah ke Favorite!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(DetailMovieActivity.this, "Data Sudah Ada!", Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }

    private void LoadData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }
}
