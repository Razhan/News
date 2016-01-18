package com.guanchazhe.news.mvp.presenters;

import android.view.View;
import android.widget.ImageView;

import com.guanchazhe.news.R;
import com.guanchazhe.news.domain.GetAuthorListUseCase;
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

    private final GetAuthorListUseCase mAuthorUsecase;
    private Subscription mAuthorListSubscription;
    private AuthorListView mAuthorListView;

    @Inject
    public AuthorListPresenter(GetAuthorListUseCase authorListUsecase) {
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

    public void onElementClick(View view, Author author) {

        ImageView imageView = (ImageView)view.findViewById(R.id.author_image);
        imageView.setTransitionName("image_shared");

        mAuthorListView.showDetailScreen(imageView, author);
    }

}
