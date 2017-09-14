package com.sher.android2.entity;

/**
 * Created by Sher on 2017/8/20.
 */

public class HttpResult<T> extends BaseHttpResult {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
