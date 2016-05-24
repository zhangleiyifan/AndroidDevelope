package com.gyz.androiddevelope.activity.custom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.fragment.TransformFragment;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.activity.custom.Transform3DActivity.java
 * @author: ZhaoHao
 * @date: 2016-05-23 09:22
 */
public class Transform3DActivity extends BaseActivity {
    private static final String TAG = "Transform3DActivity";
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private int[] layouts = new int[]{
        R.layout.fragment1,
        R.layout.fragment2,
        R.layout.fragment3

    };

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_3d_load);
        ButterKnife.bind(this);

        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());

        viewpager.setAdapter(myAdapter);
        viewpager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                if (position<1&&position>-1) {
//                设置视差变化
                    ViewGroup rl = (ViewGroup) viewpager.findViewById(R.id.layoutRoot);
                    for (int i = 0; i < rl.getChildCount(); i++) {
                        View view1 = rl.getChildAt(i);
                        float factor =0;
                        if (view1.getTag() == null) {
                            view1.setTag((float) (Math.random() * 2));
                        } else {
                            factor = (float) view1.getTag();
                        }
                        view1.setTranslationX(position * factor * view.getWidth());
                    }
//                    layout.setScaleX(1-Math.abs(position));
//                    layout.setScaleY(1-Math.abs(position));

//                    layout.setScaleX(Math.max(0.9f,1-Math.abs(position)));
//                    layout.setScaleY(Math.max(0.9f, 1 - Math.abs(position)));
//                    翻转
                    view.setPivotX(position < 0 ? view.getWidth() : 0f);//旋转轴
                    view.setRotationY(position * 90f);
                }

            }
        });
    }

    @Override
    protected void loadData() {

    }


    class MyAdapter extends FragmentPagerAdapter{


        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new TransformFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("layoutId",layouts[position]);
            f.setArguments(bundle);

            return f;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
