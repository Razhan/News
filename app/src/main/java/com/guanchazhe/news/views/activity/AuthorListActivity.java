package com.guanchazhe.news.views.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.guanchazhe.news.R;
import com.guanchazhe.news.views.adapter.AuthorListAdapter;
import com.guanchazhe.news.views.listener.EndlessScrollListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AuthorListActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)                 Toolbar mToolBar;
    @Bind(R.id.author_list_recycler)    RecyclerView authorListRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        initDependencyInjector();
//        initializePresenter();
        initToolbar();
        initRecyclerView();


    }

    private void initToolbar() {
        mToolBar.setTitle("专栏作家");
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(v -> onBackPressed());

//        getWindow().setStatusBarColor(ContextCompat.getColor(AuthorListActivity.this, R.color.red));
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        authorListRecycler.setLayoutManager(linearLayoutManager);
//        authorListRecycler.setAdapter(new AuthorListAdapter(authorListRecycler, null, this));
    }





}
