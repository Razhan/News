package com.guanchazhe.news.injector.modules;

import com.guanchazhe.news.domain.GetNewsListUseCase;
import com.guanchazhe.news.injector.scopes.Activity;
import com.guanchazhe.news.mvp.model.repository.Repository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ranzh on 12/31/2015.
 */
@Module
public class NewsListModule {

    public final int mAttributeId;
    public final int mChannelId;

    public NewsListModule(int channelId, int attributeId) {
        mChannelId = channelId;
        mAttributeId = attributeId;
    }

    @Provides
    @Activity
    GetNewsListUseCase provideGetNewsListUsecase (Repository repository) {
        return new GetNewsListUseCase(repository, mAttributeId, mChannelId);
    }
}
