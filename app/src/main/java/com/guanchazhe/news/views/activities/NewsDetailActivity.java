package com.guanchazhe.news.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.guanchazhe.news.NewsApplication;
import com.guanchazhe.news.R;
import com.guanchazhe.news.injector.components.DaggerNewsDetailComponent;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.injector.modules.NewsDetailModule;
import com.guanchazhe.news.mvp.presenters.NewsDetailPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsDetailActivity extends AppCompatActivity {

    private static final String EXTRA_NEWS_NAME    = "news.name";
    public static final String EXTRA_NEWS_ID       = "news.id";

    @Bind(R.id.activity_detail_content)         TextView newsContent;
    @Bind(R.id.activity_detail_header_tagline)  TextView newsTime;

    @Inject
    NewsDetailPresenter mNewsDetailPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        initializeDependencyInjector();
    }

    private void initUi() {
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
    }

    private void initializeDependencyInjector() {
        NewsApplication avengersApplication = (NewsApplication) getApplication();

        int NewsId = getIntent().getIntExtra(EXTRA_NEWS_ID, -1);

        DaggerNewsDetailComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(avengersApplication.getAppComponent())
                .newsDetailModule(new NewsDetailModule(NewsId))
                .build().inject(this);
    }

}
