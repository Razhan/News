package com.guanchazhe.news.mvp.views;

import com.guanchazhe.news.mvp.model.entities.VersionInfo;

/**
 * Created by ran.zhang on 1/30/16.
 */
public interface MainView extends Views {

    void showUpdateDialog(VersionInfo info);

    void showUpdateToast(String res);





}
