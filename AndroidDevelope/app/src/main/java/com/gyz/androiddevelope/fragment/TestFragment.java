package com.gyz.androiddevelope.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.activity.CircleActivity;
import com.gyz.androiddevelope.activity.HomeActivity;
import com.gyz.androiddevelope.activity.ShowInfoActivity;
import com.gyz.androiddevelope.activity.account.LoginActivity;
import com.gyz.androiddevelope.base.BaseFragment;
import com.gyz.androiddevelope.base.TestActivity;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.engine.User;
import com.gyz.androiddevelope.net.RequestParams;
import com.gyz.androiddevelope.net.okhttp.OkHttpClientManager;
import com.gyz.androiddevelope.request_bean.ReqHealthInfoList;
import com.gyz.androiddevelope.request_bean.ReqUserInfoBean;
import com.gyz.androiddevelope.response_bean.Axiba;
import com.gyz.androiddevelope.response_bean.InfoList;
import com.gyz.androiddevelope.response_bean.Tngou;
import com.gyz.androiddevelope.response_bean.UserInfo;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;
import com.gyz.androiddevelope.util.Utils;
import com.gyz.androiddevelope.view.PwdView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class TestFragment extends BaseFragment {

    public static final String TAG = "TestFragment";
    public static final int GO_TO_INFO = 3001;

    @Bind(R.id.btnOnClick)
    Button btnOnClick;
    @Bind(R.id.txtInfo)
    TextView txtInfo;
    @Bind(R.id.btnHealthList)
    Button btnHealthList;
    @Bind(R.id.btnHome)
    Button btnHome;
    @Bind(R.id.pwdView)
    PwdView pwdView;

    ProgressDialog dlg;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, view);

        context =getContext();
        dlg = Utils.createProgressDialog(context,this.getString(R.string.str_loading));

        return view;
    }


    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btnWave,
            R.id.btnHome, R.id.btnOnClick, R.id.btnGo, R.id.btnOkHttp, R.id.btnOkHttp3, R.id.view, R.id.retrofit, R.id.btnHealth, R.id.btnHealthList})
    public void OnClick(View view) {

        switch (view.getId()) {

            case R.id.btnWave:

//                startActivity(new Intent(context, WaveActivity.class));
                startActivity(new Intent(context, TestActivity.class));
                break;

            case R.id.btnHome:
                context.startActivity(new Intent(context, HomeActivity.class));
                break;
            case R.id.btnHealthList:

                RxUtil.subscribeOnNext(new Func1<String, Observable<InfoList>>() {
                    @Override
                    public Observable<InfoList> call(String s) {

                        ReqHealthInfoList list = new ReqHealthInfoList(3, 10, 1);
                        return ReUtil.getApiManager().getHealthInfoList(list);
                    }
                }, new Action1<InfoList>() {
                    @Override
                    public void call(InfoList infoList) {

                        InfoList.Info info = infoList.infos.get(0);
                        Toast.makeText(context, "infoList==" + info.description, Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            case R.id.btnHealth:

                RxUtil.subscribeAll(new Func1<String, Observable<Tngou>>() {
                    @Override
                    public Observable<Tngou> call(String s) {
                        return ReUtil.getApiManager().getInfoList();
                    }
                }, new Subscriber<Tngou>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(context, "onCompleted==", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "onError==", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Tngou tngou) {
                        Toast.makeText(context, "infoClasses.size==" + tngou.infoList.size(), Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            case R.id.retrofit:

                RxUtil.subscribeAll(new Func1<String, Observable<UserInfo>>() {
                    @Override
                    public Observable<UserInfo> call(String s) {

                        ReqUserInfoBean bean = new ReqUserInfoBean("a4fe0465b9464ae8fbl54da04bfd6e2f");

//                        Observable<UserInfo> observable =  ReUtil.getApiManager().getUserInfo(bean);
//                        return observable;
                        return ReUtil.getApiManager().getUserInfo("a4fe0465b9464ae8fbl54da04bfd6e2f");
                    }
                }, new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(context, "onCompleted==", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "onError==", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        Toast.makeText(context, "userInfo==" + userInfo.getAccount(), Toast.LENGTH_SHORT).show();
                    }
                });


//
//                RxUtil.subscribeAll("", new Func1<String, Observable<Axiba>>() {
//                    @Override
//                    public Observable<Axiba> call(String s) {
//                        return ReUtil.getApiManager().getWeather("111", "Beijing");
//
//                    }
//                }, new Subscriber<Axiba>() {
//                    @Override
//                    public void onCompleted() {
//                        L.e(TAG, "onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(Axiba axiba) {
//                        L.d(TAG, "axiba==" + axiba.getWeatherinfo().getCity() + "    |   " + axiba.getWeatherinfo().getTime());
//                        Toast.makeText(TestFragment.this, "axiba==" + axiba.getWeatherinfo().getCity(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });

//                Observable.just("","").flatMap(new Func1<String, Observable<Axiba>>() {
//                    @Override
//                    public Observable<Axiba> call(String s) {
////
////                        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.weather.com.cn")
////                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
////                        .addConverterFactory(GsonConverterFactory.create()).build();
////                         ApiManagerService service = retrofit.create(ApiManagerService.class);
////
////                        return  service.getWeather("111", "Beijing");
//                    return     ReUtil.getApiManager().getWeather("111", "Beijing");
//
//                    }
//                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeAll(new Subscriber<Axiba>() {
//                    @Override
//                    public void onCompleted() {
//                        L.e(TAG, "onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(Axiba axiba) {
//                        L.d(TAG, "axiba==" + axiba.getWeatherinfo().getCity() + "    |   " + axiba.getWeatherinfo().getTime());
//                        Toast.makeText(TestFragment.this,"axiba==" + axiba.getWeatherinfo().getCity(),Toast.LENGTH_SHORT).show();
//                    }
//                });


                break;

            case R.id.view:

                context.startActivity(new Intent(context, CircleActivity.class));
                break;
            case R.id.btnOkHttp3:


                OkHttpClientManager.Param[] params = new OkHttpClientManager.Param[2];

                OkHttpClientManager.Param param = new OkHttpClientManager.Param("cityId", "111");
                OkHttpClientManager.Param param1 = new OkHttpClientManager.Param("cityName", "Beijing");

                params[0] = param;
                params[1] = param1;

                OkHttpClientManager.postAsyn("http://www.weather.com.cn/data/sk/101010100.html"
                        , params, new OkHttpClientManager.ResultCallback<Axiba>() {
                    @Override
                    public void onError(Request request, Exception e) {

                        Log.e(TAG, "request=" + request + "-----e=" + e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Axiba response) {
                        Log.e(TAG, "response=" + response.getWeatherinfo().getCity() + "   " + response.getWeatherinfo().getWD());

                    }
                }, null);
                break;

            case R.id.btnOkHttp:

                OkHttpClientManager.Param[] params2 = new OkHttpClientManager.Param[2];

                OkHttpClientManager.Param param2 = new OkHttpClientManager.Param("cityId", "111");
                OkHttpClientManager.Param param12 = new OkHttpClientManager.Param("cityName", "Beijing");

                params2[0] = param2;
                params2[1] = param12;

                OkHttpClientManager.postAsyn("http://www.weather.com.cn/data/sk/101010100.html", params2,
                        new OkHttpClientManager.ResultCallback<Axiba>() {
                            @Override
                            public void onError(Request request, Exception e) {
                                Log.e(TAG, "request=" + request + "-----e=" + e);
                                e.printStackTrace();

                            }

                            @Override
                            public void onResponse(Axiba response) {
                                Log.e(TAG, "response=" + response.getWeatherinfo().getCity() + "   " + response.getWeatherinfo().getWD());
                            }
                        }
                );
                break;

            case R.id.btnOnClick:

                dlg.show();

                //网络操作.
                List<RequestParams> paramses = new ArrayList<>();
                paramses.add(new RequestParams("cityId", "111"));
                paramses.add(new RequestParams("cityName", "Beijing"));

//                RemoteService.getInstance().invoke(this, "getWeatherInfo", paramses, new AbstractRequestCallback() {
//                    @Override
//                    public void onSuccess(String result) {
//
//                        Log.v(TAG, "回调onSuccess==result" + result);
//                        dlg.dismiss();
//                        WeatherInfo info = JSON.parseObject(result, WeatherInfo.class);
//                        txtInfo.setText(info.getCity());
//                    }
//
//                });

                break;

            case R.id.btnGo:

                if (User.getInstantce().isLogin()) {
                    ShowInfoActivity.startActivity(context);
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra(AppContants.NEED_CALLBACK, true);
                    startActivityForResult(intent, GO_TO_INFO);
                }

                break;
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GO_TO_INFO:

                    ShowInfoActivity.startActivity(context);
                    break;
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
