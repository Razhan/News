package com.guanchazhe.news.views.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.views.fragment.ListFragment;
import com.guanchazhe.news.views.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavoriteChannelActivity extends BaseActivity {

    private final String CLICKEDCHANNEL = "clickedChannel";

    @Bind(R.id.toolbar)         Toolbar toolBar;
    @Bind(R.id.tabs)            TabLayout tabs;
    @Bind(R.id.viewPager)       ViewPager viewPager;

    private List<String> favoriteChannels;
    private String mClickedChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initTabLayout();
    }

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_main_content);
        ButterKnife.bind(this);
    }

    @Override
    protected void initToolbar() {
        toolBar.setTitle(R.string.favorite_channels);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initTabLayout() {

        int tabIndex = 0;

        loadFavoriteChannels();

        List<String> favoriteChannelsList = new ArrayList<>(favoriteChannels);

        if (mClickedChannel != null && !favoriteChannels.contains(mClickedChannel)) {
            favoriteChannelsList.add(mClickedChannel);
            tabIndex = favoriteChannelsList.size() - 1;
        } else if (mClickedChannel != null) {
            tabIndex = favoriteChannelsList.indexOf(mClickedChannel);
        }

        FragmentAdapter pagerAdapter =
                new FragmentAdapter(getSupportFragmentManager(), favoriteChannelsList.size());

        for (String channel : favoriteChannelsList) {

            pagerAdapter.addFragment(ListFragment.newInstance(Constant.NewsType.HYBRID,
                    Constant.getChannelID(channel), 0, false), channel);
        }

        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(tabIndex);

        if (favoriteChannelsList.size() <= 5) {
            tabs.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

    }

    private void loadFavoriteChannels() {

        SharedPreferences prefs = getSharedPreferences(Constant.SHAREDPREFERENCE, Context.MODE_PRIVATE);
        String stringChannels = prefs.getString(Constant.FAVORITECHANNELS, null);

        Gson gson = new Gson();
        favoriteChannels = gson.fromJson(stringChannels,
                new TypeToken<List<String>>() {
                }.getType());

        if (favoriteChannels == null) {
            favoriteChannels = Constant.getDefaultChannels();
        }

        mClickedChannel = getIntent().getStringExtra(CLICKEDCHANNEL);
    }

    public static void start(Activity activity, String channel) {
        Intent i = new Intent(activity, FavoriteChannelActivity.class);

        i.putExtra("clickedChannel", channel);

        activity.startActivity(i);
    }

}
