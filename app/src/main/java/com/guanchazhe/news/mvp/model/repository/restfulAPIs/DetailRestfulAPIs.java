package com.guanchazhe.news.mvp.model.repository.restfulAPIs;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by ranzh on 1/8/2016.
 */
public interface DetailRestfulAPIs {

    @GET("/Appdetail/get/")
    Observable<String> getNewsDetail(
            @Query("devices") String devices,
            @Query("id") String id
    );


//    @GET("endpoint")
//    @QueryParam(name="constantVariable", value="constantValue")
//    void normalGet(@Named("page") int page, Callback<MyResponse> callback);



}
