package com.brooke.sher.loginregistertest.login;

import com.brooke.sher.loginregistertest.BasePresenter;
import com.brooke.sher.loginregistertest.BaseView;

/**
 * Created by wangyang on 2017/8/28.
 */

public class LoginContact {
    interface View extends BaseView<Presenter>{
        void showToast(String toastThings);
    }

    interface Presenter extends BasePresenter {
        void attempLogin(String phone,String passwd);
    }
}
