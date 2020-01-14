package com.zhoug.httpclient.retrofit2.exception;

import com.google.gson.JsonParseException;
import com.zhoug.httpclient.retrofit2.core.BaseResponse;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;


/**
 * 网络请求异常
 * @Author HK-LJJ
 * @Date 2019/12/6
 * @Description
 */
public class ApiException extends RuntimeException {
    private int code;
    private String message;

    private static String getMessage(BaseResponse<?> response) {
        if (response == null) {
            return "BaseResponse is null";
        }
        return "code:" + response.getState() + ",message:" + response.getMessage();
    }


    private ApiException(int code, String message, Throwable throwable) {
        super(throwable != null ? throwable.toString() : "null");
        this.code = code;
        this.message = message;
    }

    /**
     * 请求成功后返回的数据构建异常
     *
     * @param baseResponse
     */
    public ApiException(BaseResponse baseResponse) {
        super(getMessage(baseResponse));
        if (baseResponse == null) {
            message = "BaseResponse is null";
        } else {
            code = baseResponse.getState();
            message = baseResponse.getMessage();
        }
    }

    /**
     * 请求失败异常
     *
     * @param throwable
     * @return
     */
    public static ApiException handleException(Throwable throwable) {
        if (throwable == null) {
            return new ApiException(-1, "未知异常",null);
        }
        String message = throwable.getMessage();
        if (message != null) {
            if (message.contains("HTTP 404 Not Found")) {
                return new ApiException(404, "无法找到资源", throwable);
            } else if (message.contains("HTTP 500 Internal Server Error")) {
                return new ApiException(500, "服务器运行时错误", throwable);
            }
        }

        if (throwable instanceof JsonParseException
                || throwable instanceof ParseException
                || throwable instanceof JSONException) {
            ////解析错误
            return new ApiException(100, "数据解析错误",throwable);
        } else if (throwable instanceof ConnectException) {
            ////网络错误
            return new ApiException(101,"网络连接失败",throwable);
        } else if (throwable instanceof UnknownHostException
                || throwable instanceof SocketException) {
            //连接协议错误
            return new ApiException(102,"连接协议错误",throwable);
        } else if (throwable instanceof java.net.SocketTimeoutException) {
            //连接超时
            return new ApiException(103,"连接超时",throwable);
        } else {
            //未知错误
            return new ApiException(-1,throwable.getMessage(),throwable);
        }

    }

    public static String handlerMessage(Throwable throwable) {
//        throwable.printStackTrace();
        if (throwable instanceof ApiException) {
            return ((ApiException) throwable).getErrorMessage();
        } else {
            return throwable.getMessage();
        }
    }

    public int getCode() {
        return code;
    }


    public String getErrorMessage() {
        return message;
    }

}
