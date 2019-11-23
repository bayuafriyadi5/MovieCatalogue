package com.baytech.submission5.Adapter;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.baytech.submission5.Fragment.FavMovieFragment;
import com.baytech.submission5.Fragment.FavTvFragment;

public class FavTabAdapter extends FragmentPagerAdapter {

    public FavTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new FavMovieFragment();
        }
        return new FavTvFragment();
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
