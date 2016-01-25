package com.gyz.androiddevelope.engine;

import android.os.Environment;

/**
 * @author: guoyazhou
 * @date: 2016-01-22 15:21
 */
public class AppContants {

    public static final int  WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 101;

    /**
     * 缓存文件路径
     */
    public static final String CACHE_PATH  =  Environment.getExternalStorageDirectory().getPath() + "/AndroidDevelop/data/";

    public static final String NEED_CALLBACK = "need_callback";
}
