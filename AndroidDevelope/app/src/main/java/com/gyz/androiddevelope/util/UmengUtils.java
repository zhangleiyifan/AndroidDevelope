package com.gyz.androiddevelope.util;

import com.umeng.socialize.PlatformConfig;

/**
 * @author: guoyazhou
 * @date: 2016-05-05 11:45
 */
public class UmengUtils {
    private static final String TAG = "UmengUtils";

    public static void ShareSettings() {
        //微信 appid appsecret
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("90823170", "827270f8d00e3ce24658716f058f7bdd");
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

}
