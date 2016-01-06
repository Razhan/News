package com.guanchazhe.news.views.activities;

import android.app.ActivityOptions;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.guanchazhe.news.NewsApplication;
import com.guanchazhe.news.R;
import com.guanchazhe.news.model.entities.News;
import com.guanchazhe.news.utils.Utils;
import com.guanchazhe.news.injector.components.DaggerNewsListComponent;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.mvp.presenters.NewsListPresenter;
import com.guanchazhe.news.mvp.views.NewsListView;
import com.guanchazhe.news.views.adapter.NewsListAdapter;
import com.guanchazhe.news.views.utils.RecyclerInsetsDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NewsListActivity extends AppCompatActivity implements NewsListView {

    @Bind(R.id.activity_avengers_recycler)              RecyclerView mAvengersRecycler;
    @Bind(R.id.activity_avengers_toolbar)               Toolbar mAvengersToolbar;
    @Bind(R.id.activity_avengers_progress)              ProgressBar mAvengersProgress;
    @Bind(R.id.activity_avengers_collapsing)            CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.activity_avengers_empty_indicator)       View mEmptyIndicator;
    @Bind(R.id.activity_avengers_error_view)            View mErrorView;

    @Inject
    NewsListPresenter mNewsListPresenter;

    private NewsListAdapter mNewsListAdapter;
    private Snackbar mLoadingMoreNewsSnack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        initializeToolbar();
        initializeRecyclerView();
        initializeDependencyInjector();
        initializePresenter();
    }

    private void initUi() {
        setContentView(R.layout.activity_news_list);
        ButterKnife.bind(this);
    }

    private void initializeToolbar() {
        mCollapsingToolbar.setTitle("");
    }

    @OnClick(R.id.view_error_retry_button)
    public void onRetryButtonClicked(View v) {
        mNewsListPresenter.onErrorRetryRequest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNewsListPresenter.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNewsListPresenter.onPause();
    }

    private void initializePresenter() {
        mNewsListPresenter.attachView(this);
        mNewsListPresenter.onCreate();
    }


    private void initializeDependencyInjector() {
        NewsApplication avengersApplication = (NewsApplication) getApplication();

        DaggerNewsListComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(avengersApplication.getAppComponent())
                .build().inject(this);
    }

    private void initializeRecyclerView() {
        mAvengersRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAvengersRecycler.addItemDecoration(new RecyclerInsetsDecoration(this));
        mAvengersRecycler.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void bindNewsList(List<News> news) {
        mNewsListAdapter = new NewsListAdapter(news, this,
                (position, sharedView, characterImageView) -> {
                    mNewsListPresenter.onElementClick(position);
                });

        mAvengersRecycler.setAdapter(mNewsListAdapter);
    }

    @Override
    public void showNewsList() {
        if (mAvengersRecycler.getVisibility() == View.GONE ||
                mAvengersRecycler.getVisibility() == View.INVISIBLE)

            mAvengersRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateNewsList(int charactersAdded) {
        mNewsListAdapter.notifyItemRangeInserted(
                mNewsListAdapter.getItemCount() + charactersAdded, charactersAdded);
    }

    @Override
    public void hideNewsList() {
        mAvengersRecycler.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingMoreNewsIndicator() {
        mLoadingMoreNewsSnack = Snackbar.make(mAvengersRecycler,
                getString(R.string.message_loading_more_characters), Snackbar.LENGTH_INDEFINITE);

        mLoadingMoreNewsSnack.show();
    }

    @Override
    public void hideLoadingMoreNewsIndicator() {
        if (mLoadingMoreNewsSnack != null) mLoadingMoreNewsSnack.dismiss();
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingMoreNewsSnack.dismiss();
    }

    @Override
    public void showLoadingView() {
        mEmptyIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mEmptyIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showLightError() {
        Snackbar.make(mAvengersRecycler, getString(R.string.error_loading_characters), Snackbar.LENGTH_LONG)
                .setAction(R.string.try_again, v -> mNewsListPresenter.onErrorRetryRequest())
                .show();
    }

    @Override
    public void hideErrorView() {
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyIndicator() {
        mEmptyIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyIndicator() {
        mEmptyIndicator.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mNewsListPresenter.onStop();
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemsCount   = layoutManager.getChildCount();
            int totalItemsCount     = layoutManager.getItemCount();
            int firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition();

            if (visibleItemsCount + firstVisibleItemPos >= totalItemsCount) {
                mNewsListPresenter.onListEndReached();
            }
        }
    };

    @Override
    public ActivityOptions getActivityOptions(int position, View clickedView) {
        String sharedViewName = Utils.getListTransitionName(position);
        return ActivityOptions.makeSceneTransitionAnimation(
                this, clickedView, sharedViewName);
    }

    @Override
    public void showConnectionErrorMessage() {
        TextView errorTextView = ButterKnife.findById(mErrorView, R.id.view_error_message);
        errorTextView.setText(R.string.error_network_uknownhost);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showServerErrorMessage() {
        TextView errorTextView = ButterKnife.findById(mErrorView, R.id.view_error_message);
        errorTextView.setText(R.string.error_network_marvel_server);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUnknownErrorMessage() {
        TextView errorTextView = ButterKnife.findById(mErrorView, R.id.view_error_message);
        errorTextView.setText("Uknown error");
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDetailScreen(News news) {
        NewsDetailActivity.start(this, news);
//        Log.d("showDetailScreen", "showDetailScreen");
    }

}
