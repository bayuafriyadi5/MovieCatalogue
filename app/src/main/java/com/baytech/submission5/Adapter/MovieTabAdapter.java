package com.baytech.submission5.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.baytech.submission5.Fragment.MoviesFragment;
import com.baytech.submission5.Fragment.TvFragment;

public class MovieTabAdapter extends FragmentPagerAdapter {

    public MovieTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new MoviesFragment();
        }
        return new TvFragment();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "Film";
        }
        return "Tv";
    }

    @Override
    public int getCount() {
        return 2;
    }
}
