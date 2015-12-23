package com.guanchazhe.news.injector.components;

import android.content.Context;

import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.injector.scopes.Activity;

import dagger.Component;

/**
 * Created by ranzh on 12/22/2015.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface NewsComponent extends ActivityComponent {
//    void inject (CharacterListListActivity activity);

    Context activityContext();
}
