package com.guanchazhe.news.injector.modules;

import com.guanchazhe.news.domain.GetNewsDetailUsecase;
import com.guanchazhe.news.injector.scopes.Activity;
import com.guanchazhe.news.model.repository.Repository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ranzh on 12/31/2015.
 */
@Module
public class NewsDetailModule {
    private final int mNewsId;

    public NewsDetailModule(int characterId) {
        mNewsId = characterId;
    }

    @Provides
    @Activity
    GetNewsDetailUsecase provideGetNewsDetailUsecase (Repository repository) {
        return new GetNewsDetailUsecase(mNewsId, repository);
    }
}
