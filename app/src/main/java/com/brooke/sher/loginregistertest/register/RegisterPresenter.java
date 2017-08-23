package com.brooke.sher.loginregistertest.register;

/**
 * Created by Sher on 2017/8/20.
 */

public class RegisterPresenter implements RegisterContact.Presenter {

    private RegisterContact.View registerView;

    public RegisterPresenter(RegisterContact.View registerView){
        this.registerView = registerView;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void getCode() {

        registerView.goNextPage();
    }
}
