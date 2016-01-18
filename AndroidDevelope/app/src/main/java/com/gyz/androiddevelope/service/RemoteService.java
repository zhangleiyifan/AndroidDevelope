package com.gyz.androiddevelope.service;

import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.net.DefaultThreadPool;
import com.gyz.androiddevelope.net.HttpRequest;
import com.gyz.androiddevelope.net.RequestCallback;
import com.gyz.androiddevelope.net.RequestParams;
import com.gyz.androiddevelope.net.UrlConfigManager;
import com.gyz.androiddevelope.net.UrlData;

import java.util.List;

/**
 * @author: guoyazhou
 * @date: 2016-01-13 16:19
 */
public class RemoteService {
    private static final String TAG = "RemoteService";

    private static RemoteService remoteService;

    private RemoteService() {
    }

    public static synchronized RemoteService getInstance() {
        if (remoteService == null) {
            remoteService = new RemoteService();
        }
        return remoteService;
    }

    public void invoke(BaseActivity activity,String key,List<RequestParams> list,RequestCallback callback){

        UrlData urlData= UrlConfigManager.findUrlData(activity,key);
        HttpRequest request = activity.getRequestManager().createHttpRequest(urlData,list,callback);
        DefaultThreadPool.getInstance().execute(request);

    }
}
