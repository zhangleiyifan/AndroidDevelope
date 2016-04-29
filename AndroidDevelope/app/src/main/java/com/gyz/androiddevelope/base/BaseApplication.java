package com.gyz.androiddevelope.base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.gyz.androiddevelope.cache.CacheManager;
import com.gyz.androiddevelope.db.CacheDbHelper;
import com.gyz.androiddevelope.db.TngouDbHelper;
import com.gyz.androiddevelope.db.TngouListDbHelper;
import com.gyz.androiddevelope.db.WebCacheDbHelper;
import com.gyz.androiddevelope.engine.AppContants;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * @author: guoyazhou
 * @date: 2016-01-19 17:21
 */
public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    private static BaseApplication instance;
    private static TngouDbHelper tngouDbHelper;
    private static CacheDbHelper cacheDbHelper;
    private static WebCacheDbHelper webCacheDbHelper;
    private static TngouListDbHelper tngouListDbHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Stetho.initializeWithDefaults(this);
//        Fresco.initialize(this, ConfigConstants.getImagePipelineConfig(this));
        Fresco.initialize(this);
        CrashReport.initCrashReport(getApplicationContext(), AppContants.BUGLY_APP_ID, false);
        CacheManager.getInstance().initCacheDir();
    }

    public static BaseApplication getInstantce(){
        return instance;
    }

    public TngouDbHelper getTngouDbHelper(){
        if (tngouDbHelper==null){
            tngouDbHelper = new TngouDbHelper(this,AppContants.DATABASE_VERSION);
        }
        return tngouDbHelper;
    }

    public CacheDbHelper getCacheDbHelper(){
        if (cacheDbHelper == null){
            cacheDbHelper = new CacheDbHelper(this,AppContants.DATABASE_VERSION);
        }
        return cacheDbHelper;
    }

    public WebCacheDbHelper getWebCacheDbHelper(){
        if (webCacheDbHelper ==null){
            webCacheDbHelper = new WebCacheDbHelper(this,AppContants.DATABASE_VERSION);
        }
        return webCacheDbHelper;
    }
    public TngouListDbHelper getTngouListDbHelper(){
        if (tngouListDbHelper ==null){
            tngouListDbHelper = new TngouListDbHelper(this,AppContants.DATABASE_VERSION);
        }
        return tngouListDbHelper;
    }
}
