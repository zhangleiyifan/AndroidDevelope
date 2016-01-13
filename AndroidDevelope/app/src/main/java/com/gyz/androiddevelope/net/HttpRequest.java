package com.gyz.androiddevelope.net;

import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author: guoyazhou
 * @date: 2016-01-12 20:11
 */
public class HttpRequest implements Runnable {

    private static final String TAG = "HttpRequest";
    private static final String GET = "get";
    private static final String POST = "post";
    private static final int TIMEOUT = 30000;
    //    网络相关
    private HttpUriRequest request = null;
    private HttpResponse response = null;
    private DefaultHttpClient httpClient = null;

    private Handler handler;
    private RequestCallback callback;
    private UrlData urlData;
    private List<RequestParams> paramses = null;
    private String url;

    public HttpRequest(UrlData urlData, List<RequestParams> params, RequestCallback callback) {

        this.urlData = urlData;
        this.paramses = params;
        this.callback = callback;
        url = urlData.getUrl();

        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
        }
        handler = new Handler();
    }

    public HttpUriRequest getRequest(){
        return request;
    }

    @Override
    public void run() {
        try {
            if (GET.equals(urlData.getNetType())) {
//            添加参数
                StringBuffer paramBuffer = new StringBuffer();
                if (paramses != null && paramses.size() > 0) {
                    for (RequestParams p : paramses) {
                        if (paramBuffer.length() == 0) {
                            paramBuffer.append(p.getName() + "=" + p.getValue());
                        } else {
                            paramBuffer.append("&" + p.getName() + "=" + p.getValue());
                        }
                    }
                    String newUrl = url + "?" + paramBuffer.toString();
                    request = new HttpGet(newUrl);
                } else {
                    request = new HttpGet(url);
                }

            } else if (POST.equals(urlData.getNetType())) {
                request = new HttpPost(url);
                //   添加参数
                if (paramses != null && paramses.size() > 0) {
                    List<BasicNameValuePair> list = new ArrayList<>();
                    for (RequestParams p : paramses) {
                        list.add(new BasicNameValuePair(p.getName(), p.getValue()));
                    }

                    ((HttpPost) request).setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
                }

            } else {
                return;
            }

            request.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIMEOUT);
            request.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, TIMEOUT);

//            发送请求
            response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {

                ByteArrayOutputStream content = new ByteArrayOutputStream();
                response.getEntity().writeTo(content);
                String result = new String(content.toByteArray()).trim();
                result = "{'isError':false,'errorType':0,'errorMessage':'','result':{'city':'北京','cityid':'101010100','temp':'17','WD':'西南风','WS':'2级','SD':'54%','WSE':'2','time':'23:15','isRadar':'1','Radar':'JC_RADAR_AZ9010_JB','njd':'暂无实况','qy':'1016'}}";

            Log.i(TAG,"result===="+result);
//                设置回调
                if (callback != null) {
                    Gson gson = new Gson();
                    final Response responseInJson = JSON.parseObject(result, Response.class);

                    if (responseInJson.isError()) {
                        handlerNetworkError(responseInJson.getErrorMsg());
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess(responseInJson.getResult());
                            }
                        });
                    }
                }else {
                    handlerNetworkError("网络异常");
                }
            } else {
                handlerNetworkError("网络异常");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void handlerNetworkError(final String errorMsg) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                HttpRequest.this.callback.onFail(errorMsg);
            }
        });
    }

    public String inputStreamToString(InputStream inputStream) throws IOException {

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        int i = -1;
        while ((i = inputStream.read()) != -1) {
            bao.write(i);
        }
        return bao.toString();
    }

}
