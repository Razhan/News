package com.guanchazhe.news.mvp.presenters;

import com.guanchazhe.news.domain.GetNewsDetailUsecase;
import com.guanchazhe.news.mvp.views.Views;


import javax.inject.Inject;

/**
 * Created by ranzh on 12/31/2015.
 */
public class NewsDetailPresenter implements Presenter {

    private final GetNewsDetailUsecase mNewsDetailUsecase;


    @Inject
    public NewsDetailPresenter(GetNewsDetailUsecase newsDetailUsecase) {
        mNewsDetailUsecase = newsDetailUsecase;
    }


    @Override
    public void onStart() {}

    @Override
    public void onStop(){}

    @Override
    public void onPause() {}

    @Override
    public void attachView (Views v) {}

    @Override
    public void onCreate() {}
}
