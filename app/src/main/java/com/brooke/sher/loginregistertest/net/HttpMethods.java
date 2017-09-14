package com.brooke.sher.loginregistertest.net;

import com.brooke.sher.loginregistertest.data.UserInfo;
import com.brooke.sher.loginregistertest.net.retrofit.TestLogin;
import com.sher.android2.entity.BaseHttpResult;
import com.sher.android2.net.BasehttpMethods;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sher on 2017/8/20.
 */

public class HttpMethods extends BasehttpMethods {

    private TestLogin mTestLogin;

    private HttpMethods() {
        super();
        mTestLogin = retrofit.create(TestLogin.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void register(Observer<BaseHttpResult> observer, String username, String passwd, String phone){
        mTestLogin.doRegister(passwd,phone).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
//    public void register(Observer<BaseObject> observer, String username, String passwd, String phone){
//        mTestLogin.register(username,passwd,phone).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//    }


    public Observable<UserInfo> login( String passwd, String phone){
     return    mTestLogin.login(passwd,phone);
    }

}
