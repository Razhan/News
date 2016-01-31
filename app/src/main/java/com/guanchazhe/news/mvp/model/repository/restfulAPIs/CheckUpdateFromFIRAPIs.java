package com.guanchazhe.news.mvp.model.repository.restfulAPIs;

import com.guanchazhe.news.mvp.model.entities.VersionInfo;

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
    Observable<VersionInfo> checkUpdate(
            @Path("appid") String appid,
            @Query("api_token") String apptoken
    );
}
