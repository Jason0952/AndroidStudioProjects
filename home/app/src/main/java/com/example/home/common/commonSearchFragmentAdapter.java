package com.example.home.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Carson_Ho on 16/7/22.
 */
public class commonSearchFragmentAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"資訊", "菜單", "評價&留言","附近景點"};

    public commonSearchFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new commonSearchFragment2();
        } else if (position == 2) {
            return new commonSearchFragment3();
        }else if (position==3){
            return new commonSearchFragment4();
        }
        return new commonSearchFragment1();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

}
