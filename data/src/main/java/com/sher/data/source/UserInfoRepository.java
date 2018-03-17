package com.sher.data.source;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sher.data.UserInfo;

import io.reactivex.Observable;

/**
 * Created by Sher on 2017/8/28.
 */

public class UserInfoRepository implements UserDataSource{

    @Nullable
    private static UserInfoRepository INSTANCE = null;

    private UserDataSource mRemoteUserDataSource;
    private UserDataSource mLocalUserDataSource;

    boolean mCacheIsDirty = false;

    private UserInfoRepository(UserDataSource mRemoteUserDataSource,UserDataSource mLocalUserDataSource){
        this.mRemoteUserDataSource = mRemoteUserDataSource;
        this.mLocalUserDataSource = mLocalUserDataSource;
    }

    public static UserInfoRepository getInstance(@NonNull UserDataSource mRemoteUserDataSource,
                                              @NonNull UserDataSource mLocalUserDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new UserInfoRepository(mRemoteUserDataSource, mLocalUserDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public Observable<UserInfo> login(String phone, String passwd) {
        if (!mCacheIsDirty){
           return mLocalUserDataSource.login(phone, passwd);
        }else{
            return mRemoteUserDataSource.login(phone,passwd);
            //本地保存一份信息
        }
    }
}
