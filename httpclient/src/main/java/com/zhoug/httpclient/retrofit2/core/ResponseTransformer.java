package com.zhoug.httpclient.retrofit2.core;




import com.zhoug.httpclient.retrofit2.exception.ApiException;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * 请求结果预处理
 * @Author HK-LJJ
 * @Date 2019/10/24
 * @Description TODO
 */
public class ResponseTransformer {

    public static <T> ObservableTransformer<BaseResponse<T>, Optional<T>> handleResult() {
        return upstream ->
                upstream.onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());

    }


    /**
     * 请求结果预处理,代码不是BaseResponse.STATUS_SUCCESS的返回异常
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<BaseResponse<T>, ObservableSource<Optional<T>>> {


        @Override
        public ObservableSource<Optional<T>> apply(BaseResponse<T> tBaseModel) throws Exception {
            if(tBaseModel!=null){
                if(tBaseModel.getState()==BaseResponse.STATUS_SUCCESS){
                    T data = tBaseModel.getData();
                   return Observable.just(Optional.create(data));
                }else{
                    return Observable.error(new ApiException(tBaseModel ));
                }
            }
            return  Observable.error(new ApiException(null));
        }
    }


    /**
     * 异常处理
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends BaseResponse<T>>> {

        @Override
        public ObservableSource<? extends BaseResponse<T>> apply(Throwable throwable) throws Exception {

            return Observable.error(ApiException.handleException(throwable));

        }
    }



}
