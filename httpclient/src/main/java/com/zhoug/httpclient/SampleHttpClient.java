package com.zhoug.httpclient;


import android.arch.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.zhoug.httpclient.retrofit2.core.BaseResponse;
import com.zhoug.httpclient.retrofit2.core.DefaultRequests;
import com.zhoug.httpclient.retrofit2.core.Optional;
import com.zhoug.httpclient.retrofit2.core.ResponseTransformer;
import com.zhoug.httpclient.retrofit2.core.RetrofitBuilder;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * 网络请求客服端例子
 * @Author HK-LJJ
 * @Date 2019/12/23
 * @Description
 */
class SampleHttpClient {
    private static final String TAG = ">>>SampleHttpClient";
    private Retrofit retrofit;
    //服务器请求接口
    private DefaultRequests requestServer;

    @SuppressWarnings("unchecked")
    protected SampleHttpClient() {
        retrofit = new RetrofitBuilder()
                .setDebug(true)
                .build("http://192.168.0.1:8080:api/");

        requestServer = retrofit.create(DefaultRequests.class);
    }


    /**
     * 服务器请求接口
     *
     * @return
     */
    public DefaultRequests getRequestServer() {
        return requestServer;
    }

    /**
     * 重置BaseUrl
     */
    public void resetBaseUrl() {
        retrofit = null;
        requestServer = null;
    }

    public static <T> ObservableSubscribeProxy<Optional<T>> handler(Observable<BaseResponse<T>> observable, LifecycleOwner lifecycleOwner) {
        return observable.compose(SchedulerUtils.apply())
                .compose(ResponseTransformer.handleResult())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)));

    }


    public static <T> Observable<Optional<T>> handler(Observable<BaseResponse<T>> observable) {
        return observable.compose(SchedulerUtils.apply())
                .compose(ResponseTransformer.handleResult());

    }

}
