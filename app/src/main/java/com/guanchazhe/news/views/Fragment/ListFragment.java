package com.guanchazhe.news.views.fragment;

import android.os.Build;
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

    @Bind(R.id.multi_swipe_refresh_layout)          MultiSwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.bv_list_content_viewanimator)        BetterViewAnimator viewAnimator;
    @Bind(R.id.rv_list_content_listview)            RecyclerView contentRV;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent, getActivity().getTheme()));
        } else {
            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        }
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setCanChildScrollUpCallback(this);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        contentRV.setLayoutManager(linearLayoutManager);
        contentRV.addItemDecoration(new RecyclerInsetsDecoration(mContext));
        contentRV.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mNewsListPresenter.onListEndReached(currentPage);
            }
        });

        setRecycleViewAdapter(getArguments().getBoolean("withHeader"), mNewsType);
    }

    protected void setRecycleViewAdapter(boolean header, Constant.NewsType type) {

        if (type.equals(Constant.NewsType.YAOWEN) || type.equals(Constant.NewsType.HUABIAN) ||
                type.equals(Constant.NewsType.HYBRID)) {
            listAdapter = new NewsListAdapter(contentRV, null, header,
                    (view, data, position) -> mNewsListPresenter.onElementClick((News)data));
        } else {
            listAdapter = new CommentaryListAdapter(contentRV, null, header,
                    (view, data, position) -> mNewsListPresenter.onElementClick((News)data));
        }

        contentRV.setAdapter(listAdapter);
    }

    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return (contentRV != null && ViewCompat.canScrollVertically(contentRV, -1)) ||
                (viewAnimator != null && viewAnimator.getDisplayedChildId() == Constant.ANIMATOR_VIEW_LOADING);
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
        viewAnimator.setDisplayedChildId(Constant.ANIMATOR_VIEW_LOADING);
    }

    @Override
    public void showNewsListView() {
        viewAnimator.setDisplayedChildId(R.id.rv_list_content_listview);
    }

    @Override
    public void showErrorView() {
        viewAnimator.setDisplayedChildId(Constant.ANIMATOR_VIEW_ERROR);
    }

    @Override
    public boolean isContentDisplayed() {
        return viewAnimator.getDisplayedChildId() == R.id.rv_list_content_listview;
    }

    @Override
    public void showRefreshIndicator() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshIndicator() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showDetailScreen(News news) {

        if (news.getType().equals(Constant.NewsType.YAOWEN.toString()) ||
                news.getType().equals(Constant.NewsType.HUABIAN.toString())) {
            NewsDetailActivity.start(getActivity(), news);
        } else {
            CommentaryDetailActivity.start(getActivity(), news);
        }
    }

    @Override
    public Constant.NewsType getNewsType() {
        return mNewsType;
    }
}