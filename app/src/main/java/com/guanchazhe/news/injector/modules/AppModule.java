package com.guanchazhe.news.injector.modules;

import com.guanchazhe.news.NewsApplication;
import com.guanchazhe.news.mvp.model.repository.Repository;
import com.guanchazhe.news.mvp.model.repository.RestDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ranzh on 12/22/2015.
 */
@Module
public class AppModule {

    private final NewsApplication mNewsApplication;

    public AppModule(NewsApplication newsApplication) {

        this.mNewsApplication = newsApplication;
    }

    @Provides
    @Singleton
    NewsApplication provideNewsApplicationContext () { return mNewsApplication; }

    @Provides
    @Singleton
    Repository provideDataRepository (RestDataSource repository) { return repository; }
}
