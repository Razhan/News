package com.guanchazhe.news.injector.components;

import com.guanchazhe.news.domain.GetCommentaryListUsecase;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.injector.modules.CommentaryListModule;
import com.guanchazhe.news.injector.scopes.Activity;
import com.guanchazhe.news.views.activities.CommentaryListActivity;

import dagger.Component;

/**
 * Created by ranzh on 1/6/2016.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {CommentaryListModule.class, ActivityModule.class})
public interface CommentaryListComponent extends ActivityComponent {

    void inject (CommentaryListActivity collectionActivity);

    GetCommentaryListUsecase getCommentaryCollectionUsecase();
}