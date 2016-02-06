package com.guanchazhe.news.mvp.presenters;

import android.content.Context;
import android.view.View;

import com.guanchazhe.news.domain.GetNewsListUseCase;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.model.repository.dataBase.ObservableRepoDb;
import com.guanchazhe.news.mvp.views.NewsListView;
import com.guanchazhe.news.mvp.views.Views;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
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
    private Context mContext;
    private ObservableRepoDb mObservableRepoDb;

    @Inject
    public NewsListPresenter(Context context, GetNewsListUseCase newsUsecase) {
        mNewsUsecase = newsUsecase;
        mCurrentPage = 1;
        mContext = context;

        mObservableRepoDb = new ObservableRepoDb(mContext);
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
            mCurrentPage++;
            sendRequest(Constant.RequestType.ASKMORE);
        }
    }

    private void sendRequest(Constant.RequestType requestType) {
        mIsRequestRunning = true;

        mNewsSubscription = Observable
                .concat(
                        mObservableRepoDb.getObservable(mNewsView.getNewsType(), mCurrentPage),
                        mNewsUsecase.execute(String.valueOf(mCurrentPage)))
                .first(news -> news != null
                        && news.size() > 0
                        && news.get(0).isUpToDate())
                .subscribe(
                        news -> resultArrived(requestType, news),
                        error -> resultError(error));
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

        if (mCurrentPage <= 1 && !mNewsView.getNewsType().equals(Constant.NewsType.HYBRID)) {
            mObservableRepoDb.insertRepoListWithType(news, mNewsView.getNewsType());
        }
    }

    private void resultError(Throwable error) {
        mNewsView.hideRefreshIndicator();
        mIsRequestRunning = false;
        mNewsView.showErrorView();
    }

    public void onElementClick(View view, News news) {
        mNewsView.hideStatusIndicator(view);
        mNewsView.showDetailScreen(news);
    }

    public void onErrorRetryRequest() {
        onRefresh();
    }

}
