package com.gyz.androiddevelope.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseRecyclerAdapter;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.response_bean.Story;
import com.gyz.androiddevelope.util.ImageUtils;
import com.gyz.androiddevelope.util.SPUtils;

/**
 * @author: guoyazhou
 * @date: 2016-03-11 17:23
 */
public class HomeNewsAdapter extends BaseRecyclerAdapter<Story> {
    private static final String TAG = "HomeNewsAdapter";

    private Context context;
    private String str;

    public HomeNewsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itme_health_info, null);

        return new MyViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int RealPosition, Story story) {

        MyViewHolder viewHolder = (MyViewHolder) holder;
        if (story.type == AppContants.TITLE_TYPE) {
//            仅显示标题
            viewHolder.img.setVisibility(View.GONE);
            viewHolder.txtTitle.setVisibility(View.GONE);
            viewHolder.txtTopic.setVisibility(View.VISIBLE);
            viewHolder.layoutItemBg.setBackgroundResource(0);
            viewHolder.txtTopic.setText(story.title);
        } else {

            str = (String) SPUtils.get(context, AppContants.READ_ID, "");
            if (str.contains(String.valueOf(story.id))) {
                viewHolder.txtTitle.setTextColor(context.getResources().getColor(R.color.color_999999));
            } else {
                viewHolder.txtTitle.setTextColor(context.getResources().getColor(R.color.colorBlack));
            }
            viewHolder.img.setVisibility(View.VISIBLE);
            viewHolder.txtTitle.setVisibility(View.VISIBLE);
            viewHolder.txtTopic.setVisibility(View.GONE);
            ImageUtils.loadImageByPicasso(context,story.images.get(0),viewHolder.img);
//            Picasso.with(context).load(story.images.get(0)).into(viewHolder.img);
            viewHolder.txtTitle.setText(story.title);

        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView txtTitle;
        public TextView txtTopic;
        public View rootView;
        public RelativeLayout layoutItemBg;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtTopic = (TextView) itemView.findViewById(R.id.txtTopic);
            rootView = itemView.findViewById(R.id.layoutContent);
            layoutItemBg = (RelativeLayout) itemView.findViewById(R.id.layoutItemBg);
        }
    }
}
