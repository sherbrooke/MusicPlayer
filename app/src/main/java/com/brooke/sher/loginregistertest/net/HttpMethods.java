package com.brooke.sher.loginregistertest.net;

import com.sher.android2.net.BasehttpMethods;

/**
 * Created by Sher on 2017/8/20.
 */

public class HttpMethods extends BasehttpMethods {

    private HttpMethods() {
        super();
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }



}
