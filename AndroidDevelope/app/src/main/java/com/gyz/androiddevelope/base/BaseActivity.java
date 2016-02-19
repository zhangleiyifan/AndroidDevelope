package com.gyz.androiddevelope.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gyz.androiddevelope.net.RequestCallback;
import com.gyz.androiddevelope.net.RequestManager;


public abstract class BaseActivity extends Activity {

    public static final String TAG = "BaseActivity";
    protected ProgressDialog dlg;
    /**
     * 请求列表管理器
     */
    protected RequestManager requestManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        requestManager = new RequestManager();
        super.onCreate(savedInstanceState);
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


    public abstract class AbstractRequestCallback implements RequestCallback {

        public abstract void onSuccess(String result);

        public void onFail(String errorMessage) {
            if (dlg != null)
                dlg.dismiss();
            Log.e(TAG, "errorMessage=" + errorMessage);
            Toast.makeText(BaseActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }


    protected void onDestroy() {
        /**
         * 在activity销毁的时候同时设置停止请求，停止线程请求回调
         */
        if (requestManager != null) {
            requestManager.cancelRequest();
        }
        super.onDestroy();
    }

    protected void onPause() {
        if (dlg != null)
            dlg.dismiss();
        /**
         * 在activity停止的时候同时设置停止请求，停止线程请求回调
         */
        if (requestManager != null) {
            requestManager.cancelRequest();
        }
        super.onPause();
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }
}