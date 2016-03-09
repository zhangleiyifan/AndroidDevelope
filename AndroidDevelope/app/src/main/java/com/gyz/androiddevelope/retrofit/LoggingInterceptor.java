package com.gyz.androiddevelope.retrofit;

import com.gyz.androiddevelope.util.L;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: guoyazhou
 * @date: 2016-03-03 14:16
 */
public class LoggingInterceptor implements Interceptor {
    private static final String TAG = "NETWORK";


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        long t1 = System.nanoTime();
        L.i(TAG, "Sending request=" + request.url() + "   connection=" + chain.connection() + "    head=" + request.headers()
                        + " request=" + request.body().toString()
        );

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        L.i(TAG, "Received response=" + response.request().url() + "   connect time=" + ((t2 - t1) / 1e6d) + "    head=" + response.headers()
                        + "    tostring" + response.toString()
        );


        return response;

    }
}
