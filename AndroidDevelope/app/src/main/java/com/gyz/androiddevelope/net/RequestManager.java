package com.gyz.androiddevelope.net;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: guoyazhou
 * @date: 2016-01-13 13:56
 */
public class RequestManager {
    private static final String TAG = "RequestManager";

    private List<HttpRequest> requestList;

    public RequestManager() {
        requestList = new ArrayList<HttpRequest>();
    }

    //添加请求队列
    public void addRequest(HttpRequest request) {
        requestList.add(request);
    }

    //    取消网络请求
    public void cancelRequest() {
        if (requestList != null && requestList.size() > 0) {
            for (HttpRequest request : requestList) {
                if (request.getRequest() != null) {
                    request.getRequest().abort();
                    requestList.remove(request);
                }
            }
        }
    }

    //    创建请求（无参）
    public HttpRequest createHttpRequest(UrlData urlData, RequestCallback callback) {

        return createHttpRequest(urlData, null, callback);
    }


    //    创建请求（有参）
    public HttpRequest createHttpRequest(UrlData urlData, List<RequestParams> paramses, RequestCallback callback) {

        HttpRequest request = new HttpRequest(urlData, paramses, callback);
        addRequest(request);
        return request;

    }


}
