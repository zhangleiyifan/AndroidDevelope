package com.gyz.androiddevelope.engine;

import android.os.Environment;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author: guoyazhou
 * @date: 2016-01-22 15:21
 */
public class AppContants {

    public static final int ZHIHU_HTTP = 1001;
    public static final int TNGOU_HTTP = 1002;
    public static final int HUABAN_HTTP = 1003;

    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 101;

    public static final int LIMIT = 20;

    public static final String FIRST_LOAD = "first_load";

    public static final String SP_LOAD_PIC_BY_MOBILE_NET = "load_pic_with_mobile_network";

    public static final boolean isDebug = true;

    public static final String BUGLY_APP_ID = "900021343";

    public static final String BASE_URL_ZHIHU = "http://news-at.zhihu.com/api/4/";
    public static final String BASE_URL_TNGOU = "http://www.tngou.net/";
    public static final String BASE_URL_HUABAN = "https://api.huaban.com/";

    //    仅为标题
    public static final int TITLE_TYPE = 101;

    public static final String READ_ID = "read_id";
    public static final int LATEST_COLUMN = Integer.MAX_VALUE;
    public static final int DATABASE_VERSION = 11;

    /**
     * 缓存文件路径
     */
    public static final String CACHE_PATH = Environment.getExternalStorageDirectory().getPath() + "/AndroidDevelop/data/";

    public static final String NEED_CALLBACK = "need_callback";

    public static final String TG_IMAGE_HEAD = "http://tnfs.tngou.net/image";
//===========知乎==begin===================================================================

    // loading 图片
    public static final String START_IMAG = "start-image/720*1184";
    //最新消息
    public static final String LATEST_NEWS = "news/latest";
    //历史消息
    public static final String BEFORE_NEWS = "news/before/{date}";
    //消息内容详情
    public static final String DETAIL_NEWS = "news/{id}";
    //新闻额外内容
    public static final String EXTRA_NEWS = "story-extra/{id}";

//===========天狗云==begin=====================================================================

    //热点分类接口
    public static final String GALLERY_CLASS = "tnfs/api/classify";

    //图片列表接口
    public static final String GALLERY_BEAN_LIST = "tnfs/api/list";

    //相册详情列表接口
    public static final String ALBUM_DETAIL_LIST = "tnfs/api/show";

    //==========花瓣=========================================================================
}
