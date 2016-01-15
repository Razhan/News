package com.guanchazhe.news.mvp.presenters;

import com.guanchazhe.news.domain.GetAuthorListUsecase;
import com.guanchazhe.news.mvp.model.entities.Author;
import com.guanchazhe.news.mvp.views.AuthorListView;
import com.guanchazhe.news.mvp.views.Views;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by ranzh on 1/14/2016.
 */
public class AuthorListPresenter implements Presenter {

    private final GetAuthorListUsecase mAuthorUsecase;
    private Subscription mAuthorListSubscription;
    private AuthorListView mAuthorListView;

    @Inject
    public AuthorListPresenter(GetAuthorListUsecase authorListUsecase) {
        mAuthorUsecase = authorListUsecase;
    }

    @Override
    public void onCreate() {
        mAuthorListView.showLoadingView();
        askForAuthors();
    }

    @Override
    public void onStart() {}

    @Override
    public void onStop() {}

    @Override
    public void onPause() {
        mAuthorListSubscription.unsubscribe();
    }
    @Override
    public void attachView(Views v) {
        mAuthorListView = (AuthorListView) v;
    }

    private void askForAuthors() {

        mAuthorListSubscription = mAuthorUsecase.execute()
                .subscribe(
                        authors -> resultArrived(authors)                        ,
                        error -> resultError(error)
                );
    }

    private void resultArrived(List<Author> authors) {
        mAuthorListView.showAuthorListView();
        mAuthorListView.setAuthorList(authors);
    }

    private void resultError(Throwable error) {
        mAuthorListView.showErrorView();
    }

    public void onElementClick(Author author) {
        mAuthorListView.showDetailScreen(author);
    }

}
