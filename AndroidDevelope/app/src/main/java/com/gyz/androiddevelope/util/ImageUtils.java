package com.gyz.androiddevelope.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.engine.AppContants;
import com.squareup.picasso.Picasso;

/**
 * @author: guoyazhou
 * @date: 2016-04-26 17:47
 */
public class ImageUtils {
    private static final String TAG = "ImageUtils";

    public static void loadImageByPicasso(Context context, String imgUrl, ImageView imageView) {

        boolean boo = (boolean) SPUtils.get(context, AppContants.SP_LOAD_PIC_BY_MOBILE_NET, false);
        if (boo) {

            Picasso.with(context).load(imgUrl).into(imageView);
        } else {

            if (NetworkUtils.isWifi(context)) {
                Picasso.with(context).load(imgUrl).into(imageView);
            } else {
                Picasso.with(context).load(R.mipmap.ic_header_logo).into(imageView);
            }
        }


    }

    public static void loadImageByFresco(Context context, SimpleDraweeView simpleDraweeView, String picUrl) {

        boolean boo = (boolean) SPUtils.get(context, AppContants.SP_LOAD_PIC_BY_MOBILE_NET, false);

        if (boo) {
            simpleDraweeView.setImageURI(Uri.parse(picUrl));
        } else {
            if (NetworkUtils.isWifi(context)) {
                simpleDraweeView.setImageURI(Uri.parse(picUrl));
            } else {
                simpleDraweeView.setImageResource(R.mipmap.prograss);
            }
        }

    }


    /**
     * CardItemView 图片展示
     * @param context
     * @param simpleDraweeView
     * @param picUrl
     * @param retryEnable
     */
    public static void setControllerByFresco(Context context, SimpleDraweeView simpleDraweeView, String picUrl, boolean retryEnable) {

        boolean boo = (boolean) SPUtils.get(context, AppContants.SP_LOAD_PIC_BY_MOBILE_NET, false);

        if (boo) {
            setController(context, simpleDraweeView, picUrl, retryEnable);

        } else {
            if (NetworkUtils.isWifi(context)) {

                setController(context, simpleDraweeView, picUrl, retryEnable);
            } else {

                simpleDraweeView.setImageResource(R.mipmap.prograss);
            }
        }
    }

    public static void setController(Context context, SimpleDraweeView simpleDraweeView, String picUrl, boolean retryEnable) {

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(picUrl))
                .setTapToRetryEnabled(retryEnable)
                .setOldController(simpleDraweeView.getController())
                .build();

        simpleDraweeView.setController(controller);
    }

}
