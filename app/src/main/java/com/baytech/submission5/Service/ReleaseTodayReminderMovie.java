package com.baytech.submission5.Service;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;

import com.baytech.submission5.Activity.DetailMovieActivity;
import com.baytech.submission5.Model.MovieItem;
import com.baytech.submission5.R;
import com.baytech.submission5.Resources.ApiBuilder;
import com.baytech.submission5.Resources.ApiService;
import com.baytech.submission5.Resources.MovieResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.baytech.submission5.BuildConfig.TMDB_API_KEY;
import static com.baytech.submission5.Service.DailyReminderMovie.CHANNEL_ID;
import static com.baytech.submission5.Service.DailyReminderMovie.CHANNEL_NAME;

public class ReleaseTodayReminderMovie extends BroadcastReceiver {

    public static final String TYPE_ONE_TIME = "OneTimeAlarm";
    public static final String TYPE_REPEATING = "RepeatingAlarm";
    public static final String EXTRA_TYPE = "type";
    private final int NOTIF_ID_ONETIME = 100;
    private final int NOTIF_ID_REPEATING = 101;
    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "channel";
    private ArrayList<MovieItem> movieList = new ArrayList<>();

    public ReleaseTodayReminderMovie() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        Date cal = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String dateToday = dateFormat.format(cal);
        final String type = intent.getStringExtra(EXTRA_TYPE);

        ApiService apiService = ApiBuilder.getClient(context).create(ApiService.class);
        Call<MovieResponse> call = apiService.getRelease(TMDB_API_KEY,dateToday,dateToday);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null){
                        if (response.body().getResults() != null) {
                            int notifId = type.equalsIgnoreCase(TYPE_ONE_TIME) ? NOTIF_ID_ONETIME : NOTIF_ID_REPEATING;
                            List<MovieItem> items = response.body().getResults();
                            for (MovieItem movie : items) {
                                String date = movie.getReleaseDate();
                                if (date.equalsIgnoreCase(dateToday)) {
                                    movieList.add(movie);
                                }
                                showNotification(context, movie,notifId);
                            }

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    private void showNotification(Context context, MovieItem item,int notifyid) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String title = item.getTitle();
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, item);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,notifyid,intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle(context.getString(R.string.Release_movie))
                    .setContentText(title + context.getString(R.string.Release_movie_content))
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setContentIntent(pendingIntent)
                    .setStyle(new NotificationCompat.InboxStyle()
                    .addLine(title + context.getString(R.string.Release_movie_content))
                            .setBigContentTitle(context.getString(R.string.Release_movie))
                    )
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                builder.setChannelId(CHANNEL_ID);
                if (notificationManagerCompat != null) {
                    notificationManagerCompat.createNotificationChannel(channel);
                }
            }

        notificationManagerCompat.notify(notifyid, builder.build());
    }



    public void setRepeatingAlarm(Context context, String type, String time) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReleaseTodayReminderMovie.class);
        intent.putExtra(EXTRA_TYPE, type);

        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) calendar.add(Calendar.DATE, 1);

        int requestCode = NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayReminderMovie.class);

        int requestCode = type.equalsIgnoreCase(TYPE_ONE_TIME) ? NOTIF_ID_ONETIME : NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);

        alarmManager.cancel(pendingIntent);
    }
}
