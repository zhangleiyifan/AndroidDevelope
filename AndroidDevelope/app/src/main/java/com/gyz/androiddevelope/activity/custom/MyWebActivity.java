package com.gyz.androiddevelope.activity.custom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.gyz.androiddevelope.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.activity.custom.MyWebActivity.java
 * @author: ZhaoHao
 * @date: 2016-05-25 13:33
 */
public class MyWebActivity extends AppCompatActivity {
    private static final String TAG = "MyWebActivity";
    @Bind(R.id.webview)
    WebView webview;


    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        webview.getSettings().setSupportZoom(false);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDefaultTextEncodingName("utf-8");
        //找到Html文件，也可以用网络上的文件
//        webview.loadUrl("http://www.baidu.com");
        webview.loadUrl("file:///android_asset/index.html");
        // 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法
        webview.addJavascriptInterface(new Contact(), "contact");
    }

    class Contact {

        //JavaScript调用此方法拨打电话

        @JavascriptInterface
        public void call(String phone) {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone)));
        }

        //Html调用此方法传递数据
        @JavascriptInterface
        public void showcontacts() {
            final String json = "[{\"name\":\"jjj\", \"amount\":\"3452345645635\", \"phone\":\"18600012345\"}]";
            // 调用JS中的方法
            webview.post(new Runnable() {
                @Override
                public void run() {
                    webview.loadUrl("javascript:show('" + json + "')");
                }
            });
        }
    }
}
