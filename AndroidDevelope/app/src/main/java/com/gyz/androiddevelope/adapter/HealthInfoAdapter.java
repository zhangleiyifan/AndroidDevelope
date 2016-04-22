package com.gyz.androiddevelope.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.response_bean.HealthInfoList;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author: guoyazhou
 * @date: 2016-03-08 17:01
 */
public class HealthInfoAdapter extends RecyclerView.Adapter {
    private static final String TAG = "HealthInfoAdapter";

    private List<HealthInfoList.HealthInfo> list;
    private OnItemClickListener listener;
    private Context context;

    public HealthInfoAdapter( List<HealthInfoList.HealthInfo> list ,Context context){
        this.list = list;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itme_health_info,null);

        return new HealthInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        HealthInfoViewHolder viewHolder = (HealthInfoViewHolder) holder;
        final HealthInfoList.HealthInfo healthInfo = list.get(position);
        viewHolder.txtKeyWords.setText(healthInfo.keywords);
        viewHolder.txtTitle.setText(healthInfo.title);
        Picasso.with(context).load(AppContants.TG_IMAGE_HEAD +healthInfo.img).into(viewHolder.img);


        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.layoutContent) {
                    if (listener != null) {
                        listener.onItemClick(position,healthInfo);
                    }
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class HealthInfoViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView txtTitle;
        public TextView txtKeyWords;
        public View rootView;

        public HealthInfoViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtKeyWords = (TextView) itemView.findViewById(R.id.txtKeyWords);
            rootView = itemView.findViewById(R.id.layoutContent);

        }
    }

    /**
     * 内部接口回调方法
     */public interface OnItemClickListener {
        void onItemClick(int position, HealthInfoList.HealthInfo object);
    }
}
