package com.guanchazhe.news.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

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

    private final String YAOWEN = "要闻";
    private final String SHIPING = "时评";
    private final String BINFEN = "缤纷";

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
        String[] rowListItem = getResources().getStringArray(R.array.channel_name);

        drawerRecycleView.setHasFixedSize(true);
        drawerRecycleView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
        drawerRecycleView.addItemDecoration(new RecyclerInsetsDecoration(this));

        ChannelListAdapter mChannelListAdapter = new ChannelListAdapter(drawerRecycleView, Arrays.asList(rowListItem), false,
                (view, data, position) -> FavoriteChannelActivity.start(this, (String)data));

        drawerRecycleView.setAdapter(mChannelListAdapter);
    }

    private void initTabLayout() {
        FragmentAdapter pagerAdapter =
                new FragmentAdapter(getSupportFragmentManager(), 3);

        pagerAdapter.addFragment(ListFragment.newInstance(Constant.NewsType.NEWS,
                Constant.getChannelID(YAOWEN), 1, true), YAOWEN);
        pagerAdapter.addFragment(ListFragment.newInstance(Constant.NewsType.COMMENTARY,
                Constant.getChannelID(SHIPING), 2, false), SHIPING);
        pagerAdapter.addFragment(ListFragment.newInstance(Constant.NewsType.NEWS,
                Constant.getChannelID(BINFEN), 3, false), BINFEN);

        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
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

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "再次点击退出应用", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(
                () -> doubleBackToExitPressedOnce=false, 2000);
    }

}
