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
import com.baytech.submission5.db.TvHelper;
import com.bumptech.glide.Glide;

public class DetailTvActivity extends AppCompatActivity {

    public static final String EXTRA_TV = "";
    private TvHelper tvHelper;
    public static final String EXTRA_FAV_TV = "extra_movie_favorite";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int RESULT_ADD = 101;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);
        final TextView tvtitle = findViewById(R.id.nametv);
        final TextView tvrating = findViewById(R.id.rating);
        final TextView tvyear = findViewById(R.id.year);
        final TextView tvdesc = findViewById(R.id.desc);
        final ImageView tvposter = findViewById(R.id.poster);
        ProgressBar loading = findViewById(R.id.progressBar);
        Button favbtn = findViewById(R.id.addFav);
        position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        loading.setVisibility(View.VISIBLE);

        final MovieItem movies = getIntent().getParcelableExtra(EXTRA_TV);
        String name_tv = movies.getName();
        String desc_tv = movies.getOverview();
        String rating_tv = movies.getVoteAverage();
        String year_tv = movies.getFirst_air_date();
        String poster_tv = "https://image.tmdb.org/t/p/w185/" + movies.getPosterPath();

        tvHelper = TvHelper.getInstance(getApplicationContext());
        tvHelper.open();

        tvtitle.setText(name_tv);
        tvdesc.setText(desc_tv);
        tvrating.setText(rating_tv);
        tvyear.setText(year_tv);
        Glide.with(this)
                .load(poster_tv)
                .into(tvposter);

        loading.setVisibility(View.INVISIBLE);

        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nametv = movies.getName();
                final String desctv = movies.getOverview();
                final String ratingtv = movies.getVoteAverage();
                final String yeartv = movies.getFirst_air_date();
                final String postertv = movies.getPosterPath();

                tvtitle.setText(nametv);
                tvdesc.setText(desctv);
                tvrating.setText(ratingtv);
                tvyear.setText(yeartv);
                Glide.with(getApplicationContext())
                        .load(postertv)
                        .into(tvposter);

                Intent intent = new Intent();
                intent.putExtra(EXTRA_FAV_TV, movies);
                intent.putExtra(EXTRA_POSITION, position);

                long result = tvHelper.insertTv(movies);
                if (result > 0) {
                    movies.setId(String.valueOf((int) result));
                    setResult(RESULT_ADD, intent);
                    Toast.makeText(DetailTvActivity.this, "Data Berhasil di tambah ke Favorite!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DetailTvActivity.this, "Data Gagal di tambah ke Favorite!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvHelper.close();
    }
}
