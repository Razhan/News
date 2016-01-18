package com.guanchazhe.news.injector.modules;

import com.guanchazhe.news.domain.GetNewsDetailUseCase;
import com.guanchazhe.news.injector.scopes.Activity;
import com.guanchazhe.news.mvp.model.repository.Repository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ranzh on 12/31/2015.
 */
@Module
public class NewsDetailModule {
    private final int mNewsId;

    public NewsDetailModule(int Id) {
        mNewsId = Id;
    }

    @Provides
    @Activity
    GetNewsDetailUseCase provideGetNewsDetailUsecase (Repository repository) {
        return new GetNewsDetailUseCase(mNewsId, repository);
    }
}
