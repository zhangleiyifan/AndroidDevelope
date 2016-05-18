package com.gyz.androiddevelope.activity.custom;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.adapter.ListAdapter;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.util.ToastUtil;
import com.gyz.androiddevelope.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.activity.custom.MyListViewActivity.java
 * @author: ZhaoHao
 * @date: 2016-05-17 17:10
 */
public class MyListViewActivity extends BaseActivity implements MyListView.onMyViewClickListener{
    private static final String TAG = "MyListViewActivity";
    @Bind(R.id.myListView)
    MyListView myListView;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_listview);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadData() {

        List<String> list = new ArrayList<>();
        for (int i = 0;i<100;i++){
            list.add(String.valueOf(i));
        }

        ListAdapter adapter = new ListAdapter(this,list);
        myListView.setListAdapter(adapter);
        myListView.setOnMyViewClickListener(this);

    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onBtnClick(View view) {
        ToastUtil.show(MyListViewActivity.this,"adfasd", Toast.LENGTH_SHORT);
    }
}
