package com.guanchazhe.news.injector.components;

import com.guanchazhe.news.NewsApplication;
import com.guanchazhe.news.injector.modules.AppModule;
import com.guanchazhe.news.mvp.model.repository.Repository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ranzh on 12/22/2015.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    NewsApplication app();

    Repository dataRepository();
}