package com.guanchazhe.news.mvp.model.entities;

/**
 * Created by ran.zhang on 1/16/16.
 */
public class NewsHeader<T> {

    T instance;

    public NewsHeader(T t) {
        instance = t;
    }

    public T getInstance() {
        return instance;
    }

}
