package com.esp.sharing.Setting;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Admin on 20/7/2017.
 */

public class BottomPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {



    public BottomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new ProfileFragment();
            case 1: return new PasswordFragment();
            case 2: return new ApplicationFragment();
            default: return new ProfileFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);

    }
}
