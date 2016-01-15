package com.guanchazhe.news.injector.components;

import com.guanchazhe.news.domain.GetNewsDetailUsecase;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.injector.modules.NewsDetailModule;
import com.guanchazhe.news.injector.scopes.Activity;
import com.guanchazhe.news.views.activity.NewsDetailActivity;
import com.guanchazhe.news.views.activity.NewsDetailNewActivity;

import dagger.Component;

/**
 * Created by ranzh on 12/31/2015.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {NewsDetailModule.class, ActivityModule.class})
public interface NewsDetailComponent extends ActivityComponent {

    void inject (NewsDetailActivity detailActivity);
    void inject (NewsDetailNewActivity detailActivity);

    GetNewsDetailUsecase getNewsDetailUsecase();
}
