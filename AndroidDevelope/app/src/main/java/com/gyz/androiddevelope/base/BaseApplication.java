package com.gyz.androiddevelope.base;

import android.app.Application;
import android.content.pm.PackageManager;

import com.alipay.euler.andfix.patch.PatchManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.gyz.androiddevelope.cache.CacheManager;
import com.gyz.androiddevelope.db.CacheDbHelper;
import com.gyz.androiddevelope.db.TngouDbHelper;
import com.gyz.androiddevelope.db.TngouListDbHelper;
import com.gyz.androiddevelope.db.WebCacheDbHelper;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.response_bean.UserInfo;
import com.gyz.androiddevelope.response_bean.UserMeAndOtherBean;
import com.gyz.androiddevelope.util.ConfigConstants;
import com.gyz.androiddevelope.util.SPUtils;
import com.squareup.leakcanary.LeakCanary;
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
    private PatchManager patchManager;
    private boolean isLogin;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Stetho.initializeWithDefaults(this);
//        Fresco.initialize(this, ConfigConstants.getImagePipelineConfig(this));
        Fresco.initialize(this);
//        andfix
//        patchManager = new PatchManager(this);
//        String appversion = null;
//        try {
//            appversion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            appversion = "2.0";
//        }
//        patchManager.init(appversion);//current version
//        patchManager.loadPatch();

        CrashReport.initCrashReport(getApplicationContext(), AppContants.BUGLY_APP_ID, false);
        CacheManager.getInstance().initCacheDir();

        LeakCanary.install(this);

    }

    public static BaseApplication getInstantce() {
        return instance;
    }

    public TngouDbHelper getTngouDbHelper() {
        if (tngouDbHelper == null) {
            tngouDbHelper = new TngouDbHelper(this, AppContants.DATABASE_VERSION);
        }
        return tngouDbHelper;
    }

    public CacheDbHelper getCacheDbHelper() {
        if (cacheDbHelper == null) {
            cacheDbHelper = new CacheDbHelper(this, AppContants.DATABASE_VERSION);
        }
        return cacheDbHelper;
    }

    public WebCacheDbHelper getWebCacheDbHelper() {
        if (webCacheDbHelper == null) {
            webCacheDbHelper = new WebCacheDbHelper(this, AppContants.DATABASE_VERSION);
        }
        return webCacheDbHelper;
    }

    public TngouListDbHelper getTngouListDbHelper() {
        if (tngouListDbHelper == null) {
            tngouListDbHelper = new TngouListDbHelper(this, AppContants.DATABASE_VERSION);
        }
        return tngouListDbHelper;
    }

    public PatchManager getPatchManager(){
        return patchManager;
    }


    public boolean isLogin() {
        return (boolean)SPUtils.get(this, AppContants.ISLOGIN,false);
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
