package com.guanchazhe.news.injector.components;

import com.guanchazhe.news.domain.GetNewsListUsecase;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.injector.modules.NewsListModule;
import com.guanchazhe.news.injector.scopes.Activity;
import com.guanchazhe.news.views.activity.NewsListActivity;

import dagger.Component;

/**
 * Created by ranzh on 12/22/2015.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {NewsListModule.class, ActivityModule.class})
public interface NewsListComponent extends ActivityComponent {

    void inject (NewsListActivity activity);

    GetNewsListUsecase getNewsListUsecase();
}
