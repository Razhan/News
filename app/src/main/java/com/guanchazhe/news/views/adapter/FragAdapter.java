package com.guanchazhe.news.views.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.guanchazhe.news.R;
import com.guanchazhe.news.views.activity.MainActivity;

/**
 * Created by ran.zhang on 1/11/16.
 */
public class FragAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private final String tabTitles[];
    private Context context;

    public FragAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabTitles = context.getResources().getStringArray(R.array.news_type);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return MainActivity.PageFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
