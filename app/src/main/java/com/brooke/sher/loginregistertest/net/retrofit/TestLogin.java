package com.brooke.sher.loginregistertest.net.retrofit;

import com.brooke.sher.loginregistertest.data.BaseObject;
import com.brooke.sher.loginregistertest.data.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Sher on 2017/8/20.
 */

public interface TestLogin {

    @FormUrlEncoded
    @POST("user/addUserByJson")
    Observable<BaseObject> register(@Field("userName") String username, @Field("password")String password, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("user/loginUser")
    Observable<UserInfo> login(@Field("password")String password, @Field("phone") String phone);

}
