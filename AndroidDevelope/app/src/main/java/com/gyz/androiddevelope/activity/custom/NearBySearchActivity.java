package com.gyz.androiddevelope.activity.custom;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import com.facebook.stetho.common.LogUtil;
import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.view.nearby.CustomViewPager;
import com.gyz.androiddevelope.view.nearby.FixedSpeedScroller;
import com.gyz.androiddevelope.view.nearby.Info;
import com.gyz.androiddevelope.view.nearby.RadarViewGroup;
import com.gyz.androiddevelope.view.nearby.ViewpagerAdapter;
import com.gyz.androiddevelope.view.nearby.ZoomOutPageTransformer;

import java.lang.reflect.Field;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: guoyazhou
 * @date: 2016-05-09 10:55
 */
public class NearBySearchActivity extends BaseActivity implements ViewPager.OnPageChangeListener ,RadarViewGroup.IRadarClickListener {
    private static final String TAG = "NearBySearchActivity";

    private int[] mImgs = {R.drawable.len, R.drawable.leo, R.drawable.lep,
            R.drawable.leq, R.drawable.ler, R.drawable.les, R.drawable.mln, R.drawable.mmz, R.drawable.mna,
            R.drawable.len, R.drawable.leo, R.drawable.leq, R.drawable.les, R.drawable.lep};
    private String[] mNames = {"ImmortalZ", "唐马儒", "王尼玛", "张全蛋", "蛋花", "王大锤", "叫兽", "哆啦A梦"};
    private int mPosition;
    private FixedSpeedScroller scroller;
    private SparseArray<Info> mDatas = new SparseArray<>();

    @Bind(R.id.vp)
    CustomViewPager viewPager;
    @Bind(R.id.ry_container)
    RelativeLayout ryContainer;
    @Bind(R.id.radarViewGroup)
    RadarViewGroup radarViewGroup;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_near_by);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadData() {
        for (int i = 0; i < mImgs.length; i++) {
            Info info = new Info();
            info.setPortraitId(mImgs[i]);
            info.setAge(((int) Math.random() * 25 + 16) + "岁");
            info.setName(mNames[(int) (Math.random() * mNames.length)]);
            info.setSex(i % 3 == 0 ? false : true);
            info.setDistance(Math.round((Math.random() * 10) * 100) / 100);
            mDatas.put(i, info);
        }

        /**
         * 将Viewpager所在容器的事件分发交给ViewPager
         */
        ryContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });

        ViewpagerAdapter mAdapter = new ViewpagerAdapter(this, mDatas);
        viewPager.setAdapter(mAdapter);
        //设置缓存数为展示的数目
        viewPager.setOffscreenPageLimit(mImgs.length);
        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
        //设置切换动画
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.addOnPageChangeListener(this);
        setViewPagerSpeed(250);
        //TODO
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                radarViewGroup.setDatas(mDatas);
            }
        }, 1500);
        radarViewGroup.setiRadarClickListener(this);
    }

    /**
     * 设置ViewPager切换速度
     *
     * @param duration
     */
    private void setViewPagerSpeed(int duration) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            scroller = new FixedSpeedScroller(this, new AccelerateInterpolator());
            field.set(viewPager, scroller);
            scroller.setmDuration(duration);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mPosition = position;
    }

    @Override
    public void onPageSelected(int position) {
        radarViewGroup.setCurrentShowItem(position);
        LogUtil.v("当前位置 " + mPosition);
        LogUtil.v("速度 " + viewPager.getSpeed());
        //当手指左滑速度大于2000时viewpager右滑（注意是item+2）
        if (viewPager.getSpeed() < -1800) {

            viewPager.setCurrentItem(mPosition + 2);
            LogUtil.v("位置 " + mPosition);
            viewPager.setSpeed(0);
        } else if (viewPager.getSpeed() > 1800 && mPosition > 0) {
            //当手指右滑速度大于2000时viewpager左滑（注意item-1即可）
            viewPager.setCurrentItem(mPosition - 1);
            LogUtil.v("位置 " + mPosition);
            viewPager.setSpeed(0);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onRadarItemClick(int position) {
        viewPager.setCurrentItem(position);
    }
}
