package com.guanchazhe.news.domain;

import rx.Observable;

/**
 * Created by ranzh on 12/22/2015.
 */
public interface Usecase<T> {

    Observable<T> execute(String... parameters);
}
