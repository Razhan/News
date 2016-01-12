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

//    http://mobileservice.guancha.cn/app/GetAuthorList/
//"id": "MeiXinYu",
//"name": "梅新育",
//"summary": "商务部国际贸易经济合作研究院研究员",
//"pic": "http://i.guancha.cn/ColumnPic/508281bc-f1e5-4221-b729-fa5b5cdc0c78.png",
//"spelling": "MeiXinYu"

//    @GET("endpoint")
//    @QueryParam(name="constantVariable", value="constantValue")
//    void normalGet(@Named("page") int page, Callback<MyResponse> callback);



}
