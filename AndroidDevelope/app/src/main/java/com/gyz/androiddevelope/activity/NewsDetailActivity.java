package com.gyz.androiddevelope.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.response_bean.NewsDetailBean;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * @author: guoyazhou
 * @date: 2016-03-17 11:40
 */
public class NewsDetailActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    private static final String TAG = "NewsDetailActivity";

    public static final String NEWS_ID = "news_id";
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbarLayout)
    AppBarLayout appbarLayout;
    @Bind(R.id.imgTitle)
    ImageView imgTitle;
    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    private int newsID;

    public static void startActivity(Context context, int id) {
        context.startActivity(new Intent(context, NewsDetailActivity.class).putExtra(NEWS_ID, id));
    }

    @Override
    protected void initVariables() {
        newsID = getIntent().getIntExtra(NEWS_ID, 0);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        webView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        webView.getSettings().setAppCacheEnabled(true);
    }

    @Override
    protected void loadData() {
        RxUtil.subscribeAll(new Func1<String, Observable<NewsDetailBean>>() {
            @Override
            public Observable<NewsDetailBean> call(String s) {
                return ReUtil.getApiManager().getNewsDetail(newsID);
            }
        }, new Subscriber<NewsDetailBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                //从数据库取出数据
                SQLiteDatabase database = getDbHelper().getReadableDatabase();
                Cursor cursor = database.rawQuery("select * from webCache where newsId = " + newsID, null);
                if (cursor.moveToFirst()) {
                    initWebContent(cursor.getString(cursor.getColumnIndex("newsId")));
                }
                cursor.close();
                database.close();
            }

            @Override
            public void onNext(NewsDetailBean newsDetailBean) {
                initWebContent(newsDetailBean.getBody());
                Picasso.with(getApplicationContext()).load(newsDetailBean.getImage()).into(imgTitle);
                //存入db
                SQLiteDatabase database = getDbHelper().getWritableDatabase();
                database.execSQL("replace into webCache(newsId,json) values( " + newsDetailBean.getId() + ",'" + newsDetailBean.getBody() + "')");
                database.close();

            }
        });
    }

    private void initWebContent(String body) {

        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + body + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String msg = "";
        switch (item.getItemId()) {
            case R.id.action_edit:
                msg += "Click edit";
                break;
            case R.id.action_share:
                msg += "Click share";
                break;
            case R.id.action_settings:
                msg += "Click setting";
                break;
        }

        if (!msg.equals("")) {
            Toast.makeText(NewsDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
