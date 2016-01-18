package com.guanchazhe.news.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.views.Fragment.ListFragment;
import com.guanchazhe.news.views.adapter.ChannelListAdapter;
import com.guanchazhe.news.views.adapter.FragmentAdapter;
import com.guanchazhe.news.views.widget.RecyclerInsetsDecoration;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)             Toolbar mToolbar;
    @Bind(R.id.tabs)                TabLayout tabs;
    @Bind(R.id.viewPager)           ViewPager viewPager;
    @Bind(R.id.drawer_layout)       DrawerLayout mDrawerLayout;
    @Bind(R.id.drawer_recycler)     RecyclerView drawerRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        initToolbar();
        initDrawerLayout();
        initDrawerRecycleView();
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

    private void initDrawerRecycleView() {
        String[] rowListItem = getResources().getStringArray(R.array.column_name);

        drawerRecycleView.setHasFixedSize(true);
        drawerRecycleView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
        drawerRecycleView.addItemDecoration(new RecyclerInsetsDecoration(this));

        ChannelListAdapter mChannelListAdapter = new ChannelListAdapter(drawerRecycleView, Arrays.asList(rowListItem), false,
                (view, data, position) -> Log.d("ColumnList", (String) data));

        drawerRecycleView.setAdapter(mChannelListAdapter);
    }

    private void initTabLayout() {
        FragmentAdapter pagerAdapter =
                new FragmentAdapter(getSupportFragmentManager(), 3);

        pagerAdapter.addFragment(ListFragment.newInstance(Constant.NewsType.NEWS, 1, true), "要闻");
        pagerAdapter.addFragment(ListFragment.newInstance(Constant.NewsType.NEWS, 3, false), "缤纷");
        pagerAdapter.addFragment(ListFragment.newInstance(Constant.NewsType.COMMENTARY, 2, false), "时评");

        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.authors)
    public void OnAuthorsClick() {
        Intent intent = new Intent(this, AuthorListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.channels)
    public void OnChannelsClick() {
        Intent intent = new Intent(this, FavoriteChannelActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.setting)
    public void OnSettingClick() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.nightMode)
    public void OnContactClick() {
//        Intent intent = new Intent(this, SettingActivity.class);
//        startActivity(intent);
    }

    @OnClick(R.id.about)
    public void OnAboutClick() {
//        Intent intent = new Intent(this, SettingActivity.class);
//        startActivity(intent);
    }



}
