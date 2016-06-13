package com.gyz.androiddevelope.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.base.BaseFragment;
import com.gyz.androiddevelope.base.BaseRecyclerFragment;
import com.gyz.androiddevelope.fragment.TestFragment;
import com.gyz.androiddevelope.fragment.Tngou.TngouFragment;
import com.gyz.androiddevelope.fragment.huaban.HuabanFragment;
import com.gyz.androiddevelope.fragment.zhihu.ZhiHu2Fragment;
import com.gyz.androiddevelope.listener.OnRecyclerRefreshListener;
import com.gyz.androiddevelope.listener.OnSwipeRefreshFragmentListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnSwipeRefreshFragmentListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.contain_home)
    FrameLayout containHome;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    protected static final int[] ints = new int[]{R.color.colorPrimaryDark};
    private BaseFragment zhihuFragment, tngouPicFragment, testFragment, huabanFragment;
    long firstTime = 0;

    //刷新的接口 子Fragment实现
    private OnRecyclerRefreshListener mListenerRefresh;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        getSwipeBackLayout().setEnableGesture(false);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        swipeRefreshLayout.setColorSchemeResources(ints);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListenerRefresh.onRecyclerRefresh();
            }
        });

        zhihuFragment = new ZhiHu2Fragment();
        switchFragment(zhihuFragment);

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Snackbar sb = Snackbar.make(containHome, R.string.exit_app, Snackbar.LENGTH_SHORT);
                sb.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                sb.show();
                firstTime = secondTime;
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            SettingsActivity.startActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            switchFragment(zhihuFragment);
            toolbar.setTitle(getString(R.string.title_zhihu));

        } else if (id == R.id.nav_test) {

            if (testFragment == null)
                testFragment = new TestFragment();
            switchFragment(testFragment);

        } else if (id == R.id.nav_picshow) {

            if (tngouPicFragment == null)
                tngouPicFragment = new TngouFragment();
            switchFragment(tngouPicFragment);
            toolbar.setTitle(getString(R.string.title_tngou));

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.navTest) {
            if (huabanFragment == null) {
                huabanFragment = new HuabanFragment();
            }

            Bundle bundle = new Bundle();
            bundle.putString(HuabanFragment.KEY, "beauty");
            huabanFragment.setArguments(bundle);
            switchFragment(huabanFragment);
            toolbar.setTitle("花瓣");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    protected void switchFragment(BaseFragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (fragment != null) {
            if (fragment instanceof BaseRecyclerFragment) {
                mListenerRefresh = (OnRecyclerRefreshListener) fragment;
            }
            ft.replace(R.id.contain_home, fragment);
        }
        ft.commit();
    }

    @Override
    public void OnRefreshState(boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
    }
}
