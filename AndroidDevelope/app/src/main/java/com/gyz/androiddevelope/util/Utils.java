package com.gyz.androiddevelope.util;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;


public class Utils {
    /**
     * @param value
     * @param defaultValue
     * @return integer
     * @throws
     * @Title: convertToInt
     * @Description: 对象转化为整数数字类型
     */
    public final static int convertToInt(Object value, int defaultValue) {
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).intValue();
            } catch (Exception e1) {
                return defaultValue;
            }
        }
    }

    /**
     * @param activity
     * @param msg
     * @return ProgressDialog
     * @Title: createProgressDialog
     * @Description: 创建ProgressDialog
     */
    public static ProgressDialog createProgressDialog(final Context activity, final String msg) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(msg);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /**
     * 获取符合要求的等级数组
     * @param currentLv
     * @return
     */
    public static List<String> getLvs(int currentLv) {

        List<String> list = new ArrayList<>();

        if ((currentLv - 3) <= 0) {
            for (int i = 0; i < 7; i++) {
                list.add("V" + i);
            }
            list.add("...");
            return list;
        }

        if ((currentLv + 3) >= 10) {

            list.add("...");
            for (int i = 4; i < 11; i++) {
                list.add("V" + i);
            }
            return list;
        }

        if ((currentLv > 3) && (currentLv < 7)) {

            list.add("...");
            int temp = -3;

            while (temp <= 3) {

                list.add("V" + (currentLv + temp));
                temp++;
            }

            list.add("...");
            return list;

        }

        return list;
    }



    /**
     * 检查图片类型是否为 git
     *
     * @param type
     * @return
     */
    public static boolean checkIsGif(String type) {
        if (type == null||type.isEmpty()) {
            return false;
        }

        if (type.contains("gif") || type.contains("GIF")) {
            return true;
        }
        return false;
    }
    /**
     * 计算宽高比
     *
     * @param width
     * @param height
     * @return
     */
    public static float getAspectRatio(int width, int height) {
        float ratio = (float) width / (float) height;
        //宽高比<0.7 表示长图 需要截断处理
        if (ratio < 0.7) {
            return 0.7f;
        }
        //// TODO: 2016/5/11 0011 ratio>3会导致图片不能显示
        return ratio;
    }

}
