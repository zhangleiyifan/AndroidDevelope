package com.gyz.androiddevelope.net;

import java.util.ArrayList;
import java.util.Iterator;
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

//            会报java.util.ConcurrentModificationException
//            抛出的条件   大意是: 一个迭代器在迭代集合的时候   集合被修改了
//            for (HttpRequest request : requestList) {
//                Log.e(TAG," cancelRequest--for循环");
//                if (request.getRequest() != null) {
//                    request.getRequest().abort();
//                Log.e(TAG," 取消网络请求");
//                    requestList.remove(request);
//                }
//            }


//            for (int i = 0; i < requestList.size(); i++) {
//                Log.e(TAG, " cancelRequest--for循环");
//                HttpRequest request = requestList.get(i);
//                if (request.getRequest() != null) {
//                    request.getRequest().abort();
//                    Log.e(TAG," 取消网络请求");
//                    requestList.remove(request);
//                }
//            }

            Iterator<HttpRequest> it = requestList.iterator();
            while (it.hasNext()){
                HttpRequest request = it.next();
                if (request.getRequest()!=null){
                    request.getRequest().abort();
                }
                it.remove();
            }
            requestList.clear();

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
