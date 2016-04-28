package com.gyz.androiddevelope.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.gyz.androiddevelope.base.BaseFragment;

import java.util.List;

/**
 * @author: guoyazhou
 * @date: 2016-04-21 15:22
 */
public class TngouPicViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "TngouPicViewPagerAdapter";

    private List<BaseFragment> fragments;
    private List<String> titleList;

    public TngouPicViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> titleList) {
        super(fm);
        this.fragments = fragments;
        this.titleList = titleList;

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position>=titleList.size()){
            return titleList.get(titleList.size()-1);
        }
        return titleList.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (fragments.size() <= position) {
            position = position % fragments.size();
        }
        return super.instantiateItem(container, position);
    }
}
