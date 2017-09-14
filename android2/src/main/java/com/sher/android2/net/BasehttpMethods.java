package com.sher.android2.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sher on 2017/8/20.
 */

public abstract class BasehttpMethods {

//    public static final String BASE_URL = "http://192.168.0.108:8080/learnTest/" ;
    public static final String BASE_URL = "http://192.168.0.111:8080/LoginRegisterTest/" ;

    private static final int DEFAULT_TIMEOUT = 30;

    protected Retrofit retrofit;
    protected BasehttpMethods(){
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
//    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
//
//        @Override
//        public T call(HttpResult<T> httpResult) {
//            if (httpResult.getResultCode() != 0) {
//                try {
//                    throw AppException.server(httpResult.getResultCode());
//                } catch (AppException e) {
//                    e.printStackTrace();
//                }
//            }
//            return httpResult.getData();
//        }
//    }
}
