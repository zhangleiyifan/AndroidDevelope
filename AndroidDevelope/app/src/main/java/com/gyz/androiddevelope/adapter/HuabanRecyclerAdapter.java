package com.gyz.androiddevelope.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseRecyclerAdapter;
import com.gyz.androiddevelope.response_bean.PinsMainEntity;
import com.gyz.androiddevelope.util.CompatUtils;
import com.gyz.androiddevelope.util.ImageLoadFresco;
import com.gyz.androiddevelope.util.ImageUtils;
import com.gyz.androiddevelope.util.Utils;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.adapter.HuabanRecyclerAdapter.java
 * @author: ZhaoHao
 * @date: 2016-06-07 13:20
 */
public class HuabanRecyclerAdapter extends BaseRecyclerAdapter {
    private static final String TAG = "HuabanRecyclerAdapter";
    private final Context context;

    private RecyclerAdapterListener listener;

    public interface RecyclerAdapterListener{
        void onClickImage(PinsMainEntity bean, View view);

        void onClickTitleInfo(PinsMainEntity bean, View view);

        void onClickInfoGather(PinsMainEntity bean, View view);

        void onClickInfoLike(PinsMainEntity bean, View view);
    }

    public HuabanRecyclerAdapter(Context context){
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_image, null);
        ViewHolderGeneral holder  = new ViewHolderGeneral(view);

        holder.tv_card_like.setCompoundDrawablesWithIntrinsicBounds(
                CompatUtils.getTintListDrawable(context, R.drawable.ic_favorite_black_18dp,
                        R.color.tint_list_grey),null,null,null);
        holder.tv_card_gather.setCompoundDrawablesWithIntrinsicBounds(
                CompatUtils.getTintListDrawable(context, R.drawable.ic_camera_black_18dp,
                        R.color.tint_list_grey), null,null,null);
        return holder;
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int RealPosition, Object data) {
        PinsMainEntity bean = (PinsMainEntity) data;

        //父类强制转换成子类 因为这个holder本来就是子类初始化的 所以可以强转
        ViewHolderGeneral viewHolder = (ViewHolderGeneral) holder;//强制类型转换 转成内部的ViewHolder

        onBindData(viewHolder, bean);
        onBindListener(viewHolder, bean);//初始化点击事件
    }

    private void onBindData(ViewHolderGeneral holder, PinsMainEntity bean) {

        //检查图片信息
        if (checkInfoContext(bean)) {
            holder.ll_title_info.setVisibility(VISIBLE);

            String title = bean.getRaw_text();//图片的文字描述
            int like = bean.getLike_count();//被喜欢数量
            int gather = bean.getRepin_count();//被转采的数量
            if (!TextUtils.isEmpty(title)) {
                holder.tv_card_title.setVisibility(VISIBLE);
                holder.tv_card_title.setText(title);
            } else {
                holder.tv_card_title.setVisibility(GONE);
            }
            holder.tv_card_like.setText(" " + like);
            holder.tv_card_gather.setText(" " + gather);
        } else {
            holder.ll_title_info.setVisibility(GONE);
        }

//        String url_img = mUrlFormat + bean.getFile().getKey()+"_fw320sf";
        String url_img = String.format(context.getResources().getString(R.string.url_image_general), bean.getFile().getKey());

//        String mImageUrl = "http://img.hb.aicdn.com/1d16a79ac7cffbec844eb48e7e714c9f8c0afffc7f997-ZZCJsm";

        if (Utils.checkIsGif(bean.getFile().getType())) {
            holder.ibtn_card_gif.setVisibility(VISIBLE);
        } else {
            holder.ibtn_card_gif.setVisibility(INVISIBLE);
        }

        float ratio = Utils.getAspectRatio(bean.getFile().getWidth(), bean.getFile().getHeight());
        //长图 "width":440,"height":5040,
        holder.img_card_image.setAspectRatio(ratio);//设置宽高比
        Drawable dProgressImage = CompatUtils.getTintListDrawable(context, R.drawable.ic_toys_black_48dp, R.color.tint_list_pink);
        new ImageLoadFresco.LoadImageFrescoBuilder(context,holder.img_card_image,url_img)
                .setProgressBarImage(dProgressImage).build();
//        ImageUtils.loadImageByFresco(context,holder.img_card_image,url_img);

    }

    /**
     * 检查三项信息 任何一项不为空 都返回true
     *
     * @param bean
     * @return
     */
    private boolean checkInfoContext(PinsMainEntity bean) {

        String title = bean.getRaw_text();//图片的文字描述
        int like = bean.getLike_count();//被喜欢数量
        int gather = bean.getRepin_count();//被转采的数量

        if (!TextUtils.isEmpty(title)) {
            return true;
        } else if (like > 0 || gather > 0) {
            return true;
        }

        return false;
    }


    private void onBindListener(ViewHolderGeneral holder, final PinsMainEntity bean) {

        holder.rl_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                listener.onClickImage(bean, v);
            }
        });

        holder.ll_title_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                listener.onClickTitleInfo(bean, v);
            }
        });

        holder.tv_card_gather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                listener.onClickInfoGather(bean, v);
            }
        });

        holder.tv_card_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                listener.onClickInfoLike(bean, v);
//                    RxBus.getDefault().post(bean);
            }

        });

    }

    public void setRecyclerAdapterListener(RecyclerAdapterListener listener){
        this.listener = listener;
    }

    public static class ViewHolderGeneral extends RecyclerView.ViewHolder {
        //这个CardView采用两层操作
        public final View mView;

        public final FrameLayout rl_image;//第一层 包含图片和播放按钮
        public final SimpleDraweeView img_card_image;
        public final ImageButton ibtn_card_gif;

        public final LinearLayout ll_title_info;//第二层 包含描述 图片信息
        public final TextView tv_card_title;//第二层 描述title

        public final LinearLayout ll_info;//第二层的子类 包含图片被采集和喜爱的信息
        public final TextView tv_card_gather;
        public final TextView tv_card_like;

        public ViewHolderGeneral(View view) {
            super(view);
            mView = view;
            rl_image = (FrameLayout) view.findViewById(R.id.framelayout_image);
            img_card_image = (SimpleDraweeView) view.findViewById(R.id.img_card_image);//主图
            ibtn_card_gif = (ImageButton) view.findViewById(R.id.ibtn_card_gif);//播放按钮

            ll_title_info = (LinearLayout) view.findViewById(R.id.linearlayout_title_info);//图片所有文字信息
            tv_card_title = (TextView) view.findViewById(R.id.tv_card_title);//描述的title

            ll_info = (LinearLayout) view.findViewById(R.id.linearlayout_info);//文字子类
            tv_card_gather = (TextView) view.findViewById(R.id.tv_card_gather);
            tv_card_like = (TextView) view.findViewById(R.id.tv_card_like);
        }

    }
}
