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
    public void onStart() {
        // Unused
    }

    @Override
    public void onStop() {
        // Unused
    }

    @Override
    public void onPause() {
        mNewsView.hideLoadingMoreNewsIndicator();
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
        mNewsView.hideErrorView();
        mNewsView.showLoadingView();

        mNewsSubscription = mNewsUsecase.execute()
                .subscribe(
                        characters -> {
                            mNews.addAll(characters);
                            mNewsView.bindNewsList(mNews);
                            mNewsView.showNewsList();
                            mNewsView.hideEmptyIndicator();
                            mIsTheNewsRequestRunning = false;
                        },
                        error -> {
                            mIsTheNewsRequestRunning = false;
                            showErrorView(error);
                        });

    }

    private void askForMoreNews() {
        mNewsView.showLoadingMoreNewsIndicator();
        mIsTheNewsRequestRunning = true;

        mNewsSubscription = mNewsUsecase.execute()
                .subscribe(new NewsListSubscriber());
    }

    private void showErrorView(Throwable error) {
//        if (error instanceof NetworkUknownHostException) {
//            mNewsView.showConnectionErrorMessage();
//
//        } else if (error instanceof ServerErrorException) {
//            mNewsView.showServerErrorMessage();
//
//        } else {
//            mNewsView.showUnknownErrorMessage();
//        }

        mNewsView.showUnknownErrorMessage();

        mNewsView.hideLoadingMoreNewsIndicator();
        mNewsView.hideEmptyIndicator();
        mNewsView.hideNewsList();
    }

    private void showGenericError() {
        mNewsView.hideLoadingIndicator();
        mNewsView.showLightError();
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

//    @RxLogSubscriber
    public class NewsListSubscriber extends Subscriber<List<News>> {

        @Override
        public void onNext(List<News> newNews) {
            mNews.addAll(newNews);
            mNewsView.updateNewsList(GetNewsListUsecase.NEWS_LIMIT);
            mNewsView.hideLoadingIndicator();
            mIsTheNewsRequestRunning = false;
        }

        @Override
        public void onError(Throwable throwable) {
            mIsTheNewsRequestRunning = false;
            showGenericError();
        }

        @Override
        public void onCompleted() {
        }
    }

}
