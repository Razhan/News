package com.guanchazhe.news.views.Fragment;


import android.os.Bundle;
import android.view.View;

import com.guanchazhe.news.NewsApplication;
import com.guanchazhe.news.injector.components.DaggerNewsListComponent;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.views.adapter.NewsListAdapter;

import java.util.List;

/**
 * Created by ranzh on 1/10/2016.
 */
public class NewsListFragment extends ListFragment {

    private NewsListAdapter mNewsListAdapter;

    public static NewsListFragment newInstance(int someInt, boolean someBoolean) {
        NewsListFragment myFragment = new NewsListFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        args.putBoolean("someBoolean", someBoolean);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initDependencyInjector();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initDependencyInjector() {
        NewsApplication avengersApplication = (NewsApplication) getActivity().getApplication();

        DaggerNewsListComponent.builder()
                .activityModule(new ActivityModule(mContext))
                .appComponent(avengersApplication.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void setRecycleViewAdapter(boolean header) {
        mNewsListAdapter = new NewsListAdapter(mRecyclerView, null, header,
                (view, data, position) -> mNewsListPresenter.onElementClick((News)data));

        mRecyclerView.setAdapter(mNewsListAdapter);
    }

    @Override
    public void setNewsList(List<News> news) {
        mNewsListAdapter.set(news);
    }

    @Override
    public void addNewsList(List<News> moreNews) {
        mNewsListAdapter.add(moreNews);
    }

    @Override
    public void clearNewsList() {
        mNewsListAdapter.clear();
    }

}

