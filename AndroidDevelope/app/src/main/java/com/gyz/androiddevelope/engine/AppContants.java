package com.gyz.androiddevelope.engine;

import android.os.Environment;

/**
 * @author: guoyazhou
 * @date: 2016-01-22 15:21
 */
public class AppContants {

    public static final int  WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 101;

    public static final String FIRST_LOAD="first_load";

    public static final boolean isDebug = true;

    public static final String BUGLY_APP_ID = "900021343";

    public static final String BASE_URL="http://news-at.zhihu.com/api/4/";

    public static final int TITLE_TYPE = 101;

    public static final String READ_ID = "read_id";
    public static final int LATEST_COLUMN = Integer.MAX_VALUE;
    public static final int DATABASE_VERSION = 11;

    /**
     * 缓存文件路径
     */
    public static final String CACHE_PATH  =  Environment.getExternalStorageDirectory().getPath() + "/AndroidDevelop/data/";

    public static final String NEED_CALLBACK = "need_callback";

    public static final String IMAGE_HEAD="http://tnfs.tngou.net/image";
//===========知乎==begin===================================================================

    // loading 图片
    public static final String START_IMAG = "start-image/720*1184";
    //最新消息
    public static final String LATEST_NEWS = "news/latest";
    //历史消息
    public static final String BEFORE_NEWS = "news/before/{date}";
    //消息内容详情
    public static final String DETAIL_NEWS = "news/{id}";


//===========知乎==end=====================================================================
}
