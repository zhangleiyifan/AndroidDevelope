package com.gyz.androiddevelope.activity.custom;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.adapter.MyAdapter;
import com.gyz.androiddevelope.base.BaseToolbarActivity;
import com.gyz.androiddevelope.view.recyclerview.MyRecyclerView;

import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.activity.custom.MyRecyclerActivity.java
 * @author: ZhaoHao
 * @date: 2016-06-14 16:00
 */
public class MyRecyclerActivity extends BaseToolbarActivity {
    private static final String TAG = "MyRecyclerActivity";
    @Bind(R.id.myRecyclerView)
    MyRecyclerView recyclerView;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_myrecycler);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadData() {


//		View headerView = View.inflate(this, resource, root);
        TextView headerView = new TextView(this);
        //		TextView tv = headerView.findViewById(id);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        headerView.setLayoutParams(params);
        headerView.setText("我是HeaderView");

        TextView footerView = new TextView(this);
        params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        footerView.setLayoutParams(params);
        footerView.setText("我是FooterView");

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("item "+i);
        }
        recyclerView.addHeadView(headerView);
        recyclerView.addFootView(footerView);

        MyAdapter adapter = new MyAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected String currActivityName() {
        return "自定义recyclerView";
    }
}
