package com.guanchazhe.news.views.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.views.Fragment.ListFragment;
import com.guanchazhe.news.views.adapter.FragmentAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavoriteChannelActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)         Toolbar toolbar;
    @Bind(R.id.tabs)            TabLayout tabs;
    @Bind(R.id.viewPager)       ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        initToolbar();
        initTabLayout();
    }

    private void initUi() {
        setContentView(R.layout.activity_main_content);
        ButterKnife.bind(this);
    }

    private void initToolbar() {
        toolbar.setTitle("我的栏目");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initTabLayout() {
        FragmentAdapter pagerAdapter =
                new FragmentAdapter(getSupportFragmentManager(), 2);

        pagerAdapter.addFragment(ListFragment.newInstance(Constant.NewsType.NEWS, 1, false), "要闻");
        pagerAdapter.addFragment(ListFragment.newInstance(Constant.NewsType.NEWS, 3, false), "花边");

        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }


}
