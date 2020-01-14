package com.zhoug.httpclient.retrofit2.core;

/**
 * @Author HK-LJJ
 * @Date 2019/11/11
 * @Description TODO
 */
public class Optional<T> {
    private T data;

    public Optional(T data) {
        this.data = data;
    }

    public static <T> Optional<T> create(T data){
        return new Optional<>(data);
    }

    public T get(){
        return data;
    }
}
