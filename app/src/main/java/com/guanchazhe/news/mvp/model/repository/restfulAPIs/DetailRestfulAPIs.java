package com.guanchazhe.news.mvp.model.repository.restfulAPIs;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by ranzh on 1/8/2016.
 */
public interface DetailRestfulAPIs {

//    http://mobileservice.guancha.cn/Appdetail/get/?devices=android&id=251497
    @GET("/Appdetail/get/")
    Observable<String> getNewsDetail(
            @Query("devices") String devices,
            @Query("id") String id
    );

}
