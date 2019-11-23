package com.baytech.submission5.Activity;

import android.content.Intent;
import android.provider.Settings;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.baytech.submission5.R;
import com.baytech.submission5.Resources.AppPreference;
import com.baytech.submission5.Service.DailyReminderMovie;
import com.baytech.submission5.Service.ReleaseTodayReminderMovie;

import java.util.Objects;


public class SettingsActivity extends AppCompatActivity{

    private DailyReminderMovie dailyReminderMovie;
    private ReleaseTodayReminderMovie releaseTodayReminderMovie;
    private boolean isUpcoming, isDaily;
    private AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Toolbar toolbar = findViewById(R.id.toolbar);

        final LinearLayoutCompat settingLocal = findViewById(R.id.setting_local);
        final SwitchCompat dailySwitch = findViewById(R.id.switch_daily);
        final SwitchCompat upcomingSwitch = findViewById(R.id.switch_upcoming);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);

        dailyReminderMovie = new DailyReminderMovie();
        releaseTodayReminderMovie = new ReleaseTodayReminderMovie();

        appPreference = new AppPreference(this);

        dailySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDaily = dailySwitch.isChecked();
                if (isDaily){
                    dailySwitch.setEnabled(true);
                    appPreference.setDaily(isDaily);
                    dailyReminderMovie.setRepeatingAlarm(SettingsActivity.this, DailyReminderMovie.TYPE_REPEATING,
                            "07:00", getString(R.string.message_notif_daily));
                }else {
                    dailySwitch.setChecked(false);
                    appPreference.setDaily(isDaily);
                    dailyReminderMovie.cancelAlarm(SettingsActivity.this, DailyReminderMovie.TYPE_REPEATING);
                }
            }
        });

        upcomingSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUpcoming = upcomingSwitch.isChecked();
                if (isUpcoming){
                    upcomingSwitch.setEnabled(true);
                    appPreference.setUpcoming(isUpcoming);
                    releaseTodayReminderMovie.setRepeatingAlarm(SettingsActivity.this, ReleaseTodayReminderMovie.TYPE_REPEATING,
                            "10:03");

                    Toast.makeText(SettingsActivity.this, R.string.message_setup_upcoming, Toast.LENGTH_SHORT).show();
                }else {
                    upcomingSwitch.setChecked(false);
                    appPreference.setUpcoming(isUpcoming);
                    releaseTodayReminderMovie.cancelAlarm(SettingsActivity.this, ReleaseTodayReminderMovie.TYPE_REPEATING);
                    Toast.makeText(SettingsActivity.this, R.string.message_cancel_upcoming, Toast.LENGTH_SHORT).show();
                }
            }
        });

        settingLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
            }
        });

        if (appPreference.isDaily()){
            dailySwitch.setChecked(true);
        }else {
            dailySwitch.setChecked(false);
        }

        if (appPreference.isUpcoming()){
            upcomingSwitch.setChecked(true);
        }else {
            upcomingSwitch.setChecked(false);
        }
    }

}
