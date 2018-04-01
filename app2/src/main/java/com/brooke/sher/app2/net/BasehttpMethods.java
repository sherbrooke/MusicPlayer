package com.brooke.sher.app2.net;

import com.brooke.sher.app2.util.LogUtils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sher on 2017/8/20.
 */

public abstract class BasehttpMethods {

    private static final String TAG = "BasehttpMethods";
    private static final boolean IS_DEV = false;

    public static final String DEV_BASE_URL = "http://192.168.0.112:8080/" ;
    public static final String PRODUCT_BASE_URL = "http://116.62.143.100:8080/" ;

    protected Retrofit retrofit;

    protected BasehttpMethods(){
        this(true);
    }

    protected BasehttpMethods(boolean useRxJava){

        Retrofit.Builder retrofitBuilder  = new Retrofit.Builder()
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(IS_DEV ? DEV_BASE_URL : PRODUCT_BASE_URL);
        if (useRxJava){
            retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        }
        retrofit = retrofitBuilder.build();
    }

    private OkHttpClient getClient() {
        // log用拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
        if (IS_DEV) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
        // 如果使用到HTTPS，我们需要创建SSLSocketFactory，并设置到client
        SSLSocketFactory sslSocketFactory = null;
        try {
            // 这里直接创建一个不做证书串验证的TrustManager
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {
                        }
                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {
                        }
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
        }

        return new OkHttpClient.Builder()
                // 信任所有主机名
//                .hostnameVerifier((hostname, session) -> true)
                // HeadInterceptor实现了Interceptor，用来往Request Header添加一些业务相关数据，如APP版本，token信息
                .addInterceptor(new HeadInterceptor())
                .addInterceptor(logging)
                // 连接超时时间设置
                .connectTimeout(10, TimeUnit.SECONDS)
                // 读取超时时间设置
                .readTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory)
                // 这里我们使用host name作为cookie保存的key
                .cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(HttpUrl.parse(url.host()), cookies);
                    }
                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(HttpUrl.parse(url.host()));
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
