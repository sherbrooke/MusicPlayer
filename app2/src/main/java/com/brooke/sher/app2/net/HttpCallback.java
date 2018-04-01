package com.brooke.sher.app2.net;

import retrofit2.Response;

/**
 * Created by Sher on 2018/4/1.
 */

public interface HttpCallback {
    void onComplete(Response response);
    void onError(Throwable t);
}
