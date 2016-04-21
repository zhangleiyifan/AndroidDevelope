package com.gyz.androiddevelope.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.adapter.HealthInfoAdapter;
import com.gyz.androiddevelope.base.BaseFragment;
import com.gyz.androiddevelope.request_bean.ReqHealthInfoList;
import com.gyz.androiddevelope.response_bean.HealthInfoList;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;
import com.gyz.androiddevelope.util.L;

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
public class HealthInfoListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HealthInfoListFragment";

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    List<HealthInfoList.HealthInfo> list;
    HealthInfoAdapter adapter;

    LinearLayoutManager mLayoutManager;
    private int page = 1, rows, id, lastVisibleItemPosition;
    private boolean isAdd;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_info_list, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initView() {

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent, R.color.color_f98435, R.color.color_ef5350);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                .getDisplayMetrics()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                L.e("lastVisibleItemPosition==" + lastVisibleItemPosition + "getItemCount==" + adapter.getItemCount());
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    swipeRefreshLayout.setRefreshing(true);
                    page++;
                    isAdd = true;
                    requestNetData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
            }
        });

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void initData() {
        requestNetData();
    }

    @Override
    public String getTitle() {
        return "健康列表";
    }


    @Override
    public void onRefresh() {
//      下拉时会调用
        page = 1;
        isAdd = false;
        requestNetData();
    }


    private void requestNetData() {
        RxUtil.subscribeAll(new Func1<String, Observable<HealthInfoList>>() {
            @Override
            public Observable<HealthInfoList> call(String s) {
                return ReUtil.getApiManager(true).getHealthNewsInfoList(new ReqHealthInfoList(page, rows, id));
            }
        }, new Subscriber<HealthInfoList>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                on error
                Toast.makeText(getContext(), "网络请求出现异常", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onNext(HealthInfoList healthInfoList) {

                swipeRefreshLayout.setRefreshing(false);
                if (isAdd) {
                    list.addAll(healthInfoList.healthInfoList);
                } else {
                    list = healthInfoList.healthInfoList;
                }
                L.e(TAG, "size===" + list.size());

                adapter = new HealthInfoAdapter(list, getContext());
                adapter.setOnItemClickListener(new HealthInfoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, HealthInfoList.HealthInfo object) {


                    }
                });

                recyclerView.setAdapter(adapter);

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
