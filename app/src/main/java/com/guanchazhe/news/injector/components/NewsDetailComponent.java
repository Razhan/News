package com.guanchazhe.news.injector.components;

import com.guanchazhe.news.domain.GetNewsDetailUseCase;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.injector.modules.NewsDetailModule;
import com.guanchazhe.news.injector.scopes.Activity;
import com.guanchazhe.news.views.activity.CommentaryDetailActivity;
import com.guanchazhe.news.views.activity.NewsDetailActivity;

import dagger.Component;

/**
 * Created by ranzh on 12/31/2015.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {NewsDetailModule.class, ActivityModule.class})
public interface NewsDetailComponent extends ActivityComponent {

    void inject (NewsDetailActivity detailActivity);
    void inject (CommentaryDetailActivity detailActivity);

    GetNewsDetailUseCase getNewsDetailUsecase();
}
