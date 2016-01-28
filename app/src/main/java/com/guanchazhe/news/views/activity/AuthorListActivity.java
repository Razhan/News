package com.guanchazhe.news.views.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.guanchazhe.news.NewsApplication;
import com.guanchazhe.news.R;
import com.guanchazhe.news.injector.components.DaggerAuthorListComponent;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.mvp.model.entities.Author;
import com.guanchazhe.news.mvp.presenters.AuthorListPresenter;
import com.guanchazhe.news.mvp.views.AuthorListView;
import com.guanchazhe.news.views.adapter.AuthorListAdapter;
import com.guanchazhe.news.views.widget.BetterViewAnimator;
import com.guanchazhe.news.views.widget.RecyclerInsetsDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AuthorListActivity extends BaseActivity implements AuthorListView {

    @Bind(R.id.toolbar)                             Toolbar toolBar;
    @Bind(R.id.rv_list_content_listview)            RecyclerView authorListRV;
    @Bind(R.id.bv_list_content_viewanimator)        BetterViewAnimator viewAnimator;

    @Inject
    AuthorListPresenter mAuthorListPresenter;

    private AuthorListAdapter mAuthorListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        initToolbar();
        initDependencyInjector();
        initPresenter();
        initRecyclerView();
    }

    @Override
    protected void initUi() {
        setContentView(R.layout.activity_author_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initToolbar() {
        toolBar.setTitle(R.string.all_authors);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initDependencyInjector() {
        NewsApplication avengersApplication = (NewsApplication) getApplication();

        DaggerAuthorListComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(avengersApplication.getAppComponent())
                .build().inject(this);
    }

    private void initPresenter() {
        mAuthorListPresenter.attachView(this);
        mAuthorListPresenter.onCreate();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        authorListRV.setLayoutManager(linearLayoutManager);
        authorListRV.addItemDecoration(new RecyclerInsetsDecoration(this));

        mAuthorListAdapter = new AuthorListAdapter(authorListRV, null, false,
                (view, data, position) -> mAuthorListPresenter.onElementClick(view, (Author) data));

        authorListRV.setAdapter(mAuthorListAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuthorListPresenter.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAuthorListPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuthorListPresenter.onStop();
    }

    @Override
    public void setAuthorList(List<Author> authors) {
        mAuthorListAdapter.set(authors);
    }

    @Override
    public void showAuthorListView() {
        viewAnimator.setDisplayedChildId(R.id.rv_list_content_listview);
    }

    @Override
    public void showLoadingView() {
        viewAnimator.setDisplayedChildId(Constant.ANIMATOR_VIEW_LOADING);
    }

    @Override
    public void showErrorView() {
        viewAnimator.setDisplayedChildId(Constant.ANIMATOR_VIEW_ERROR);
    }

    @Override
    public void showDetailScreen(View view, Author author) {
        CommentaryListActivity.start(this, view, author);
    }

}
