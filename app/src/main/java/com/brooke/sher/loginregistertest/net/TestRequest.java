package com.brooke.sher.loginregistertest.net;

import com.brooke.sher.loginregistertest.data.UserInfo;
import com.brooke.sher.loginregistertest.net.retrofit.TestLogin;
import com.google.gson.Gson;
import com.sher.android2.entity.BaseHttpResult;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 2018/3/15.
 */

public class TestRequest {

    private TestLogin mTestLogin;
    private HttpMethods methods;
    private static Gson mGson;

    public TestRequest(){
        methods = HttpMethods.getInstance();
        mTestLogin = methods.getRetrofit().create(TestLogin.class);
        mGson = new Gson();
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


    public Observable<UserInfo> login(String passwd, String phone){
        return    mTestLogin.login(passwd,phone);
    }




//    public static <T> Observable<T> extractData(Observable<HttpResult<T>> observable) {
//        return observable.flatMap(response -> {
//            if (response == null) {
//                return Observable.error(new NetworkConnectionException());
//            } else if (response.getResultCode() == ResponseException.STATUS_CODE_SUCCESS) {
//                return Observable.just(response.getData());
//            } else {
//                LogUtils.e("TestRequest", response.getData().toString());
//                return Observable.error(new ResponseException(response));
//            }
//        });
//    }


}
