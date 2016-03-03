package com.gyz.androiddevelope.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gyz.androiddevelope.R;

/**
 * @author: guoyazhou
 * @date: 2016-02-23 16:54
 */
public class PwdView extends LinearLayout implements View.OnKeyListener {

    private static final String TAG = "PwdView";

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;

    public PwdView(Context context) {
        super(context);
        initView(context);
    }

    public PwdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PwdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.view_pwd, null);

        editText1 = (EditText) view.findViewById(R.id.edtPwd1);
        editText2 = (EditText) view.findViewById(R.id.edtPwd2);
        editText3 = (EditText) view.findViewById(R.id.edtPwd3);
        editText4 = (EditText) view.findViewById(R.id.edtPwd4);


        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    editText2.setFocusable(true);
                    editText2.setFocusableInTouchMode(true);
                    editText2.requestFocus();
                }
            }
        });

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("".equals(s.toString())) {
                    editText1.setFocusable(true);
                    editText1.setFocusableInTouchMode(true);
                    editText1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    editText3.setFocusable(true);
                    editText3.setFocusableInTouchMode(true);
                    editText3.requestFocus();
                }
            }
        });

        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("".equals(s.toString())) {
                    editText2.setFocusable(true);
                    editText2.setFocusableInTouchMode(true);
                    editText2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    editText4.setFocusable(true);
                    editText4.setFocusableInTouchMode(true);
                    editText4.requestFocus();
                }
            }
        });

        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("".equals(s.toString())) {
                    editText3.setFocusable(true);
                    editText3.setFocusableInTouchMode(true);
                    editText3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText2.setOnKeyListener(this);
        editText3.setOnKeyListener(this);
        editText4.setOnKeyListener(this);

        addView(view);

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_DEL) {


            switch (v.getId()) {
                case R.id.edtPwd1:
                    editText1.setText("");
                    break;

                case R.id.edtPwd2:
                    editText2.setText("");
                    break;

                case R.id.edtPwd3:
                    editText3.setText("");
                    break;

                case R.id.edtPwd4:
                    editText4.setText("");
                    break;

            }
        }
        return false;
    }
}
