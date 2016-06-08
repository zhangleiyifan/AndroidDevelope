package com.gyz.androiddevelope.fragment.zhihu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.activity.NewsDetailActivity;
import com.gyz.androiddevelope.adapter.HomeNewsAdapter;
import com.gyz.androiddevelope.base.BaseApplication;
import com.gyz.androiddevelope.base.BaseRecyclerAdapter;
import com.gyz.androiddevelope.base.BaseRecyclerFragment;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.listener.OnSwipeRefreshFragmentListener;
import com.gyz.androiddevelope.response_bean.BeforeNewsBean;
import com.gyz.androiddevelope.response_bean.LatestNewsBean;
import com.gyz.androiddevelope.response_bean.Story;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;
import com.gyz.androiddevelope.util.DateUtil;
import com.gyz.androiddevelope.util.L;
import com.gyz.androiddevelope.util.SPUtils;
import com.gyz.androiddevelope.util.ToastUtil;
import com.gyz.androiddevelope.view.MarqueeView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.fragment.zhihu.ZhiHu2Fragment.java
 * @author: ZhaoHao
 * @date: 2016-06-08 13:50
 */
public class ZhiHu2Fragment extends BaseRecyclerFragment {
    private static final String TAG = "ZhiHu2Fragment";

    HomeNewsAdapter adapter;
    private String date;
    MarqueeView marqueeView;
    SQLiteDatabase database;
    //与Activity 交互接口 SWIpe 刷新结束后，停止转圈
    private OnSwipeRefreshFragmentListener mRefreshListener;

    @Override
    protected void addListNetData(boolean isAdd) {
        if (isAdd) {
            requestAddData();
        } else {
            requestFirstData();
        }
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return mLayoutManager;
    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {

        adapter = new HomeNewsAdapter(getContext());
        //添加头部view
        View header = LayoutInflater.from(getContext()).inflate(R.layout.kanner, getRecyclerView(), false);
        marqueeView = (MarqueeView) header.findViewById(R.id.marqueeView);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void click(LatestNewsBean.TopStory entity) {
                ToastUtil.showShort(getContext(), "title===" + entity.getTitle());
            }
        });

        adapter.setHeaderView(header);

        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data, View v) {

                Story object = (Story) data;
                String readId = (String) SPUtils.get(context, AppContants.READ_ID, "");

                if (readId != null && !readId.contains(String.valueOf(object.id))) {
                    TextView textView = (TextView) v.findViewById(R.id.txtTitle);
                    textView.setTextColor(context.getResources().getColor(R.color.color_999999));
                    //保存已读
                    SPUtils.put(context, AppContants.READ_ID, readId + "," + object.id);
                }
                //跳转至详情页
                NewsDetailActivity.startActivity(context, object.id);
            }
        });
        return adapter;
    }

    @Override
    public void getBundleDatas(Bundle bundle) {
    }

    @Override
    public void initData() {
        requestFirstData();
    }

    @Override
    public String getTitle() {
        return "知乎2";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSwipeRefreshFragmentListener) {
            mRefreshListener = (OnSwipeRefreshFragmentListener) context;
        }
    }

    //下拉刷新
    private void requestFirstData() {

        RxUtil.subscribeAll(new Func1<String, Observable<LatestNewsBean>>() {
            @Override
            public Observable<LatestNewsBean> call(String s) {
                return ReUtil.getApiManager(AppContants.ZHIHU_HTTP).getLatestNews();
            }
        }, new Subscriber<LatestNewsBean>() {
            @Override
            public void onCompleted() {
                mRefreshListener.OnRefreshState(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mRefreshListener.OnRefreshState(false);
//                从数据库中获取缓存json
                SQLiteDatabase database = BaseApplication.getInstantce().getCacheDbHelper().getReadableDatabase();
                Cursor cursor = database.rawQuery("select * from CacheList where date = " + AppContants.LATEST_COLUMN, null);

                if (cursor.moveToFirst()) {
                    String json = cursor.getString(cursor.getColumnIndex("json"));
                    afterPullRefresh(getGson().fromJson(json, LatestNewsBean.class));
                }
            }

            @Override
            public void onNext(LatestNewsBean latestNewsBean) {
                afterPullRefresh(latestNewsBean);
                //存入数据库
                database = BaseApplication.getInstantce().getCacheDbHelper().getWritableDatabase();
                L.e(TAG, "sql=====" + "replace into CacheList(date,json) values (" + AppContants.LATEST_COLUMN + ",'" + getGson().toJson(latestNewsBean, LatestNewsBean.class) + "')");

                database.execSQL("replace into CacheList(date,json) values (" + AppContants.LATEST_COLUMN + ",'" + getGson().toJson(latestNewsBean, LatestNewsBean.class) + "')");
            }
        });

    }

    //加载更多
    private void requestAddData() {
        RxUtil.subscribeAll(new Func1<String, Observable<BeforeNewsBean>>() {
            @Override
            public Observable<BeforeNewsBean> call(String s) {
                return ReUtil.getApiManager(AppContants.ZHIHU_HTTP).getBeforeNews(date);
            }
        }, new Subscriber<BeforeNewsBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(BeforeNewsBean beforeNewsBean) {
                L.e(TAG, "beforeNewsBean=" + beforeNewsBean.date);
                date = beforeNewsBean.date;

                //加入分割标题
                Story story = new Story();
                story.title = DateUtil.convertDate(date);
                story.type = AppContants.TITLE_TYPE;

                List<Story> storiesList = beforeNewsBean.stories;
                storiesList.add(0, story);
                adapter.addDatas(storiesList);

            }
        });
    }

    private void afterPullRefresh(LatestNewsBean latestNewsBean) {
        L.e(TAG, "LatestNewsBean=" + latestNewsBean.date);
        date = latestNewsBean.date;
        adapter.clearDatas();
        adapter.addDatas(latestNewsBean.stories);
        marqueeView.setTopEntities(latestNewsBean.topStories);
    }
}
