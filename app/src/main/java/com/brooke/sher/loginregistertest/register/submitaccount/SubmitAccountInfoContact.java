package com.brooke.sher.loginregistertest.register.submitaccount;

import com.brooke.sher.loginregistertest.BasePresenter;
import com.brooke.sher.loginregistertest.BaseView;

/**
 * Created by Sher on 2017/8/20.
 */

public class SubmitAccountInfoContact {
    interface View extends BaseView<Presenter>{
        void showToast(String info);
    };

    interface Presenter extends BasePresenter{
        void register(String username,String passwd,String passwdAgain,String phone);
    }
}
