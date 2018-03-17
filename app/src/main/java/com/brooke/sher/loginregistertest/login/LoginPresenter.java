package com.brooke.sher.loginregistertest.login;

import android.text.TextUtils;

import com.sher.data.UserInfo;
import com.sher.data.source.UserInfoRepository;
import com.sher.data.source.local.LocalUserDataSource;
import com.sher.data.source.remote.RemoteUserDataSource;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangyang on 2017/8/28.
 */

public class LoginPresenter implements LoginContact.Presenter {

    private LoginContact.View view;
    private UserInfoRepository mUserInfoRepository;

    public LoginPresenter(LoginContact.View view){
        this.view = view;
        mUserInfoRepository = UserInfoRepository.getInstance(RemoteUserDataSource.getINSTANCE(), LocalUserDataSource.getINSTANCE());
    }


    //如果一进入页面就需要数据，通过在view的onresume方法里面调用实现,需要的参数可以通过构造方法传入
    @Override
    public void subscribe() {
//        attempLogin()
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

            Observer<UserInfo> observer = new Observer<UserInfo>() {
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

        mUserInfoRepository.login(phone,passwd)
            .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
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
