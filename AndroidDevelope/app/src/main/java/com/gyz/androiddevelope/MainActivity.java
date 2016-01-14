package com.gyz.androiddevelope;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.bean.WeatherInfo;
import com.gyz.androiddevelope.net.RequestParams;
import com.gyz.androiddevelope.service.RemoteService;
import com.gyz.androiddevelope.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Bind(R.id.btnOnClick)
    Button btnOnClick;
    @Bind(R.id.txtInfo)
    TextView txtInfo;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        dlg = Utils.createProgressDialog(this,
                this.getString(R.string.str_loading));
    }

    @Override
    protected void loadData() {

    }

    @OnClick(R.id.btnOnClick)
    public void OnClick() {

        dlg.show();

        //网络操作.
        List<RequestParams> paramses = new ArrayList<>();
        paramses.add(new RequestParams("cityId", "111"));
        paramses.add(new RequestParams("cityName", "Beijing"));

        RemoteService.getInstance().invoke(this, "getWeatherInfo", paramses, new AbstractRequestCallback() {
            @Override
            public void onSuccess(String result) {
                dlg.dismiss();
                WeatherInfo info = JSON.parseObject(result, WeatherInfo.class);
                txtInfo.setText(info.getCity());
            }

        });

    }

}
