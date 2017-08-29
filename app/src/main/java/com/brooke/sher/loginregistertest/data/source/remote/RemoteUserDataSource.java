package com.brooke.sher.loginregistertest.data.source.remote;


import com.brooke.sher.loginregistertest.data.UserInfo;
import com.brooke.sher.loginregistertest.data.source.UserDataSource;
import com.brooke.sher.loginregistertest.net.HttpMethods;

import io.reactivex.Observable;

/**
 * Created by Sher on 2017/8/28.
 */

public class RemoteUserDataSource implements UserDataSource {

    private static RemoteUserDataSource INSTANCE;

    private RemoteUserDataSource(){

    }

    public static RemoteUserDataSource getINSTANCE(){
        if (INSTANCE == null) {
            INSTANCE = new RemoteUserDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<UserInfo> login(String phone, String passwd) {
        return  HttpMethods.getInstance().login(passwd, phone);
    }
}
