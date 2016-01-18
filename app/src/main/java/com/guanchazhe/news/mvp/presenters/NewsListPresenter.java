package com.guanchazhe.news.mvp.presenters;

import com.guanchazhe.news.domain.GetNewsListUseCase;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.views.NewsListView;
import com.guanchazhe.news.mvp.views.Views;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by ranzh on 12/23/2015.
 */
public class NewsListPresenter implements Presenter {

    private final GetNewsListUseCase mNewsUsecase;
    private boolean mIsRequestRunning;
    private Subscription mNewsSubscription;

    private NewsListView mNewsView;
    private int mCurrentPage;

    @Inject
    public NewsListPresenter(GetNewsListUseCase newsUsecase) {
        mNewsUsecase = newsUsecase;
        mCurrentPage = 1;
    }

    @Override
    public void onCreate() {
        mNewsView.showLoadingView();
        sendRequest(Constant.RequestType.ASK);
    }

    @Override
    public void onStart() {}

    @Override
    public void onStop() {}

    @Override
    public void onPause() {
        mNewsSubscription.unsubscribe();
        mIsRequestRunning = false;
    }

    @Override
    public void attachView(Views v) {
        mNewsView = (NewsListView) v;
    }

    public void onRefresh() {
        mCurrentPage = 1;
        sendRequest(Constant.RequestType.ASK);
    }

    public void onListEndReached(int currentPage) {
        if (!mIsRequestRunning) {
            mCurrentPage = currentPage;
            sendRequest(Constant.RequestType.ASKMORE);
        }
    }

    private void sendRequest(Constant.RequestType requestType) {
        mIsRequestRunning = true;

        mNewsSubscription = mNewsUsecase.execute(String.valueOf(mCurrentPage))
                .subscribe(
                        news -> resultArrived(requestType, news)                        ,
                        error -> resultError(error)
                );
    }

    private void resultArrived(Constant.RequestType requestType, List<News> news) {
        mNewsView.hideRefreshIndicator();
        mIsRequestRunning = false;

        if (!mNewsView.isContentDisplayed()) {
            mNewsView.showNewsListView();
        }

        if (requestType == Constant.RequestType.ASK) {
            mNewsView.setNewsList(news);
        } else {
            mNewsView.addNewsList(news);
        }
    }

    private void resultError(Throwable error) {
        mNewsView.hideRefreshIndicator();
        mIsRequestRunning = false;
        mNewsView.showErrorView();
    }

    public void onElementClick(News news) {
        mNewsView.showDetailScreen(news);
    }

    public void onErrorRetryRequest() {
        onRefresh();
    }

}
