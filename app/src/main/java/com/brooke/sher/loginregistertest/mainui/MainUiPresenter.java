package com.brooke.sher.loginregistertest.mainui;

import android.support.v4.app.Fragment;

import com.brooke.sher.loginregistertest.connect.ConnectFragment;
import com.brooke.sher.loginregistertest.mine.MineFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sher on 2017/8/20.
 */

public class MainUiPresenter implements MainUiContact.Presenter {

    private MainUiContact.View mainView;
    private List<String> tabNames;
    private List<Fragment> fragments;

    public MainUiPresenter(MainUiContact.View mainView){
        this.mainView = mainView;
    }



    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initViewPager() {
        initPagerData();
        mainView.initViewPager(fragments,tabNames);
    }

    private void initPagerData() {
        tabNames = new ArrayList<>();
        tabNames.add("首页");
        tabNames.add("其他");
        tabNames.add("我的");
        fragments = new ArrayList<>();
        fragments.add(new MainFragment());
        fragments.add(new ConnectFragment());
        fragments.add(new MineFragment());


    }
}
