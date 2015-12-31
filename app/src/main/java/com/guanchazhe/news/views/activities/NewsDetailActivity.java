package com.guanchazhe.news.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.guanchazhe.news.NewsApplication;
import com.guanchazhe.news.R;
import com.guanchazhe.news.injector.components.DaggerNewsDetailComponent;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.injector.modules.NewsDetailModule;
import com.guanchazhe.news.model.repository.Repository;
import com.guanchazhe.news.model.rest.RestDataSource;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewsDetailActivity extends AppCompatActivity {

    private static final String EXTRA_NEWS_NAME    = "news.name";
    public static final String EXTRA_NEWS_ID       = "news.id";

    @Bind(R.id.button)      Button bu;
    @Bind(R.id.textView)    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_news_detail);
//        ButterKnife.bind(this);
//        bu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Repository repository = new RestDataSource();
//
//                repository.getNewsDetail("ios", 250357)
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                        html -> {
//                            Log.d("html", html);
//                        },
//
//                        error -> {
//                            Log.d("error", error.getMessage());
//                        });
//            }
//        });

//        initializeBinding();
        initUi();
        initializeDependencyInjector();
//        initializePresenter();
//        initToolbar();
//        initTransitions();
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
