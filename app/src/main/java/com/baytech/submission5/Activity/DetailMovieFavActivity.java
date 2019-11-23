package com.baytech.submission5.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.baytech.submission5.Model.MovieItem;
import com.baytech.submission5.R;
import com.baytech.submission5.db.MovieHelper;
import com.bumptech.glide.Glide;



public class DetailMovieFavActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_FAV = "extra_fav";
    public static final String EXTRA_POSITION = "extra_position";
    private MovieHelper movieHelper;
    private MovieItem movieItem;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;
    private TextView tvtitle,tvrating,tvyear,tvdesc;
    private ImageView tvposter;
    private String nametv,ratingtv,desctv,yeartv,postertv;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie_fav);
         tvtitle = findViewById(R.id.name);
         tvrating = findViewById(R.id.rating);
         tvyear = findViewById(R.id.year);
         tvdesc = findViewById(R.id.desc);
         tvposter = findViewById(R.id.poster);
        ProgressBar loading = findViewById(R.id.progressBar);
        Button delfav = findViewById(R.id.delfavdetail);
        delfav.setOnClickListener(this);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieItem = getIntent().getParcelableExtra(EXTRA_FAV);


        loading.setVisibility(View.INVISIBLE);

        if (movieItem != null) {
            LoadData();

        } else {
            movieItem = new MovieItem();
            loading.setVisibility(View.VISIBLE);
        }

    }

    private void LoadData() {
        nametv = movieItem.getTitle();
        desctv = movieItem.getOverview();
        ratingtv = movieItem.getVoteAverage();
        yeartv = movieItem.getReleaseDate();
        postertv = "https://image.tmdb.org/t/p/w185/" + movieItem.getPosterPath();
        tvtitle.setText(nametv);
        tvdesc.setText(desctv);
        tvrating.setText(ratingtv);
        tvyear.setText(yeartv);
        Glide.with(getApplicationContext())
                .load(postertv)
                .into(tvposter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.delfavdetail) {
            showAlertDialog(ALERT_DIALOG_DELETE);
        }
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogMessage;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        if (!isDialogClose) {
            dialogMessage = getString(R.string.notif_question_delete);
            
            alertDialogBuilder
                    .setMessage(dialogMessage)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                                long result = movieHelper.deleteMovie(Integer.parseInt(movieItem.getId()));
                                if (result > 0 ) {
                                    Intent intent = new Intent();
                                    intent.putExtra(EXTRA_POSITION, position);
                                    setResult(RESULT_DELETE, intent);
                                    finish();
                                    Toast.makeText(DetailMovieFavActivity.this, "Data Berhasil di Hapus!", Toast.LENGTH_SHORT).show();
                                } else {
                                    LoadData();
                                    Toast.makeText(DetailMovieFavActivity.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                                }
                        }
                    })
                    .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            finish();
        }
    }
}
