package com.guanchazhe.news.views.Fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.presenters.NewsListPresenter;
import com.guanchazhe.news.views.widget.BetterViewAnimator;
import com.guanchazhe.news.views.widget.MultiSwipeRefreshLayout;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by ranzh on 1/10/2016.
 */
public abstract class NewsListFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        MultiSwipeRefreshLayout.CanChildScrollUpCallback {

    @Bind(R.id.multi_swipe_refresh_layout)      MultiSwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.movies_animator)                 BetterViewAnimator mViewAnimator;
    @Bind(R.id.movies_recycler_view)            RecyclerView mRecyclerView;

    @Inject
    NewsListPresenter mNewsListPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipe_progress_colors));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setCanChildScrollUpCallback(this);
    }

    @Override
    public abstract void onRefresh();
}
