package com.guanchazhe.news.injector.modules;

import com.guanchazhe.news.domain.GetVersionInfoUseCase;
import com.guanchazhe.news.injector.scopes.Activity;
import com.guanchazhe.news.mvp.model.repository.Repository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ran.zhang on 1/30/16.
 */
@Module
public class VersionInfoModule {

    public VersionInfoModule() {}

    @Provides
    @Activity
    GetVersionInfoUseCase provideGetVersionInfoUseCase (Repository repository) {
        return new GetVersionInfoUseCase(repository);
    }
}
