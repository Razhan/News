package com.guanchazhe.news.mvp.presenters;

import com.guanchazhe.news.domain.GetNewsListUsecase;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.views.NewsListView;
import com.guanchazhe.news.mvp.views.Views;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ranzh on 12/23/2015.
 */
public class NewsListPresenter implements Presenter {

    private final GetNewsListUsecase mNewsUsecase;
    private boolean mIsTheNewsRequestRunning;
    private CompositeSubscription mNewsSubscription;

    private List<News> mNews;
    private NewsListView mNewsView;

    @Inject
    public NewsListPresenter(GetNewsListUsecase newsUsecase) {
        mNewsUsecase = newsUsecase;
        mNews = new ArrayList<>();
        mNewsSubscription = new CompositeSubscription();
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

    public void onRefresh() {
        askForNews();
    }

    @Override
    public void attachView(Views v) {
        mNewsView = (NewsListView) v;
    }

    public void onListEndReached() {
        if (!mIsTheNewsRequestRunning) {
            askForMoreNews();
        }
    }

    private void askForNews() {
        mIsTheNewsRequestRunning = true;

        mNewsSubscription.add(mNewsUsecase.execute()
                .subscribe(
                        news -> {
                            newsArrived();
                            mNewsView.setNewsList(news);
                        },
                        error ->
                            newsArrivedError(error))
        );

    }

    private void askForMoreNews() {
        mIsTheNewsRequestRunning = true;

        mNewsSubscription.add(mNewsUsecase.execute()
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
        if (mNews.isEmpty())
            askForNews();
        else
            askForMoreNews();
    }

}
