package com.baytech.submission5.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.baytech.submission5.Fragment.SearchFragment;
import com.baytech.submission5.R;

import static com.baytech.submission5.Activity.BaseAppCompatActivity.KEY_FRAGMENT;

public class SearchActivity extends AppCompatActivity {
    private Fragment pageContent = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();
        } else {
            pageContent = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pageContent).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void setMode(int itemId) {
        switch (itemId) {
            case R.id.action_change_settings:
                Intent mIntent = new Intent(SearchActivity.this,SettingsActivity.class);
                startActivity(mIntent);
                break;
            case R.id.action_home:
                Intent intent1 = new Intent(SearchActivity.this,MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.action_search:
                break;
            case R.id.action_favorite:
                Intent intent = new Intent(SearchActivity.this,FavoriteActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, pageContent);
        super.onSaveInstanceState(outState);
    }
}
