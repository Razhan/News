package com.guanchazhe.news.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class AuthorListActivity extends AppCompatActivity implements AuthorListView {

    @Bind(R.id.toolbar)                 Toolbar mToolBar;
    @Bind(R.id.author_list_recycler)    RecyclerView authorListRecycler;
    @Bind(R.id.author_list_animator)    BetterViewAnimator mViewAnimator;

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

    private void initUi() {
        setContentView(R.layout.activity_author_list);
        ButterKnife.bind(this);
    }

    private void initToolbar() {
        mToolBar.setTitle("专栏作家");
        mToolBar.setNavigationOnClickListener(v -> onBackPressed());
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
        authorListRecycler.setLayoutManager(linearLayoutManager);
        authorListRecycler.addItemDecoration(new RecyclerInsetsDecoration(this));

        mAuthorListAdapter = new AuthorListAdapter(authorListRecycler, null, false,
                (view, data, position) -> mAuthorListPresenter.onElementClick(view, (Author) data));

        authorListRecycler.setAdapter(mAuthorListAdapter);
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
        mViewAnimator.setDisplayedChildId(R.id.author_list_recycler);
    }

    @Override
    public void showLoadingView() {
        mViewAnimator.setDisplayedChildId(Constant.ANIMATOR_VIEW_LOADING);
    }

    @Override
    public void showErrorView() {
        mViewAnimator.setDisplayedChildId(Constant.ANIMATOR_VIEW_ERROR);
    }

    @Override
    public void showDetailScreen(View view, Author author) {
        CommentaryListActivity.start(this, view, author);
    }

}
