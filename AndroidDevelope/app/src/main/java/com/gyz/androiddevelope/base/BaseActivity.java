package com.gyz.androiddevelope.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gyz.androiddevelope.db.WebCacheDbHelper;
import com.gyz.androiddevelope.engine.AppContants;


public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG = "BaseActivity";
    protected ProgressDialog dlg;
    private WebCacheDbHelper dbHelper;
    /**
     * 请求列表管理器
     */
//    protected RequestManager requestManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

//        requestManager = new RequestManager();
        super.onCreate(savedInstanceState);
        dbHelper = new WebCacheDbHelper(getApplicationContext(), AppContants.DATABASE_VERSION);
        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    /*
        初始化传入参数
         */
    protected abstract void initVariables();

    /*
    	初始化界面及view
    	 */
    protected abstract void initViews(Bundle savedInstanceState);

    /*
    网络请求
     */
    protected abstract void loadData();




    //==================================================
    public WebCacheDbHelper getDbHelper(){
        return dbHelper;
    }
}