package com.gyz.androiddevelope.activity.huaban;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.adapter.TngouPicViewPagerAdapter;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.base.BaseApplication;
import com.gyz.androiddevelope.base.BaseFragment;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.fragment.huaban.UserLikeFragment;
import com.gyz.androiddevelope.response_bean.FollowUserOperateBean;
import com.gyz.androiddevelope.response_bean.UserMeAndOtherBean;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;
import com.gyz.androiddevelope.util.FastBlurUtil;
import com.gyz.androiddevelope.util.ImageLoadFresco;
import com.gyz.androiddevelope.util.L;
import com.gyz.androiddevelope.util.SPUtils;
import com.gyz.androiddevelope.util.UserUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.activity.huaban.UserActivity.java
 * @author: ZhaoHao
 * @date: 2016-06-15 17:22
 */
public class UserActivity extends BaseActivity {
    private static final String TAG = "UserActivity";
    private static final String TYPE_KEY = "TYPE_KEY";
    private static final String TYPE_TITLE = "TYPE_TITLE";

    @Bind(R.id.imgPhoto)
    SimpleDraweeView imgPhoto;
    @Bind(R.id.tvUserName)
    TextView tvUserName;
    @Bind(R.id.tvUserInfo)
    TextView tvUserInfo;
    @Bind(R.id.tv_user_friend)
    TextView tvUserFriend;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.appbarLayout)
    AppBarLayout appbarLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    public String userId;
    public String userName;
    public boolean isMe;
    private boolean isFollow;
    private String[] titles;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    public static void startActivity(Context context, String key, String title) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(TYPE_TITLE, title);
        intent.putExtra(TYPE_KEY, key);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables() {

        userName = getIntent().getStringExtra(TYPE_TITLE);
        userId = getIntent().getStringExtra(TYPE_KEY);

        String userId = (String) SPUtils.get(this, AppContants.USERID, "");
        isMe = this.userId.equals(userId);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);//设置打开时的文字颜色
        titles = getResources().getStringArray(R.array.title_user_info);
    }

    @Override
    protected void loadData() {
        getHttpsUserInfo();
        setTabFragment();
    }

    private void setTabFragment() {

        fragmentList.add(UserLikeFragment.newInstance(userId, UserLikeFragment.TYPE_PIN));
        fragmentList.add(UserLikeFragment.newInstance(userId, UserLikeFragment.TYPE_LIKE));

        TngouPicViewPagerAdapter pagerAdapter = new TngouPicViewPagerAdapter(getSupportFragmentManager(), fragmentList, Arrays.asList(titles));
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(isMe ? R.menu.menu_user_me : R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_follow:
                httpFollowUser();
                break;
            case R.id.action_user_setting:
                //// TODO 用户设置相关跳转
                break;
        }

        // boolean Return false to allow normal menu processing to
        // proceed, true to consume it here.
        // false：允许继续事件传递  true：就自己消耗事件 不再传递
        return true;
    }

    //关注
    private void httpFollowUser() {

        RxUtil.subscribeAll(new Func1<String, Observable<FollowUserOperateBean>>() {
            @Override
            public Observable<FollowUserOperateBean> call(String s) {
                String operate = isFollow ? AppContants.OPERATEUNFOLLOW : AppContants.OPERATEFOLLOW;
                return ReUtil.getApiManager(AppContants.HUABAN_HTTP).httpsFollowUserOperate(UserUtils.getAuthorizations(true, getApplicationContext()), userId, operate);
            }
        }, new Subscriber<FollowUserOperateBean>() {
            @Override
            public void onCompleted() {
                int res = isFollow ? R.drawable.ic_done_black_24dp : R.drawable.ic_loyalty_black_24dp;
                setFabAnimation(res, floatingActionButton);
            }

            @Override
            public void onError(Throwable e) {
                setFabAnimation(R.drawable.ic_report_black_24dp, floatingActionButton);
            }

            @Override
            public void onNext(FollowUserOperateBean followUserOperateBean) {
                isFollow = !isFollow;
            }
        });
    }

    private void setFabAnimation(final int res, FloatingActionButton floatingActionButton) {
        floatingActionButton.hide(new FloatingActionButton.OnVisibilityChangedListener() {
            @Override
            public void onHidden(FloatingActionButton fab) {
                super.onHidden(fab);

                fab.setImageResource(res);
                fab.show();
                //TODO 通知fragment刷新数据
            }
        });
    }

    /**
     * 获取用户信息并展示
     */
    private void getHttpsUserInfo() {
        RxUtil.subscribeOnNext(new Func1<String, Observable<UserMeAndOtherBean>>() {
            @Override
            public Observable<UserMeAndOtherBean> call(String s) {
                return ReUtil.getApiManager(AppContants.HUABAN_HTTP).httpsUserInfoRx(UserUtils.getAuthorizations(BaseApplication.getInstantce().isLogin(), getBaseContext()), userId);
            }
        }, new Action1<UserMeAndOtherBean>() {
            @Override
            public void call(UserMeAndOtherBean userMeAndOtherBean) {

                setUserInfo(userMeAndOtherBean);
            }
        });
    }

    private void setUserInfo(UserMeAndOtherBean bean) {
        //设置用户头像和 模糊背景
        String url = bean.getAvatar().getKey();
        if (!TextUtils.isEmpty(url)) {
            if (!url.contains("http://")) {
                url = String.format(getString(R.string.url_image_small), url);
            }

            new ImageLoadFresco.LoadImageFrescoBuilder(getApplicationContext(), imgPhoto, url)
                    .setIsCircle(true, true).setBitmapDataSubscriber(new BaseBitmapDataSubscriber() {
                @Override
                protected void onNewResultImpl(Bitmap bitmap) {
                    //得到缓存中的Bitmap对象 这里可以进行操作
                    //构造Drawable对象 模糊化设置给View控件
                    if (bitmap != null) {
                        final Drawable drawable = new BitmapDrawable(getResources(), FastBlurUtil.doBlur(bitmap, 25, false));
                        appbarLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                appbarLayout.setBackgroundDrawable(drawable);
                            }
                        });
                    }
                }

                @Override
                protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                    L.e(TAG, "onFailureImpl...");
                }
            }).build();

        }

        //设置用户文字信息
        tvUserName.setText(bean.getUsername());
        String location = bean.getProfile().getLocation();
        String job = bean.getProfile().getJob();
        StringBuffer buffer = new StringBuffer();
        if (!TextUtils.isEmpty(location)) {
            buffer.append(location);
            buffer.append(" ");
        }
        if (!TextUtils.isEmpty(job)) {
            buffer.append(job);
        }
        if (!TextUtils.isEmpty(buffer)) {
            tvUserInfo.setText(buffer);
        }

        tvUserFriend.setText(String.format(getString(R.string.text_fans_attention), bean.getFollower_count(), bean.getFollowing_count()));

        //设置关注 根据网络返回数据 主要判断是否已经关注过
        isFollow = bean.getFollowing() == 1;
//        设置网络返回数据 主要判断是否已经关注过
        floatingActionButton.setImageResource(isMe ? R.drawable.ic_add_black_24dp : (isFollow ? R.drawable.ic_done_black_24dp : R.drawable.ic_loyalty_black_24dp));
        floatingActionButton.show();
    }

}
