package com.brooke.sher.loginregistertest.register;

import com.brooke.sher.loginregistertest.BasePresenter;
import com.brooke.sher.loginregistertest.BaseView;

/**
 * Created by Sher on 2017/8/20.
 */

public class RegisterContact {
    interface View extends BaseView<Presenter>{
        void goNextPage();
    };

    interface Presenter extends BasePresenter{
        void getCode();
    }
}
