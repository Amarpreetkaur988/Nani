package com.omninos.nani.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.omninos.nani.fragments.AboutOneFragment;
import com.omninos.nani.fragments.AboutThreeFragment;
import com.omninos.nani.fragments.AboutTwoFragment;

public class GetStartedPagerAdapter extends FragmentStatePagerAdapter {

    public GetStartedPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                AboutOneFragment aboutOneFragment = new AboutOneFragment();
                return aboutOneFragment;
            case 1:
                AboutTwoFragment aboutTwoFragment = new AboutTwoFragment();
                return aboutTwoFragment;
            case 2:
                AboutThreeFragment aboutThreeFragment = new AboutThreeFragment();
                return aboutThreeFragment;

            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return 3;
    }
}

