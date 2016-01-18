package com.guanchazhe.news.mvp;

import com.guanchazhe.news.R;

/**
 * Created by ranzh on 1/14/2016.
 */
public class Constant {

    public enum RequestType {
        ASK,
        ASKMORE
    }

    public enum NewsType {
        NEWS,
        COMMENTARY,
        HBRID
    }



    public static final int ANIMATOR_VIEW_LOADING = R.id.view_loading;
    public static final int ANIMATOR_VIEW_ERROR = R.id.view_error;
}
