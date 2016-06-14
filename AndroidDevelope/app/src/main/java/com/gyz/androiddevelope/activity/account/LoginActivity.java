package com.gyz.androiddevelope.activity.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseToolbarActivity;
import com.gyz.androiddevelope.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        return "登入";
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

        String userName =actvUsername.getText().toString().toLowerCase().trim();
        String pwd = editPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userName)|| TextUtils.isEmpty(pwd)){
            ToastUtil.showShort(getBaseContext(),getString(R.string.null_info));
        }




    }
}
