package io.carpoolapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.carpoolapp.screens.AccountFragment;
import io.carpoolapp.screens.ProfileDetailsFragment;

public class ProfilePageAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public ProfilePageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.numberOfTabs = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                ProfileDetailsFragment profileDetailsFragment = new ProfileDetailsFragment();
                return profileDetailsFragment;
            }
            case 1: {
                AccountFragment accountFragment = new AccountFragment();
                return accountFragment;
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
