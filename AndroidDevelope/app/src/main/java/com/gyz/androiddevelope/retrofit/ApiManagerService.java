package com.gyz.androiddevelope.retrofit;

import com.gyz.androiddevelope.response_bean.Axiba;
import com.gyz.androiddevelope.response_bean.HealthInfoList;
import com.gyz.androiddevelope.response_bean.InfoList;
import com.gyz.androiddevelope.response_bean.Tngou;
import com.gyz.androiddevelope.response_bean.UserInfo;

import com.gyz.androiddevelope.request_bean.ReqHealthInfoList;
import com.gyz.androiddevelope.request_bean.ReqUserInfoBean;
import com.gyz.androiddevelope.request_bean.ReqWeatherBean;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author: guoyazhou
 * @date: 2016-02-19 10:55
 */
public interface ApiManagerService {

    @FormUrlEncoded
    @POST("/data/sk/101010100.html")
    Observable<Axiba> getWeather(@Field("cityId") String cityId,@Field("cityName") String cityName);

    @POST("/data/sk/101010100.html")
    Observable<Axiba> getWeather2(@Body ReqWeatherBean reqWeatherBean);

    @POST("api/user")
    Observable<UserInfo> getUserInfo(@Body ReqUserInfoBean bean);

//    @FormUrlEncoded
//    @POST("api/user")
//    Observable<UserInfo> getUserInfo(@Field("access_token") String access_token);

    @GET("api/user")
    Observable<UserInfo> getUserInfo(@Query("access_token") String access_token);

    @POST("api/info/classify")
    Observable<Tngou> getInfoList();


    @GET("api/info/list")
    Observable<InfoList> getHealthInfoList(@Query("id") int id,@Query("rows") int rows,@Query("page")int page);

    @POST("api/info/list")
    Observable<InfoList> getHealthInfoList(@Body ReqHealthInfoList list);

    @POST("api/info/list")
    Observable<HealthInfoList> getHealthNewsInfoList(@Body ReqHealthInfoList list );



}
