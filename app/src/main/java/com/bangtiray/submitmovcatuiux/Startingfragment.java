package com.bangtiray.submitmovcatuiux;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bangtiray.submitmovcatuiux.adapter.SlidingAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class Startingfragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;

    public Startingfragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_startingfragment, container, false);
        tabLayout = (TabLayout) rootview.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootview.findViewById(R.id.viewpager);


        viewPager.setAdapter(new SlidingAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        return rootview;
    }

}
