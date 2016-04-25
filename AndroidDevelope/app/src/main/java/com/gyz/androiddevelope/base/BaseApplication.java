package com.gyz.androiddevelope.base;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.gyz.androiddevelope.cache.CacheManager;
import com.gyz.androiddevelope.engine.AppContants;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * @author: guoyazhou
 * @date: 2016-01-19 17:21
 */
public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    private static BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Stetho.initializeWithDefaults(this);
        CrashReport.initCrashReport(getApplicationContext(), AppContants.BUGLY_APP_ID, false);
        CacheManager.getInstance().initCacheDir();
    }

    public static BaseApplication getInstantce(){
        return instance;
    }
}
