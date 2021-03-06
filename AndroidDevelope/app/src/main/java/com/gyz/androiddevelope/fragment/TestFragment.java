package com.gyz.androiddevelope.fragment;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.activity.custom.CircleActivity;
import com.gyz.androiddevelope.activity.HomeActivity;
import com.gyz.androiddevelope.activity.custom.ShowInfoActivity;
import com.gyz.androiddevelope.activity.huaban.LoginActivity;
import com.gyz.androiddevelope.activity.custom.CalenderActivity;
import com.gyz.androiddevelope.activity.custom.ConcatMatrixActivity;
import com.gyz.androiddevelope.activity.custom.FlyViewActivity;
import com.gyz.androiddevelope.activity.custom.McalendarActivity;
import com.gyz.androiddevelope.activity.custom.MountainViewActivity;
import com.gyz.androiddevelope.activity.custom.MyListViewActivity;
import com.gyz.androiddevelope.activity.custom.MyProgressBarActivity;
import com.gyz.androiddevelope.activity.custom.MyRecyclerActivity;
import com.gyz.androiddevelope.activity.custom.MyWebActivity;
import com.gyz.androiddevelope.activity.custom.NearBySearchActivity;
import com.gyz.androiddevelope.activity.custom.ToolbarTestActivity;
import com.gyz.androiddevelope.activity.custom.Transform3DActivity;
import com.gyz.androiddevelope.activity.custom.WaveActivity;
import com.gyz.androiddevelope.activity.custom.NoBoringActionBarActivity;
import com.gyz.androiddevelope.base.BaseFragment;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.engine.User;
import com.gyz.androiddevelope.net.okhttp.OkHttpClientManager;
import com.gyz.androiddevelope.proxy.EvilInstrumentation;
import com.gyz.androiddevelope.request_bean.ReqUserInfoBean;
import com.gyz.androiddevelope.response_bean.Axiba;
import com.gyz.androiddevelope.response_bean.Tngou;
import com.gyz.androiddevelope.response_bean.UserInfo;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;
import com.gyz.androiddevelope.util.L;
import com.gyz.androiddevelope.util.Utils;
import com.gyz.androiddevelope.view.PwdView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Request;
import rx.Observable;
import rx.Subscriber;
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void getBundleDatas(Bundle bundle) {

    }


    @Override
    public void initView() {

        context = getContext();
        dlg = Utils.createProgressDialog(context, this.getString(R.string.str_loading));

    }
    @Override
    public void initData() {
    }

    @Override
    public String getTitle() {
        return "test";
    }

    @OnClick({R.id.btnAndfix, R.id.btnToolbar, R.id.btnWebView, R.id.btnProgress, R.id.btnMyListview, R.id.btnWave, R.id.noToolBar, R.id.btnCalendar, R.id.btnMCalendar, R.id.btnM, R.id.btnFly, R.id.btnNearBy, R.id.btnMatrix,
            R.id.btnHome, R.id.btnOnClick, R.id.btnGo, R.id.btnOkHttp, R.id.btnOkHttp3, R.id.view, R.id.retrofit, R.id.btnHealth, R.id.btnHealthList})
    public void OnClick(View view) {

        switch (view.getId()) {
            case R.id.btnAndfix:
                String str = "abcdefg123";
                String encodeStr = new String(Base64.encode(str.getBytes(), Base64.DEFAULT)) ;
                L.e(TAG, "encodeStr===" + encodeStr);

                String decodeStr =new String( Base64.decode(encodeStr.getBytes(),Base64.DEFAULT)) ;
                L.e(TAG, "decodeStr====" + decodeStr);

////                AndFix
//                PatchManager patchManager = BaseApplication.getInstantce().getPatchManager();
//                File patchFile = new File(Environment.getExternalStorageDirectory().getPath() + "/new.apatch");
//                Log.e(TAG, "patchFile=toString==" + patchFile.toString());
//                if (patchFile.exists()) {
//                    try {
//                        patchManager.addPatch(patchFile.getPath());
//                        ToastUtil.showLong(context, "andfix 合并patch 成功！！！");
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                break;
            case R.id.btnToolbar:

                gotoToolbarActivity();

                break;
            case R.id.btnProgress:
                startActivity(new Intent(context, MyProgressBarActivity.class));
                break;
            case R.id.btnWebView:
                startActivity(new Intent(context, MyWebActivity.class));
                break;
            case R.id.btnMyListview:
                startActivity(new Intent(context, MyListViewActivity.class));
                break;
            case R.id.btnMatrix:
                startActivity(new Intent(context, ConcatMatrixActivity.class));
                break;
            case R.id.btnNearBy:
                startActivity(new Intent(context, NearBySearchActivity.class));
                break;
            case R.id.btnMCalendar:
                startActivity(new Intent(context, McalendarActivity.class));
                break;
            case R.id.btnFly:

                startActivity(new Intent(context, FlyViewActivity.class));
                break;
            case R.id.btnM:

                startActivity(new Intent(context, MountainViewActivity.class));
                break;

            case R.id.btnCalendar:
                startActivity(new Intent(context, CalenderActivity.class));
                break;
            case R.id.noToolBar:
                startActivity(new Intent(context, NoBoringActionBarActivity.class));
                break;
            case R.id.btnWave:
                startActivity(new Intent(context, WaveActivity.class));
                break;
            case R.id.btnHome:
                context.startActivity(new Intent(context, HomeActivity.class));
                break;
            case R.id.btnHealthList:
                startActivity(new Intent(context, Transform3DActivity.class));

                break;

            case R.id.btnHealth:

                RxUtil.subscribeAll(new Func1<String, Observable<Tngou>>() {
                    @Override
                    public Observable<Tngou> call(String s) {
                        return ReUtil.getApiManager(AppContants.ZHIHU_HTTP).getInfoList();
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
                        return ReUtil.getApiManager(AppContants.ZHIHU_HTTP).getUserInfo("a4fe0465b9464ae8fbl54da04bfd6e2f");
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


                try {
                    Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
                    Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
                    currentActivityThreadMethod.setAccessible(true);
                    Object currentActivityThread = currentActivityThreadMethod.invoke(null);
                    // 拿到原始的 mInstrumentation字段
                    Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
                    mInstrumentationField.setAccessible(true);
                    Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

                    // 创建代理对象
                    Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);
                    // 偷梁换柱
                    mInstrumentationField.set(currentActivityThread, evilInstrumentation);

                } catch (Exception e) {
                    e.printStackTrace();
                }

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

                startActivity(new Intent(getContext(),MyRecyclerActivity.class));

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

    private void gotoToolbarActivity() {
        startActivity(new Intent(context, ToolbarTestActivity.class));
//        startActivity(new Intent(context, MyProgressBarActivity.class));
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

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }

}
