package com.gyz.androiddevelope.view.nearby;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyz.androiddevelope.R;

/**
 * @author: guoyazhou
 * @date: 2016-05-09 11:35
 */
public class ViewpagerAdapter extends PagerAdapter {
    private static final String TAG = "ViewpagerAdapter";
    private Context context;
    private SparseArray<Info> mDatas;//数据源

    public ViewpagerAdapter(Context context,SparseArray<Info> mDatas){
        this.context = context;
        this.mDatas = mDatas;

    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        final Info info = mDatas.get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.viewpager_layout, null);
        ImageView ivPortrait = (ImageView) view.findViewById(R.id.iv);
        ImageView ivSex = (ImageView) view.findViewById(R.id.iv_sex);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvDistance = (TextView) view.findViewById(R.id.tv_distance);
        tvName.setText(info.getName());
        tvDistance.setText(info.getDistance() + "km");
        ivPortrait.setImageResource(info.getPortraitId());
        if (info.getSex()) {
            ivSex.setImageResource(R.drawable.girl);
        } else {
            ivSex.setImageResource(R.drawable.boy);
        }
        ivPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "这是 " + info.getName() + " >.<", Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
