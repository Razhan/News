package com.guanchazhe.news.injector.modules;

import com.guanchazhe.news.domain.GetNewsListUsecase;
import com.guanchazhe.news.injector.scopes.Activity;
import com.guanchazhe.news.mvp.model.repository.Repository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ranzh on 12/31/2015.
 */
@Module
public class NewsListModule {

    @Provides
    @Activity
    GetNewsListUsecase provideGetNewsListUsecase (Repository repository) {
        return new GetNewsListUsecase(repository);
    }
}
