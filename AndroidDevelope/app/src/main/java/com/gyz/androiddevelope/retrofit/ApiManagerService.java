package com.gyz.androiddevelope.retrofit;

import com.gyz.androiddevelope.bean.Axiba;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author: guoyazhou
 * @date: 2016-02-19 10:55
 */
public interface ApiManagerService {

    @FormUrlEncoded
    @POST("data/sk/101010100.html")
    Observable<Axiba> getWeather(@Field("cityId") String cityId,@Field("cityName") String cityName);

}
