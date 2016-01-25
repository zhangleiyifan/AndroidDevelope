package com.gyz.androiddevelope.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.engine.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: guoyazhou
 * @date: 2016-01-22 15:44
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    @Bind(R.id.edtUserName)
    EditText edtUserName;
    @Bind(R.id.edtPwd)
    EditText edtPwd;
    @Bind(R.id.btnLogin)
    Button btnLogin;

    boolean needCallback;

    public static void startActivity(Context context ,boolean needCallBack) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(AppContants.NEED_CALLBACK, needCallBack);
        context.startActivity(intent);

    }

    @Override
    protected void initVariables() {

        needCallback= getIntent().getBooleanExtra(AppContants.NEED_CALLBACK,false);

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadData() {

    }

    @OnClick(R.id.btnLogin)
    public void OnClick(View view){

     User user=   User.getInstantce();
        user.setIsLogin(true);
        user.setLoginName(edtUserName.getText().toString());
        user.setUserName("张三");

        if (needCallback){
            setResult(Activity.RESULT_OK);
            finish();
        }else {
            finish();
//            or go to some activity
        }
    }

}
