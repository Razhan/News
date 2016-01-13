package com.guanchazhe.news.mvp.presenters;

import com.guanchazhe.news.domain.GetNewsListUsecase;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.views.NewsListView;
import com.guanchazhe.news.mvp.views.Views;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ranzh on 12/23/2015.
 */
public class NewsListPresenter implements Presenter {

    private final GetNewsListUsecase mNewsUsecase;
    private boolean mIsTheNewsRequestRunning;
    private Subscription mNewsSubscription;

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
        askForNews(RequestType.ASK);
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
        askForNews(RequestType.ASK);
    }

    public void onListEndReached(int currentPage) {
        if (!mIsTheNewsRequestRunning) {
            mCurrentPage = currentPage;
            askForNews(RequestType.ASKMORE);
        }
    }

    private void askForNews(RequestType requestType) {
        mIsTheNewsRequestRunning = true;

        mNewsSubscription = mNewsUsecase.execute(String.valueOf(mFragmentIndex), String.valueOf(mCurrentPage))
                .subscribe(
                        news -> newsArrived(requestType, news)                        ,
                        error -> newsError(error)
                );
    }

    private void newsArrived(RequestType requestType, List<News> news) {
        mNewsView.hideRefreshIndicator();
        mIsTheNewsRequestRunning = false;

        if (!mNewsView.isContentDisplayed()) {
            mNewsView.showNewsList();
        }

        if (requestType == RequestType.ASK) {
            mNewsView.setNewsList(news);
        } else {
            mNewsView.addNewsList(news);
        }
    }

    private void newsError(Throwable error) {
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

    private enum RequestType {
        ASK,
        ASKMORE
    }

}
