package com.gyz.androiddevelope.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * @author: guoyazhou
 * @date: 2016-02-26 14:37
 */
public class ReUtil {


    public static ApiManagerService getApiManager(){

        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl("http://www.tngou.net/")
//        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.weather.com.cn/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiManagerService service = retrofit.create(ApiManagerService.class);

        return service;

    }


}
