package com.brooke.sher.loginregistertest.mainui;


import android.support.v4.app.Fragment;

import com.brooke.sher.loginregistertest.BasePresenter;
import com.brooke.sher.loginregistertest.BaseView;

import java.util.List;

/**
 * Created by Sher on 2017/8/20.
 */

public class MainUiContact {

    interface View extends BaseView<Presenter>{
        void initViewPager(List<Fragment> fragments, List<String> tabNames);
    }

    interface Presenter extends BasePresenter{
        void initViewPager();
    }
}
