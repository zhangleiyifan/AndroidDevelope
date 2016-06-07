package com.gyz.androiddevelope.retrofit;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.gyz.androiddevelope.engine.AppContants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * @author: guoyazhou
 * @date: 2016-02-26 14:37
 */
public class ReUtil {

    public static ApiManagerService getApiManager(int httpType){

        String baseUrl=AppContants.BASE_URL_ZHIHU;

        switch (httpType){
            case AppContants.ZHIHU_HTTP:
                baseUrl = AppContants.BASE_URL_ZHIHU;
                break;

            case AppContants.TNGOU_HTTP:
                baseUrl = AppContants.BASE_URL_TNGOU;
                break;

            case AppContants.HUABAN_HTTP:
                baseUrl = AppContants.BASE_URL_HUABAN;
                break;
        }

//        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();
//        OkHttpClient okHttpClient =  new OkHttpClient.Builder()
//                .addNetworkInterceptor(new StethoInterceptor()).addInterceptor(loggingInterceptor)
//                .build();
//        Log 打印
        HttpLoggingInterceptor loggingInterceptor1 = new HttpLoggingInterceptor();
        loggingInterceptor1.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).addInterceptor(loggingInterceptor1).build();


        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(ApiManagerService.class);

    }

}
