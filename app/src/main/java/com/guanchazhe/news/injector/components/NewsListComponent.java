package com.guanchazhe.news.injector.components;

import com.guanchazhe.news.domain.GetNewsListUseCase;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.injector.modules.NewsListModule;
import com.guanchazhe.news.injector.scopes.Activity;
import com.guanchazhe.news.views.fragment.ListFragment;

import dagger.Component;

/**
 * Created by ranzh on 12/22/2015.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {NewsListModule.class, ActivityModule.class})
public interface NewsListComponent extends ActivityComponent {

    void inject (ListFragment fragment);

    GetNewsListUseCase getNewsListUsecase();
}
