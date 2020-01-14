package com.zhoug.httpclient.retrofit2.core;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 常用的默认请求,可以继承他
 * @Author HK-LJJ
 * @Date 2019/11/11
 * @Description TODO
 */
public interface DefaultRequests {
    //支持文件上传的form表单
    @Multipart
    @POST("{url}")
    Observable<BaseResponse<String>> post(@Path("url") String url, @PartMap() Map<String, RequestBody> map, @Part List<MultipartBody.Part> files);

    //不支持文件上传的form表单
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResponse<String>> post(@Path("url") String url, @FieldMap() Map<String, RequestBody> map);

    //get请求
    @GET("{url}")
    Observable<BaseResponse<String>> get(@Path("url") String url, @QueryMap Map<String, Object> params);

    //下载文件
    @GET
    @Streaming
    Call<ResponseBody> download(@Url String url);


}
