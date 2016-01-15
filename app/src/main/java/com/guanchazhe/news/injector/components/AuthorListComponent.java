package com.guanchazhe.news.injector.components;

import com.guanchazhe.news.domain.GetAuthorListUsecase;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.injector.modules.AuthorListModule;
import com.guanchazhe.news.injector.scopes.Activity;
import com.guanchazhe.news.views.activity.AuthorListActivity;

import dagger.Component;

/**
 * Created by ranzh on 1/14/2016.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {AuthorListModule.class, ActivityModule.class})
public interface AuthorListComponent extends ActivityComponent {

    void inject (AuthorListActivity activity);

    GetAuthorListUsecase getAuthorListUsecase();
}