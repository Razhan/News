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

/**
 * Created by ranzh on 12/23/2015.
 */
public class NewsListPresenter implements Presenter {

    private final GetNewsListUsecase mNewsUsecase;
    private boolean mIsTheNewsRequestRunning;
    private Subscription mNewsSubscription;

    private List<News> mNews;
    private NewsListView mNewsView;

    @Inject
    public NewsListPresenter(GetNewsListUsecase newsUsecase) {
        mNewsUsecase = newsUsecase;
        mNews = new ArrayList<News>();
    }

    @Override
    public void onCreate() {
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

    public void onListEndReached() {
        if (!mIsTheNewsRequestRunning) {
            askForMoreNews();
        }
    }

    private void askForNews() {
        mIsTheNewsRequestRunning = true;
        mNewsView.showLoadingView();

        mNewsSubscription = mNewsUsecase.execute()
                .subscribe(
                        characters -> {
                            mNews.addAll(characters);
                            mNewsView.hideRefreshIndicator();
                            mNewsView.bindNewsList(mNews);
                            mNewsView.showNewsList();
                            mIsTheNewsRequestRunning = false;
                        },
                        error -> {
                            mNewsView.hideRefreshIndicator();
                            mIsTheNewsRequestRunning = false;
                            showErrorView(error);
                        });

    }

    private void askForMoreNews() {
        mIsTheNewsRequestRunning = true;

        mNewsSubscription = mNewsUsecase.execute()
                .subscribe(
                        newNews -> {
                            mNews.addAll(newNews);
                            mNewsView.hideRefreshIndicator();
                            mNewsView.updateNewsList(GetNewsListUsecase.NEWS_LIMIT);
                            mIsTheNewsRequestRunning = false;
                        },
                        error -> {
                            mNewsView.hideRefreshIndicator();
                            mIsTheNewsRequestRunning = false;
                            showErrorView(error);
                        });
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

    public void onElementClick(int position) {
        mNewsView.showDetailScreen(mNews.get(position));
    }


}
