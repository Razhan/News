package com.guanchazhe.news.mvp.presenters;

import android.content.Context;

import com.guanchazhe.news.domain.GetCommentaryListUsecase;
import com.guanchazhe.news.mvp.model.entities.Commentary;
import com.guanchazhe.news.mvp.views.CommentaryListView;
import com.guanchazhe.news.mvp.views.Views;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ranzh on 1/6/2016.
 */
public class CommentaryListPresenter implements Presenter {

    private final GetCommentaryListUsecase mCommentaryCollectionUsecase;
    private final Context mActivityContext;
    private String mAuthorName;
    private CommentaryListView mCommentaryListView;

    @Inject
    public CommentaryListPresenter(GetCommentaryListUsecase getCommentaryListUsecase,
                                   Context activityContext) {
        mCommentaryCollectionUsecase = getCommentaryListUsecase;
        mActivityContext = activityContext;
    }

    @Override public void onStart() {}

    @Override public void onStop() {}

    @Override
    public void onPause() {}

    @Override
    public void attachView(Views v) {
        mCommentaryListView = (CommentaryListView) v;
    }

    @Override
    public void onCreate() {
        mCommentaryCollectionUsecase.execute(mAuthorName)
                .map(this::fixImageURL)
                .subscribe(
                        this::onCollectionItemsReceived,
                        this::manageCollectionItemsError
                );
    }

    public void initialisePresenters(String authorName) {
        mAuthorName = authorName;
    }

    private void onCollectionItemsReceived(List<Commentary> items) {
        mCommentaryListView.hideLoadingIndicator();
        mCommentaryListView.showItems(items);
    }

    private void manageCollectionItemsError(Throwable error) {
        // TODO
    }

    private List<Commentary> fixImageURL(List<Commentary> items) {

        for (Commentary item : items) {
            item.setPic("http://i.guancha.cn/" + item.getPic());
        }

        return items;
    }
}
