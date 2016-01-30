package com.guanchazhe.news.mvp.model.repository.restfulAPIs;

import com.guanchazhe.news.mvp.model.entities.UpdateInfo;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by ran.zhang on 1/30/16.
 */
public interface CheckUpdateFromFIRAPIs {

    String END_POINT = "http://api.fir.im/";

    @GET("apps/latest/{appid}")
    Observable<UpdateInfo> checkUpdate(
            @Path("appid") String appid,
            @Query("apptoken") String apptoken
    );

}
