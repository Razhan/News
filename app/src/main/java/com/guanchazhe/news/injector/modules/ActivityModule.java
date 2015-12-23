package com.guanchazhe.news.injector.modules;

import android.content.Context;

import com.guanchazhe.news.injector.scopes.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ranzh on 12/22/2015.
 */
@Module
public class ActivityModule {

    private final Context mContext;

    public ActivityModule(Context mContext) {

        this.mContext = mContext;
    }

    @Provides
    @Activity
    Context provideActivityContext() {
        return mContext;
    }
}
