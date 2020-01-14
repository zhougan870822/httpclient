package com.zhoug.httpclient;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 描述：rxjava2 线程切换
 *  zhougan
 * 2019/3/23
 **/
public class SchedulerUtils {

    public static Scheduler io() {
        return Schedulers.io();
    }

    public static Scheduler newThread(){
        return Schedulers.newThread();
    }

    public static Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }


    public static <T> ObservableTransformer<T, T> apply() {
        return upstream -> upstream.subscribeOn(io()).observeOn(ui());
    }
}
