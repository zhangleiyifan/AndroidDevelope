package com.gyz.androiddevelope.util;

import android.content.Context;

import com.gyz.androiddevelope.engine.AppContants;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.util.UserUtils.java
 * @author: ZhaoHao
 * @date: 2016-06-16 11:27
 */
public class UserUtils {
    private static final String TAG = "UserUtils";




    public static String getAuthorizations(boolean isLogin,Context mContext) {

        String temp = " ";
        if (isLogin) {
            return SPUtils.get(mContext, AppContants.TOKENTYPE, temp) + temp
                    + SPUtils.get(mContext, AppContants.TOKENACCESS, temp);
        }
        return AppContants.HuaBanLoginAuth;
    }
}
