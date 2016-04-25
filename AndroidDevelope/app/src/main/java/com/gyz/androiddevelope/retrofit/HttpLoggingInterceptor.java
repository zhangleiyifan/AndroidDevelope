package com.gyz.androiddevelope.retrofit;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;
/**
 * <br/>
 * Created by Ryan on 2015/12/31.
 */
public class HttpLoggingInterceptor implements Interceptor {
    private static final String TAG = "debug";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        long t1 = System.nanoTime();

        Buffer buffer = new Buffer();
        request.body().writeTo(buffer);

        Log.e(TAG, String.format("request: %s",
                request.url(), chain.connection(), request.headers(), buffer.clone().readUtf8()));
        buffer.close();

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        BufferedSource source = response.body().source();
        source.request(Long.MAX_VALUE);
        buffer = source.buffer().clone();
        Log.e(TAG, String.format("Received response for %s in %.1fms%n%sResponse Json: %s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers(),
                buffer.readUtf8()));

        return response;
    }
}