package com.guanchazhe.news.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.views.NewsListView;


public class NewsListActivity extends AppCompatActivity implements NewsListView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list_view);
    }
}
