package com.brooke.sher.loginregistertest.mainui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.brooke.sher.loginregistertest.R;

import java.util.List;

import android2.ui.BaseActivity;

/**
 *
 * 所有显示操作放在这里
 *
 */
public class MainActivity extends BaseActivity implements MainUiContact.View{

    private TabLayout tablayout;
    private ViewPager viewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private MainUiPresenter persenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        persenter = new MainUiPresenter(this);
        persenter.initViewPager();
    }

    @Override
    public void initViewPager(List<Fragment> fragments, List<String> tabNames) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPagerAdapter = new ViewPagerAdapter(fragmentManager,tabNames,fragments);
        viewPager.setAdapter(mViewPagerAdapter);
        tablayout.setupWithViewPager(viewPager);
    }


    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<String> tabNames;
        private List<Fragment> fragments;

        public ViewPagerAdapter(FragmentManager fm,List<String> tabNames,List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
            this.tabNames = tabNames;
        }


        @Override
        public int getCount() {
            return tabNames.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames.get(position);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments.get(position);
        }
    }


}
