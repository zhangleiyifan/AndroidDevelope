package com.gyz.androiddevelope.fragment.zhihu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.activity.NewsDetailActivity;
import com.gyz.androiddevelope.adapter.BaseRecyclerAdapter;
import com.gyz.androiddevelope.adapter.HomeNewsAdapter;
import com.gyz.androiddevelope.base.BaseFragment;
import com.gyz.androiddevelope.engine.AppContants;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * @author: guoyazhou
 * @date: 2016-03-08 16:00
 */
public class ZhiHuFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HealthInfoListFragment";

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    HomeNewsAdapter adapter;
    MarqueeView marqueeView;
    LinearLayoutManager mLayoutManager;
    private String date;
    private boolean isLoadMore;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initView() {

        adapter = new HomeNewsAdapter(getContext());
        recyclerView.setAdapter(adapter);
        //下拉刷新
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent, R.color.color_f98435, R.color.color_ef5350);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                .getDisplayMetrics()));
        swipeRefreshLayout.setOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        //添加头部view
        View header = LayoutInflater.from(getContext()).inflate(R.layout.kanner, recyclerView, false);
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

                if (!readId.contains(String.valueOf(object.id))) {
                    TextView textView = (TextView) v.findViewById(R.id.txtTitle);
                    textView.setTextColor(context.getResources().getColor(R.color.color_999999));
                    //保存已读
                    SPUtils.put(context, AppContants.READ_ID, readId + "," + object.id);
                }

                //跳转至详情页
                NewsDetailActivity.startActivity(context, object.id);
//                TransitionAnimation 跳转
//                Intent intent = new Intent(context, NewsDetailActivity.class);
//                intent.putExtra(NewsDetailActivity.NEWS_ID, object.id);
//               context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();

                if (lastVisibleItem >= totalItemCount - 1 && !isLoadMore) {
                    isLoadMore = true;
                    requestAddData();
                }

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isLoadMore = false;
                requestFirstData();
            }
        });

    }

    @Override
    public void initData() {
        requestFirstData();
    }

    @Override
    public String getTitle() {
            return getResources().getString(R.string.title_zhihu);
    }


    @Override
    public void onRefresh() {
//      下拉时会调用
        isLoadMore = false;
        L.d(TAG, "触发下拉刷新");
        requestFirstData();
    }

    //下拉刷新
    private void requestFirstData() {

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }

        recyclerView.smoothScrollToPosition(0);

        RxUtil.subscribeAll(new Func1<String, Observable<LatestNewsBean>>() {
            @Override
            public Observable<LatestNewsBean> call(String s) {
                return ReUtil.getApiManager(true).getLatestNews();
            }
        }, new Subscriber<LatestNewsBean>() {
            @Override
            public void onCompleted() {
                if (swipeRefreshLayout != null)
                    swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
//                从数据库中获取缓存json
                SQLiteDatabase database = getCacheDbHelper().getReadableDatabase();
                Cursor cursor = database.rawQuery("select * from CacheList where date = " + AppContants.LATEST_COLUMN, null);
                if (cursor.moveToFirst()) {

                    String json = cursor.getString(cursor.getColumnIndex("json"));
                    afterPullRefresh(getGson().fromJson(json, LatestNewsBean.class));
                }
                if (swipeRefreshLayout != null)
                    swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onNext(LatestNewsBean latestNewsBean) {
                afterPullRefresh(latestNewsBean);
                //存入数据库
                SQLiteDatabase database = getCacheDbHelper().getWritableDatabase();
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
                return ReUtil.getApiManager(true).getBeforeNews(date);
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
                isLoadMore = false;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
