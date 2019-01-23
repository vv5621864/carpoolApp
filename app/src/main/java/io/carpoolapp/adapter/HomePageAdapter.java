package io.carpoolapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.carpoolapp.screens.CurrentRidesFragment;
import io.carpoolapp.screens.HistoryFragment;

public class HomePageAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public HomePageAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                CurrentRidesFragment currentRidesFragment = new CurrentRidesFragment();
                return currentRidesFragment;
            }
            case 1: {
                HistoryFragment historyFragment = new HistoryFragment();
                return historyFragment;
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}

