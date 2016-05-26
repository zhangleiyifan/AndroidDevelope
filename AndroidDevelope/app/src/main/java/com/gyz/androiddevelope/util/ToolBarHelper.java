package com.gyz.androiddevelope.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.gyz.androiddevelope.R;

import java.lang.reflect.Field;

/**
 * @author: guoyazhou
 * @date: 2016-03-18 17:31
 */
public class ToolBarHelper {
    /*上下文，创建view的时候需要用到*/
    private Context mContext;
    /*toolbar*/
    private Toolbar mToolBar;
    /*
    * 两个属性
    * 1、toolbar是否悬浮在窗口之上
    * 2、toolbar的高度获取
    * */
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            R.attr.actionBarSize
    };

    public ToolBarHelper(Context context, Toolbar toolbar) {
        this.mContext = context;
       this.mToolBar = toolbar;
    }



    public Toolbar getToolBar() {
        return mToolBar;
    }


    public static int getTitleHeight(Context context) {
        int titleHeight = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            titleHeight = getStatusBarHeight(context);
        }

        return titleHeight;
    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
}
