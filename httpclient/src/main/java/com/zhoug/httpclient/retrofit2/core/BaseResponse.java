package com.zhoug.httpclient.retrofit2.core;


import android.support.annotation.Keep;

import com.zhoug.httpclient.Utils;

import java.io.Serializable;

/**
 * 描述：服务器返回的数据
 * {'code':1,'msg':'请求成功了xxx','data':{.......}}
 * zhougan
 * 2019/3/23
 **/
@Keep
public class BaseResponse<T> implements Serializable {
    private int state;//返回的code 200成功
    private String message;//可用来返回接口的说明
    private T data;//具体数据
    private String token;//具体

    public transient static final int STATUS_SUCCESS=200;
    public transient static final int STATUS_COOKIE_TIME_OUT=444;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return Utils.toJson(this);
    }


}
