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
import android.widget.TextView;
import android.widget.Toast;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.base.BaseApplication;
import com.gyz.androiddevelope.response_bean.NewsDetailBean;
import com.gyz.androiddevelope.response_bean.StoryExtraBean;
import com.gyz.androiddevelope.retrofit.MySubscriber;
import com.gyz.androiddevelope.util.ImageUtils;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
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
    @Bind(R.id.txtTitle)
    TextView txtTitle;
    @Bind(R.id.imgShare)
    ImageView imgShare;
    @Bind(R.id.imgComment)
    ImageView imgComment;
    @Bind(R.id.txtCommentCount)
    TextView txtCommentCount;
    @Bind(R.id.imgZan)
    ImageView imgZan;
    @Bind(R.id.txtZanCount)
    TextView txtZanCount;
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
                return ReUtil.getApiManager(true).getNewsDetail(newsID);
            }
        }, new MySubscriber<NewsDetailBean>() {

            @Override
            public void onStart() {
                super.onStart();
                dlg.show();
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                dlg.hide();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                //从数据库取出数据
                SQLiteDatabase database = BaseApplication.getInstantce().getWebCacheDbHelper().getReadableDatabase();
                Cursor cursor = database.rawQuery("select * from webCache where newsId = " + newsID, null);
                if (cursor.moveToFirst()) {
                    initWebContent(cursor.getString(cursor.getColumnIndex("newsId")));
                }
                cursor.close();
                database.close();
            }

            @Override
            public void onNext(NewsDetailBean newsDetailBean) {

                txtTitle.setText(newsDetailBean.getTitle());
                initWebContent(newsDetailBean.getBody());
                ImageUtils.loadImageByPicasso(getApplicationContext(),newsDetailBean.getImage(),imgTitle);
//                Picasso.with(getApplicationContext()).load(newsDetailBean.getImage()).into(imgTitle);
                //存入db
                SQLiteDatabase database = BaseApplication.getInstantce().getWebCacheDbHelper().getWritableDatabase();
                database.execSQL("replace into webCache(newsId,json) values( " + newsDetailBean.getId() + ",'" + newsDetailBean.getBody() + "')");
                database.close();

            }
        });
        //文章评论 点赞数
        RxUtil.subscribeAll(new Func1<String, Observable<StoryExtraBean>>() {
            @Override
            public Observable<StoryExtraBean> call(String s) {
                return ReUtil.getApiManager(true).getNewsExtra(newsID);
            }
        }, new MySubscriber<StoryExtraBean>() {
            @Override
            public void onNext(StoryExtraBean bean) {
                txtCommentCount.setText(String.valueOf(bean.getComments()));
                txtZanCount.setText(String.valueOf(bean.getPopularity()));
            }
        });
    }

    private void initWebContent(String body) {

        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + body + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }


    @OnClick({R.id.imgShare, R.id.imgComment, R.id.imgZan})
    public void onClick(View view) {
        String msg = "";
        switch (view.getId()) {
            case R.id.imgShare:
                msg += "Click share";
                break;

            case R.id.imgComment:
                msg += "Click imgComment";
                break;

            case R.id.imgZan:
                msg += "Click imgZan";
                break;

        }
        if (!msg.equals("")) {
            Toast.makeText(NewsDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String msg = "";
        switch (item.getItemId()) {

            case R.id.imgShare:
                msg += "Click share";
                break;

            case R.id.imgComment:
                msg += "Click imgComment";
                break;

            case R.id.imgZan:
                msg += "Click imgZan";
                break;

        }

        if (!msg.equals("")) {
            Toast.makeText(NewsDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
