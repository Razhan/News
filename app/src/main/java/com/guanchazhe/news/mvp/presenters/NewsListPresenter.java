package com.guanchazhe.news.mvp.presenters;

import com.guanchazhe.news.domain.GetNewsListUsecase;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.views.NewsListView;
import com.guanchazhe.news.mvp.views.Views;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by ranzh on 12/23/2015.
 */
public class NewsListPresenter implements Presenter {

    private final GetNewsListUsecase mNewsUsecase;
    private boolean mIsTheNewsRequestRunning;
    private CompositeSubscription mNewsSubscription;

    private int mFragmentIndex;
    private NewsListView mNewsView;
    private int mCurrentPage;

    @Inject
    public NewsListPresenter(GetNewsListUsecase newsUsecase) {
        mNewsUsecase = newsUsecase;
        mNewsSubscription = new CompositeSubscription();
        mCurrentPage = 1;
    }

    @Override
    public void onCreate() {
        mNewsView.showLoadingView();
        askForNews();
    }

    @Override
    public void onStart() {}

    @Override
    public void onStop() {}

    @Override
    public void onPause() {
        mNewsSubscription.unsubscribe();
        mIsTheNewsRequestRunning = false;
    }

    @Override
    public void attachView(Views v) {
        mNewsView = (NewsListView) v;
    }

    public void setFragmentIndex(int index) {
        mFragmentIndex = index;
    }

    public void onRefresh() {
        mCurrentPage = 1;
        askForNews();
    }

    public void onListEndReached(int currentPage) {
        if (!mIsTheNewsRequestRunning) {
            mCurrentPage = currentPage;
            askForMoreNews(mCurrentPage);
        }
    }

    private void askForNews() {
        mIsTheNewsRequestRunning = true;

        mNewsSubscription.add(mNewsUsecase.execute(String.valueOf(mFragmentIndex), String.valueOf(mCurrentPage))
                .subscribe(
                        news -> {
                            newsArrived();
                            mNewsView.setNewsList(news);
                        },
                        error ->
                            newsArrivedError(error))
        );

    }

    private void askForMoreNews(int currentPage) {
        mIsTheNewsRequestRunning = true;

        mNewsSubscription.add(mNewsUsecase.execute(String.valueOf(mFragmentIndex), String.valueOf(currentPage))
                .subscribe(
                        newNews -> {
                            newsArrived();
                            mNewsView.addNewsList(newNews);
                        },
                        error ->
                            newsArrivedError(error))
        );
    }

    private void newsArrived() {
        mNewsView.hideRefreshIndicator();
        mIsTheNewsRequestRunning = false;

        if (!mNewsView.isContentDisplayed()) {
            mNewsView.showNewsList();
        }
    }

    private void newsArrivedError(Throwable error) {
        mNewsView.hideRefreshIndicator();
        mIsTheNewsRequestRunning = false;
        showErrorView(error);
    }

    public void onElementClick(News news) {
        mNewsView.showDetailScreen(news);
    }

    private void showErrorView(Throwable error) {
        mNewsView.showErrorView();
    }

    public void onErrorRetryRequest() {
        onRefresh();
    }

}
