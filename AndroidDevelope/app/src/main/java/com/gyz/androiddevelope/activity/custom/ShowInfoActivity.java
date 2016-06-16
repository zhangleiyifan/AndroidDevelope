package com.gyz.androiddevelope.activity.custom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.engine.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowInfoActivity extends BaseActivity {

    @Bind(R.id.txtName)
    TextView txtName;
    @Bind(R.id.txtPwd)
    TextView txtPwd;

    public static void startActivity(Context context) {

        Intent intent = new Intent(context, ShowInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_do_sth);
        ButterKnife.bind(this);

    }

    @Override
    protected void loadData() {

        txtName.setText(User.getInstantce().getUserName());
        txtPwd.setText(User.getInstantce().getLoginName());

    }

}
