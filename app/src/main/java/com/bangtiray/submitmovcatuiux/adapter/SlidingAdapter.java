package com.bangtiray.submitmovcatuiux.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bangtiray.submitmovcatuiux.FavoriteFragment;
import com.bangtiray.submitmovcatuiux.NowPlayingFragment;
import com.bangtiray.submitmovcatuiux.UpComingFragment;

/**
 * Created by Ahmad Satiri on 1/24/2018.
 */

public class SlidingAdapter extends FragmentPagerAdapter {
    public static int item = 3;

    public SlidingAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NowPlayingFragment();
            case 1:
                return new UpComingFragment();
            case 2:
                return new FavoriteFragment();

        }
        return null;
    }

    @Override
    public int getCount() {

        return item;

    }


    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "NOW PLAYING";
            case 1:
                return "UPCOMING";

        }
        return null;
    }
}


