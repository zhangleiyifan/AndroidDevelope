package com.gyz.androiddevelope.util.gifload;

import android.content.Context;

import java.io.InputStream;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.util.gifload.GiftUtils.java
 * @author: ZhaoHao
 * @date: 2016-05-24 10:26
 */
public class GiftUtils {
    private static final String TAG = "GiftUtils";

    private static GiftUtils instance;

    private GiftUtils() {

    }

    public static GiftUtils with(Context context) {
        if (instance == null) {
            synchronized (GiftUtils.class) {
                instance = new GiftUtils();
            }
        }
        return instance;
    }

    public GifDraw load(InputStream inputStream){



        return  null ;
    }



}
