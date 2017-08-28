package com.brooke.sher.loginregistertest.login;

import android.text.TextUtils;

import com.brooke.sher.loginregistertest.R;
import com.brooke.sher.loginregistertest.data.UserInfo;
import com.brooke.sher.loginregistertest.net.HttpMethods;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.R.attr.value;
import static android.R.string.cancel;
import static com.brooke.sher.loginregistertest.R.id.email;

/**
 * Created by wangyang on 2017/8/28.
 */

public class LoginPresenter implements LoginContact.Presenter {

    private LoginContact.View view;

    public LoginPresenter(LoginContact.View view){
        this.view = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }


    @Override
    public void attempLogin(String phone, String passwd) {
        if (TextUtils.isEmpty(passwd) || !isPasswordValid(passwd)) {
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            return;
        }

            Observer<UserInfo> observable = new Observer<UserInfo>() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onNext(UserInfo value) {
                    view.showToast(value.getUserName() + "test");
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    view.showToast("失败");
                }

                @Override
                public void onComplete() {
                    view.showToast("成功");
                }
            };
            HttpMethods.getInstance().login(observable, passwd, phone);
    }

    /**
     * 待补全
     * @param phone
     * @return
     */
        private boolean isPhoneValid(String phone) {
            return true;
        }

        private boolean isPasswordValid(String password) {
            return password.length() > 4;
        }
}
