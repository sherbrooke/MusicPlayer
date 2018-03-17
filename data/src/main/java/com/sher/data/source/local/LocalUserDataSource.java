package com.sher.data.source.local;


import com.sher.data.UserInfo;
import com.sher.data.source.UserDataSource;

import io.reactivex.Observable;

/**
 * Created by Sher on 2017/8/28.
 */

public class LocalUserDataSource implements UserDataSource {

    private static LocalUserDataSource INSTANCE;

    private LocalUserDataSource(){

    }

    public static LocalUserDataSource getINSTANCE(){
        if (INSTANCE == null) {
            INSTANCE = new LocalUserDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<UserInfo> login(String phone, String passwd) {

        return null;
    }
}
