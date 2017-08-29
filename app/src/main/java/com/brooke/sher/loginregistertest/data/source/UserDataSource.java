package com.brooke.sher.loginregistertest.data.source;

/**
 * Created by Sher on 2017/8/20.
 */

public interface UserDataSource {
    interface UserInfoCallBack{
        void onComplete();

        void onError();
    }

    void login(String phone,String passwd,UserInfoCallBack callBack);
}
