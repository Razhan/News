package com.guanchazhe.news.views.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ran.zhang on 1/11/16.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT;
    private final List<Fragment> mFragmentList;
    private final List<String> mFragmentTitleList;

    public FragmentAdapter(FragmentManager fm, int size) {
        super(fm);
        mFragmentList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();
        PAGE_COUNT = size;
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
