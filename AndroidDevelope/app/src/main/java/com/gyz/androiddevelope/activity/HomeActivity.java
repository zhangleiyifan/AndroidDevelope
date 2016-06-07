package com.gyz.androiddevelope.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.base.BaseFragment;
import com.gyz.androiddevelope.fragment.TestFragment;
import com.gyz.androiddevelope.fragment.Tngou.TngouFragment;
import com.gyz.androiddevelope.fragment.huaban.HuabanFragment;
import com.gyz.androiddevelope.fragment.zhihu.ZhiHuFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.contain_home)
    FrameLayout containHome;

    private BaseFragment mainFragment,tngouPicFragment, testFragment,huabanFragment;
    long firstTime = 0;

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

        mainFragment = new ZhiHuFragment();
        switchFragment(mainFragment);

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
                Snackbar sb = Snackbar.make(containHome, R.string.exit_app,Snackbar.LENGTH_SHORT);
                sb.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                sb.show();
                firstTime = secondTime;
            }else {
               finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
                SettingsActivity.startActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            switchFragment(mainFragment);
            toolbar.setTitle(getString(R.string.title_zhihu));

        } else if (id == R.id.nav_test) {

            if (testFragment == null)
                testFragment = new TestFragment();
            switchFragment(testFragment);

        } else if (id == R.id.nav_picshow) {

            if (tngouPicFragment ==null)
                tngouPicFragment = new TngouFragment();
            switchFragment(tngouPicFragment);
            toolbar.setTitle(getString(R.string.title_tngou));

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.navTest) {
            if (huabanFragment==null){
                huabanFragment = new HuabanFragment();
            }
            switchFragment(huabanFragment);
//            toolbar.setTitle("");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    protected void switchFragment(BaseFragment fragment) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (fragment != null) {
            ft.replace(R.id.contain_home, fragment);
        }
        ft.commit();

    }

}
