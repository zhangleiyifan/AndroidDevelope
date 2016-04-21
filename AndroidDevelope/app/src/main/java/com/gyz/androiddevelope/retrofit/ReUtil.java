package com.gyz.androiddevelope.retrofit;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.gyz.androiddevelope.engine.AppContants;

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

        OkHttpClient okHttpClient =  new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor()).addInterceptor(loggingInterceptor)
                .build();
//        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(AppContants.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiManagerService apiManagerService = retrofit.create(ApiManagerService.class);

        return apiManagerService;

    }






}
