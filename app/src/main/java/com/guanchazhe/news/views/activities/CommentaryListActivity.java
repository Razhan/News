package com.guanchazhe.news.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.guanchazhe.news.NewsApplication;
import com.guanchazhe.news.R;
import com.guanchazhe.news.injector.components.DaggerCommentaryListComponent;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.injector.modules.CommentaryListModule;
import com.guanchazhe.news.model.entities.Commentary;
import com.guanchazhe.news.mvp.presenters.CommentaryListPresenter;
import com.guanchazhe.news.mvp.views.CommentaryListView;
import com.guanchazhe.news.views.adapter.BaseRecyclerAdapter;
import com.guanchazhe.news.views.adapter.CommentaryListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentaryListActivity extends AppCompatActivity
        implements CommentaryListView, BaseRecyclerAdapter.OnItemClickListener {

    private final static String EXTRA_AUTHOR_NAME = "author_name";

    @Inject
    CommentaryListPresenter mCommentaryListPresenter;

    @Bind(R.id.collection_list)         RecyclerView mCollectionRecycler;
    @Bind(R.id.collection_loading)      ProgressBar mLoadingIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentary_list);
        ButterKnife.bind(this);

        initDependencyInjector();
        initializePresenter();
    }

    private void initDependencyInjector() {
        String authorname = getIntent().getStringExtra(EXTRA_AUTHOR_NAME);

        DaggerCommentaryListComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(((NewsApplication) getApplication()).getAppComponent())
                .commentaryListModule(new CommentaryListModule(authorname))
                .build().inject(this);
    }

    private void initializePresenter() {
        String authorname = getIntent().getStringExtra(EXTRA_AUTHOR_NAME);

        mCommentaryListPresenter.attachView(this);
        mCommentaryListPresenter.initialisePresenters(authorname);
        mCommentaryListPresenter.onCreate();
    }

    @Override
    public void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showItems(List<Commentary> items) {
        mCollectionRecycler.setAdapter(new CommentaryListAdapter(mCollectionRecycler, items, this));
    }

    public static void start(Context context, int anthorname, String type) {
        Intent collectionIntent = new Intent(context, CommentaryListActivity.class);
        collectionIntent.putExtra(EXTRA_AUTHOR_NAME, anthorname);
        context.startActivity(collectionIntent);
    }

    @Override
    public void onItemClick(View view, Object data, int position) {
        Log.d("onItemClick", "onItemClick");
    }
}
