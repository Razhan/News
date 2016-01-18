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
import com.guanchazhe.news.injector.modules.NewsListModule;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.presenters.NewsListPresenter;
import com.guanchazhe.news.mvp.views.NewsListView;
import com.guanchazhe.news.views.activity.CommentaryDetailActivity;
import com.guanchazhe.news.views.activity.NewsDetailActivity;
import com.guanchazhe.news.views.adapter.BaseRecyclerAdapter;
import com.guanchazhe.news.views.adapter.CommentaryListAdapter;
import com.guanchazhe.news.views.adapter.NewsListAdapter;
import com.guanchazhe.news.views.listener.EndlessScrollListener;
import com.guanchazhe.news.views.widget.BetterViewAnimator;
import com.guanchazhe.news.views.widget.MultiSwipeRefreshLayout;
import com.guanchazhe.news.views.widget.RecyclerInsetsDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by ran.zhang on 1/16/16.
 */
public class ListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        MultiSwipeRefreshLayout.CanChildScrollUpCallback, NewsListView {

    @Bind(R.id.multi_swipe_refresh_layout)      MultiSwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.movies_animator)                 BetterViewAnimator mViewAnimator;
    @Bind(R.id.movies_recycler_view)            RecyclerView mRecyclerView;

    @Inject
    NewsListPresenter mNewsListPresenter;

    private BaseRecyclerAdapter listAdapter;
    private Constant.NewsType mNewsType;

    public static ListFragment newInstance(Constant.NewsType newsType, int channelId,
                                           int attributeId, boolean withHeader) {
        ListFragment myFragment = new ListFragment();

        Bundle args = new Bundle();
        args.putSerializable("type", newsType);
        args.putInt("channelId", channelId);
        args.putInt("attributeId", attributeId);
        args.putBoolean("withHeader", withHeader);

        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDependencyInjector();
        initializePresenter();
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void initDependencyInjector() {
        NewsApplication avengersApplication = (NewsApplication) getActivity().getApplication();

        DaggerNewsListComponent.builder()
                .activityModule(new ActivityModule(mContext))
                .appComponent(avengersApplication.getAppComponent())
                .newsListModule(new NewsListModule(getArguments().getInt("channelId", 0),
                        getArguments().getInt("attributeId", 0)))
                .build().inject(this);
    }

    private void initializePresenter() {

        mNewsType = (Constant.NewsType) getArguments().get("type");

        mNewsListPresenter.attachView(this);
        mNewsListPresenter.onCreate();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipe_progress_colors));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setCanChildScrollUpCallback(this);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerInsetsDecoration(mContext));
        mRecyclerView.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mNewsListPresenter.onListEndReached(currentPage);
            }
        });

        setRecycleViewAdapter(getArguments().getBoolean("withHeader"), mNewsType);
    }

    protected void setRecycleViewAdapter(boolean header, Constant.NewsType type) {

        switch (type) {
            case NEWS:
                listAdapter = new NewsListAdapter(mRecyclerView, null, header,
                        (view, data, position) -> mNewsListPresenter.onElementClick((News)data));
                break;
            case COMMENTARY:
                listAdapter = new CommentaryListAdapter(mRecyclerView, null, header,
                        (view, data, position) -> mNewsListPresenter.onElementClick((News)data));
                break;
            case HYBRID:
                listAdapter = new NewsListAdapter(mRecyclerView, null, header,
                        (view, data, position) -> mNewsListPresenter.onElementClick((News)data));
                break;
            default:
                listAdapter = new NewsListAdapter(mRecyclerView, null, header,
                        (view, data, position) -> mNewsListPresenter.onElementClick((News)data));
                break;
        }

        mRecyclerView.setAdapter(listAdapter);
    }

    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return (mRecyclerView != null && ViewCompat.canScrollVertically(mRecyclerView, -1)) ||
                (mViewAnimator != null && mViewAnimator.getDisplayedChildId() == Constant.ANIMATOR_VIEW_LOADING);
    }

    @Override
    public void onRefresh(){
        mNewsListPresenter.onRefresh();
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
    public void setNewsList(List<News> news) {
        listAdapter.set(news);
    }

    @Override
    public void addNewsList(List<News> moreNews) {
        listAdapter.add(moreNews);
    }

    @Override
    public void clearNewsList() {
        listAdapter.clear();
    }

    @Override
    public void showLoadingView() {
        mViewAnimator.setDisplayedChildId(Constant.ANIMATOR_VIEW_LOADING);
    }

    @Override
    public void showNewsListView() {
        mViewAnimator.setDisplayedChildId(R.id.movies_recycler_view);
    }

    @Override
    public void showErrorView() {
        mViewAnimator.setDisplayedChildId(Constant.ANIMATOR_VIEW_ERROR);
    }

    @Override
    public boolean isContentDisplayed() {
        return mViewAnimator.getDisplayedChildId() == R.id.movies_recycler_view;
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

        if (mNewsType == Constant.NewsType.NEWS) {
            NewsDetailActivity.start(getActivity(), news);
        } else if (mNewsType == Constant.NewsType.COMMENTARY) {
            CommentaryDetailActivity.start(getActivity(), news);
        } else {
            NewsDetailActivity.start(getActivity(), news);
        }
    }
}