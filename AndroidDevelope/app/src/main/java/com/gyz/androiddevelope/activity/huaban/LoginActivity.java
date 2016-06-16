package com.gyz.androiddevelope.activity.huaban;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseApplication;
import com.gyz.androiddevelope.base.BaseToolbarActivity;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.response_bean.BoardItemInfoBean;
import com.gyz.androiddevelope.response_bean.BoardListInfoBean;
import com.gyz.androiddevelope.response_bean.TokenBean;
import com.gyz.androiddevelope.response_bean.UserMeAndOtherBean;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.util.KeyBoardUtils;
import com.gyz.androiddevelope.util.SPUtils;
import com.gyz.androiddevelope.util.SnackbarUtils;
import com.gyz.androiddevelope.util.ToastUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author: guoyazhou
 * @date: 2016-01-22 15:44
 */
public class LoginActivity extends BaseToolbarActivity {

    private static final String TAG = "LoginActivity";
    @Bind(R.id.actv_username)
    AutoCompleteTextView actvUsername;
    @Bind(R.id.edit_password)
    EditText editPassword;
    @Bind(R.id.progress_login)
    ProgressBar progressLogin;
    @Bind(R.id.scroll_login_form)
    ScrollView scrollLoginForm;

    private TokenBean mTokenBean;
    private UserMeAndOtherBean mUserBean;
    //联网的授权字段 提供子Fragment使用
    public String mAuthorization = "";

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void initVariables() {


    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadData() {

    }

    /**
     * 描述当前页面的title--便于友盟统计
     */
    @Override
    protected String currActivityName() {
        return "登录";
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.btn_register:

                break;
        }
    }

    /**
     * 登录操作
     */
    private void doLogin() {

        final String userName = actvUsername.getText().toString().toLowerCase().trim();
        final String pwd = editPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd)) {
            ToastUtil.showShort(getBaseContext(), getString(R.string.null_info));
        }

        ReUtil.getApiManager(AppContants.HUABAN_HTTP)
                .httpsTokenRx(AppContants.HuaBanLoginAuth, "password", userName, pwd)
                .flatMap(new Func1<TokenBean, Observable<UserMeAndOtherBean>>() {
                    @Override
                    public Observable<UserMeAndOtherBean> call(TokenBean tokenBean) {
                        //得到toke成功 用内部字段保存 在最后得到用户信息一起保存写入
                        mTokenBean = tokenBean;
                        mAuthorization = tokenBean.getToken_type() + " " + tokenBean.getAccess_token();

                        //取得token后 第二次网络操作  获取用户信息
                        return ReUtil.getApiManager(AppContants.HUABAN_HTTP).httpsUserRx(mAuthorization);
                    }
                })
                .flatMap(new Func1<UserMeAndOtherBean, Observable<BoardListInfoBean>>() {
                    @Override
                    public Observable<BoardListInfoBean> call(UserMeAndOtherBean userMeAndOtherBean) {
                        mUserBean = userMeAndOtherBean;
//                         获取用户信息 成功后  第三次网络操作  取得用户画板集。（不需要显示，但需保存）
                        return ReUtil.getApiManager(AppContants.HUABAN_HTTP).httpsBoardListInfo(mAuthorization, AppContants.OPERATEBOARDEXTRA);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BoardListInfoBean>() {
                    // 第三次网络请求后的结果处理

                    @Override
                    public void onStart() {
                        super.onStart();
                        progressLogin.setVisibility(View.VISIBLE);
                        //隐藏软键盘
                        KeyBoardUtils.closeKeybord(editPassword,getBaseContext());
                    }

                    @Override
                    public void onCompleted() {
                        progressLogin.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(final BoardListInfoBean boardListInfoBean) {
                        progressLogin.setVisibility(View.GONE);
                        SnackbarUtils.showSnackBar(getBaseContext(), scrollLoginForm, R.string.login_success).setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                super.onDismissed(snackbar, event);
                                // 跳转至个人中心
                                UserActivity.startActivity(LoginActivity.this, String.valueOf(mUserBean.getUser_id()), mUserBean.getUsername());
                                saveUserInfo(mUserBean,mTokenBean,userName,pwd,boardListInfoBean.getBoards());
                                finishActivity();
                            }
                        });

                    }
                });
    }



    private void saveUserInfo(UserMeAndOtherBean result,
                              TokenBean mTokenBean,
                              String mUserAccount, String mUserPassword, List<BoardItemInfoBean> mBoardList) {

        BaseApplication.getInstantce().setIsLogin(true);
        //构造两个StringBuilder对象 拼接用逗号分隔 写入 SharedPreferences
        StringBuilder boardTitle = new StringBuilder();
        StringBuilder boardId = new StringBuilder();

        for (int i = 0, size = mBoardList.size(); i < size; i++) {
            boardTitle.append(mBoardList.get(i).getTitle());
            boardId.append(mBoardList.get(i).getBoard_id());

            if (i != size - 1) {
                boardTitle.append(AppContants.SEPARATECOMMA);
                boardId.append(AppContants.SEPARATECOMMA);
            }
        }

        //逻辑的关键信息
        SPUtils.put(getApplicationContext(), AppContants.ISLOGIN, Boolean.TRUE);
        SPUtils.put(getApplicationContext(), AppContants.LOGINTIME, System.currentTimeMillis());//获取当前时间作为登录时间
        SPUtils.put(getApplicationContext(), AppContants.USERACCOUNT, mUserAccount);
        SPUtils.put(getApplicationContext(), AppContants.USERPASSWORD, mUserPassword);

        //token 信息
        SPUtils.put(getApplicationContext(), AppContants.TOKENACCESS,  mTokenBean.getAccess_token());
        SPUtils.put(getApplicationContext(), AppContants.TOKENREFRESH, mTokenBean.getRefresh_token());
        SPUtils.put(getApplicationContext(), AppContants.TOKENTYPE, mTokenBean.getToken_type());
        SPUtils.put(getApplicationContext(), AppContants.TOKENEXPIRESIN,  mTokenBean.getExpires_in());

        //用户个人信息
        SPUtils.put(getApplicationContext(), AppContants.USERNAME, result.getUsername());
        SPUtils.put(getApplicationContext(), AppContants.USERID, String.valueOf(result.getUser_id()));
        SPUtils.put(getApplicationContext(), AppContants.USERHEADKEY, result.getAvatar().getKey());
        SPUtils.put(getApplicationContext(), AppContants.USEREMAIL, result.getEmail());

        SPUtils.put(getApplicationContext(), AppContants.BOARDTILTARRAY,  boardTitle.toString());
        SPUtils.put(getApplicationContext(), AppContants.BOARDIDARRAY, boardId.toString());

    }

}
