package com.guanchazhe.news.mvp.presenters;

import com.guanchazhe.news.domain.GetCommentaryListUsecase;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.views.CommentaryListView;
import com.guanchazhe.news.mvp.views.Views;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by ranzh on 1/6/2016.
 */
public class CommentaryListPresenter implements Presenter {

    private final GetCommentaryListUsecase mCommentaryCollectionUsecase;
    private boolean mIsRequestRunning;
    private Subscription mCommentariesSubscription;
    private int mCurrentPage;
    private CommentaryListView mCommentaryListView;

    @Inject
    public CommentaryListPresenter(GetCommentaryListUsecase getCommentaryListUsecase) {
        mCommentaryCollectionUsecase = getCommentaryListUsecase;
        mCurrentPage = 1;
    }

    @Override
    public void onCreate() {
        mCommentaryListView.showLoadingView();
        sendRequest(Constant.RequestType.ASK);
    }

    @Override
    public void attachView(Views v) {
        mCommentaryListView = (CommentaryListView) v;
    }

    @Override public void onStart() {}

    @Override public void onStop() {}

    @Override
    public void onPause() {
        mCommentariesSubscription.unsubscribe();
        mIsRequestRunning = false;
    }

    public void onListEndReached(int currentPage) {
        if (!mIsRequestRunning) {
            mCurrentPage = currentPage;
            sendRequest(Constant.RequestType.ASKMORE);
        }
    }

    private void sendRequest(Constant.RequestType requestType) {
        mCommentariesSubscription = mCommentaryCollectionUsecase.execute(String.valueOf(mCurrentPage))
                .map(commentaries -> fixImageURL(commentaries))
                .subscribe(
                        commentaries -> resultArrived(requestType, commentaries),
                        error -> resultError(error)
                );
    }

    private void resultArrived(Constant.RequestType requestType, List<News> commentaries) {
        mIsRequestRunning = false;

        if (!mCommentaryListView.isContentDisplayed()) {
            mCommentaryListView.showNewsListView();
        }

        if (requestType == Constant.RequestType.ASK) {
            mCommentaryListView.setCommentaryList(commentaries);
        } else {
            mCommentaryListView.addCommentaryList(commentaries);
        }
    }

    private void resultError(Throwable error) {
        error.printStackTrace();
        mIsRequestRunning = false;

        if (error instanceof com.google.gson.JsonSyntaxException) {
            mCommentaryListView.showNewsListView();
        } else {
            mCommentaryListView.showErrorView();
        }
    }

    private List<News> fixImageURL(List<News> items) {

        for (News item : items) {
            item.setPic("http://i.guancha.cn/" + item.getPic());
        }
        return items;
    }

    public void onElementClick(News commentary) {
        mCommentaryListView.showDetailScreen(commentary);
    }

}
