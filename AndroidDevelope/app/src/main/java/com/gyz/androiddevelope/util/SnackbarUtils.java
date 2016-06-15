package com.gyz.androiddevelope.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.gyz.androiddevelope.R;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.util.SnackbarUtils.java
 * @author: ZhaoHao
 * @date: 2016-06-15 13:45
 */
public class SnackbarUtils {
    private static final String TAG = "SnackbarUtils";

    public static Snackbar showSnackBar(Context context,View rootView, int message) {
        Snackbar sb = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
        sb.getView().setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        sb.show();

        return sb;
    }
}
