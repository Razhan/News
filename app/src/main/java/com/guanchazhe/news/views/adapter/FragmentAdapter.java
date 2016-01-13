package com.guanchazhe.news.views.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.views.Fragment.NewsListFragment;
import com.guanchazhe.news.views.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ran.zhang on 1/11/16.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private final List<Fragment> mFragmentList;
    private final List<String> mFragmentTitleList;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        mFragmentList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();
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
