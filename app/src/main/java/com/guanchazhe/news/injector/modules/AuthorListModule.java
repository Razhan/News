package com.guanchazhe.news.injector.modules;

import com.guanchazhe.news.domain.GetAuthorListUsecase;
import com.guanchazhe.news.injector.scopes.Activity;
import com.guanchazhe.news.mvp.model.repository.Repository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ranzh on 1/14/2016.
 */
@Module
public class AuthorListModule {

    @Provides
    @Activity
    GetAuthorListUsecase provideGetNewsListUsecase (Repository repository) {
        return new GetAuthorListUsecase(repository);
    }
}
