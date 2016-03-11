package com.gyz.androiddevelope.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.response_bean.LatestNewsBean;
import com.squareup.picasso.Picasso;

/**
 * @author: guoyazhou
 * @date: 2016-03-11 17:23
 */
public class HomeNewsAdapter extends BaseRecyclerAdapter<LatestNewsBean.Story> {
    private static final String TAG = "HomeNewsAdapter";

    private Context context;

    public HomeNewsAdapter (Context context){
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itme_health_info, null);

        return new MyViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int RealPosition, LatestNewsBean.Story data) {

        MyViewHolder viewHolder = (MyViewHolder) holder;
        final LatestNewsBean.Story story = data;
        viewHolder.txtTitle.setText(story.title);
        Picasso.with(context).load(story.images.get(0)).into(viewHolder.img);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView txtTitle;
        public View rootView;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            rootView = itemView.findViewById(R.id.layoutContent);

        }
    }
}
