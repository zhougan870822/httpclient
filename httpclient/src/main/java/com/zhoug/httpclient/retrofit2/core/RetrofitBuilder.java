package com.zhoug.httpclient.retrofit2.core;



import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 创建工厂
 * @Author HK-LJJ
 * @Date 2019/12/6
 * @Description
 */
public class RetrofitBuilder {
    private static final String TAG = ">>>RetrofitBuilder";

    /**
     * 超时时间 单位秒
     */
    private int connectTimeOut = 10;
    private int writeTimeOut = 60;
    private int readTimeOut = 60;
    /**
     * 断网重连
     */
    private boolean retry = false;

    /**
     * 请求重定向
     */
    private boolean followRedirects = true;
    /**
     * 是否开启debug
     */
    private boolean debug = false;

    /**
     * debug模式下的日志级别
     */
    private HttpLoggingInterceptor.Level debugLevel = HttpLoggingInterceptor.Level.BODY;

    private final List<Interceptor> interceptors = new ArrayList<>();
    private final List<Interceptor> networkInterceptors = new ArrayList<>();


    /**
     * 创建OkHttpClient
     *
     * @return
     */
    private OkHttpClient buildOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
                .retryOnConnectionFailure(retry)
                .followRedirects(followRedirects);

        //添加拦截器
        for (Interceptor interceptor : interceptors) {
            if (interceptor != null) {
                builder.addInterceptor(interceptor);
            }
        }
        //添加网络拦截器
        for (Interceptor interceptor : networkInterceptors) {
            if (interceptor != null) {
                builder.addNetworkInterceptor(interceptor);
            }
        }

        //添加日志拦截器
        if (debug && debugLevel != null) {
            HttpLoggingInterceptor httpLogging = new HttpLoggingInterceptor();
            httpLogging.setLevel(debugLevel);
            builder.addInterceptor(httpLogging);
        }

        OkHttpClient okHttpClient = builder.build();
        Log.d(TAG, "OkHttpClient初始化完成");
        return okHttpClient;

    }

    /**
     * 创建Retrofit
     * @param baseUrl
     * @return
     */
    public Retrofit build(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(buildOkHttpClient())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//这个是用来决定你的返回值是Observable还是Call
                .addConverterFactory(GsonConverterFactory.create())//json字符串转化为对象
                .build();
        Log.d(TAG, "retrofit2初始化完成");

        return retrofit;
    }


    public RetrofitBuilder setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
        return this;
    }

    public RetrofitBuilder setWriteTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public RetrofitBuilder setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public RetrofitBuilder setRetry(boolean retry) {
        this.retry = retry;
        return this;
    }

    public RetrofitBuilder setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }

    public RetrofitBuilder setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public RetrofitBuilder setDebugLevel(HttpLoggingInterceptor.Level debugLevel) {
        this.debugLevel = debugLevel;
        return this;
    }

    public RetrofitBuilder addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
        return this;
    }

    public RetrofitBuilder addNetworkInterceptor(Interceptor interceptor) {
        networkInterceptors.add(interceptor);
        return this;
    }
}
