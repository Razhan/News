package com.guanchazhe.news;

import android.app.Application;

import com.guanchazhe.news.injector.components.AppComponent;
import com.guanchazhe.news.injector.components.DaggerAppComponent;
import com.guanchazhe.news.injector.modules.AppModule;

import im.fir.sdk.FIR;

/**
 * Created by ranzh on 12/22/2015.
 */
public class NewsApplication extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {

        super.onCreate();
        FIR.init(this);
        initializeInjector();
    }

    private void initializeInjector() {

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {

        return mAppComponent;
    }
}
