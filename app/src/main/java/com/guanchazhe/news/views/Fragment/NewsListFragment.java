package com.guanchazhe.news.views.Fragment;


import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guanchazhe.news.NewsApplication;
import com.guanchazhe.news.R;
import com.guanchazhe.news.injector.components.DaggerNewsListComponent;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.presenters.NewsListPresenter;
import com.guanchazhe.news.mvp.views.NewsListView;
import com.guanchazhe.news.views.activity.NewsDetailActivity;
import com.guanchazhe.news.views.adapter.NewsListAdapter;
import com.guanchazhe.news.views.widget.BetterViewAnimator;
import com.guanchazhe.news.views.widget.MultiSwipeRefreshLayout;
import com.guanchazhe.news.views.widget.RecyclerInsetsDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by ranzh on 1/10/2016.
 */
public class NewsListFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        MultiSwipeRefreshLayout.CanChildScrollUpCallback,
        NewsListView {

    protected static final int ANIMATOR_VIEW_LOADING = R.id.view_loading;
    protected static final int ANIMATOR_VIEW_CONTENT = R.id.movies_recycler_view;
    protected static final int ANIMATOR_VIEW_ERROR = R.id.view_error;

    @Bind(R.id.multi_swipe_refresh_layout)      MultiSwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.movies_animator)                 BetterViewAnimator mViewAnimator;
    @Bind(R.id.movies_recycler_view)            RecyclerView mRecyclerView;

    @Inject
    NewsListPresenter mNewsListPresenter;

    private NewsListAdapter mNewsListAdapter;
    private int mCurrentPage = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeDependencyInjector();
        initializePresenter();
        initSwipeRefreshLayout();
        initializeRecyclerView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNewsListPresenter.onCreate();
    }

    private void initializeDependencyInjector() {
        NewsApplication avengersApplication = (NewsApplication) getActivity().getApplication();

        DaggerNewsListComponent.builder()
                .activityModule(new ActivityModule(mContext))
                .appComponent(avengersApplication.getAppComponent())
                .build()
                .inject(this);
    }

    private void initializePresenter() {
        mNewsListPresenter.attachView(this);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipe_progress_colors));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setCanChildScrollUpCallback(this);
    }

    private void initializeRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new RecyclerInsetsDecoration(mContext));
        mRecyclerView.addOnScrollListener(mOnScrollListener);
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
    public boolean canSwipeRefreshChildScrollUp() {
        return (mRecyclerView != null && ViewCompat.canScrollVertically(mRecyclerView, -1)) ||
                (mViewAnimator != null && mViewAnimator.getDisplayedChildId() == ANIMATOR_VIEW_LOADING);
    }

    @Override
    public void onRefresh(){
        reloadContent();
    }

    private void reloadContent() {
        mNewsListPresenter.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        mNewsListPresenter.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mNewsListPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mNewsListPresenter.onStop();
    }

    @Override
    public void bindNewsList(List<News> news) {
        mNewsListAdapter = new NewsListAdapter(news, mContext,
                (position, sharedView, characterImageView) ->
                    mNewsListPresenter.onElementClick(position));

        mRecyclerView.setAdapter(mNewsListAdapter);
    }

    @Override
    public void showLoadingView() {
        mViewAnimator.setDisplayedChildId(ANIMATOR_VIEW_LOADING);
    }

    @Override
    public void showNewsList() {
        mViewAnimator.setDisplayedChildId(ANIMATOR_VIEW_CONTENT);
    }

    @Override
    public void showErrorView() {
        mViewAnimator.setDisplayedChildId(ANIMATOR_VIEW_ERROR);
    }

    @Override
    public void showRefreshIndicator() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshIndicator() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showDetailScreen(News news) {
        NewsDetailActivity.start(getActivity(), news);
    }

    @Override
    public void updateNewsList(int charactersAdded) {
        mNewsListAdapter.notifyItemRangeInserted(
                mNewsListAdapter.getItemCount() + charactersAdded, charactersAdded);
    }
}


