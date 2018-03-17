package com.brooke.sher.loginregistertest.register.submitaccount;


import android.util.Log;

import com.brooke.sher.app2.entity.BaseHttpResult;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Sher on 2017/8/20.
 */

public class SubmitAccountInfoPresenter implements SubmitAccountInfoContact.Presenter {

    private SubmitAccountInfoContact.View submitAccountInfoView;

    public SubmitAccountInfoPresenter(SubmitAccountInfoContact.View submitAccountInfoView){
        this.submitAccountInfoView = submitAccountInfoView;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }


    @Override
    public void register(String username, String passwd,String passwdAgain, String phone) {
        Observer<BaseHttpResult> observable = new Observer<BaseHttpResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("ssss", "subscribe");
            }

            @Override
            public void onNext(BaseHttpResult value) {
                Log.d("ssss", "" + value.getResultMessage());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.d("ssss", "error");
            }

            @Override
            public void onComplete() {
                submitAccountInfoView.showToast("oncomplete");
                Log.d("ssss", "complete");

            }
        };
//        HttpMethods.getInstance().register(observable,username,passwd,phone);







//        Observer<BaseObject> observable = new Observer<BaseObject>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.d("ssss", "subscribe");
//            }
//
//            @Override
//            public void onNext(BaseObject value) {
//                Log.d("ssss", "" + value.getMessage());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//                Log.d("ssss", "error");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d("ssss", "complete");
//
//            }
//        };
//        HttpMethods.getInstance().register(observable,username,passwd,phone);
    }
}
