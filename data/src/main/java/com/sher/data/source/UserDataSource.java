package com.sher.data.source;


import com.sher.data.UserInfo;

import io.reactivex.Observable;

/**
 * Created by Sher on 2017/8/20.
 */

public interface UserDataSource {

    Observable<UserInfo> login(String phone, String passwd);
}
