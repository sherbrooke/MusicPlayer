package com.sher.data.source.remote;


import com.sher.data.UserInfo;
import com.sher.data.source.UserDataSource;

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
//        return  HttpMethods.getInstance().login(passwd, phone);
        return null;
    }
}
