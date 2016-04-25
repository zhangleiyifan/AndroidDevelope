package com.gyz.androiddevelope.adapter;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.response_bean.GalleryBean;
import com.gyz.androiddevelope.util.ScreenUtils;

/**
 * @author: guoyazhou
 * @date: 2016-04-21 16:47
 */
public class TgPicListAdapter extends BaseRecyclerAdapter<GalleryBean> {
    private static final String TAG = "TgPicListAdapter";

    private Context context;
    private int imgWidth,imgHeight;

    public TgPicListAdapter(Context context) {
        this.context = context;
        imgWidth = ScreenUtils.getScreenWidth(context)/3;
        imgHeight = ScreenUtils.getScreenHeight(context)/3;
    }


    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tg_pic_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int RealPosition, GalleryBean data) {

        MyViewHolder viewHolder = (MyViewHolder) holder;

//        Picasso.with(context).load(AppContants.TG_IMAGE_HEAD + data.getImg())
//                .resize(imgWidth, imgHeight)
//                .config(Bitmap.Config.RGB_565)
//                .centerCrop()
//                .tag(new Object())
//                .into(viewHolder.img);
        viewHolder.img.setImageURI(Uri.parse(AppContants.TG_IMAGE_HEAD + data.getImg()));
        viewHolder.txtCount.setText(String.valueOf(data.getSize()));
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView img;
        public TextView txtCount;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (SimpleDraweeView) itemView.findViewById(R.id.item_img);
            txtCount = (TextView) itemView.findViewById(R.id.txtImgCount);
        }
    }
}
