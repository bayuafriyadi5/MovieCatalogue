package com.baytech.submission5.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.baytech.submission5.Adapter.MovieTabAdapter;
import com.baytech.submission5.R;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayout = view.findViewById(R.id.home_tablayout);
        ViewPager viewPager = view.findViewById(R.id.home_viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new MovieTabAdapter(getChildFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);

    }

}
