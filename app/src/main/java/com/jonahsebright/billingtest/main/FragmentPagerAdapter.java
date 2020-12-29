package com.jonahsebright.billingtest.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> pageTitles;
    private ArrayList<Fragment> fragments;

    public FragmentPagerAdapter(@NonNull FragmentManager fm, ArrayList<String> pageTitles, ArrayList<Fragment> fragments) {
        super(fm);
        this.pageTitles = pageTitles;
        this.fragments = fragments;
    }

    public FragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<String> pageTitles, ArrayList<Fragment> fragments) {
        super(fm, behavior);
        this.pageTitles = pageTitles;
        this.fragments = fragments;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
