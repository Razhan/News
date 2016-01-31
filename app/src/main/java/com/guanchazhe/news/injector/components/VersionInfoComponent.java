package com.guanchazhe.news.injector.components;

import com.guanchazhe.news.domain.GetVersionInfoUseCase;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.injector.modules.VersionInfoModule;
import com.guanchazhe.news.injector.scopes.Activity;
import com.guanchazhe.news.views.activity.MainActivity;

import dagger.Component;

/**
 * Created by ran.zhang on 1/30/16.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {VersionInfoModule.class, ActivityModule.class})
public interface VersionInfoComponent {

    void inject (MainActivity activity);

    GetVersionInfoUseCase getVersionInfoUseCase();
}