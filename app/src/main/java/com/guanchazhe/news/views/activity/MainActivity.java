package com.guanchazhe.news.views.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.guanchazhe.news.R;
import com.guanchazhe.news.views.Fragment.CommentaryListFragment;
import com.guanchazhe.news.views.Fragment.NewsListFragment;
import com.guanchazhe.news.views.adapter.FragmentAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)             Toolbar mToolbar;
    @Bind(R.id.tabs)                TabLayout tabs;
    @Bind(R.id.viewPager)           ViewPager viewPager;
    @Bind(R.id.drawer_layout)       DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        initToolbar();
        initDrawerLayout();
        initTabLayout();
    }

    private void initUi() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void initToolbar()   {
        mToolbar.setTitle("观察者");
        setSupportActionBar(mToolbar);
    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void initTabLayout() {
        FragmentAdapter pagerAdapter =
                new FragmentAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(NewsListFragment.newInstance(1, true), "要闻");
        pagerAdapter.addFragment(CommentaryListFragment.newInstance(2), "时评");
        pagerAdapter.addFragment(NewsListFragment.newInstance(3, false), "花边");

        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }

}
