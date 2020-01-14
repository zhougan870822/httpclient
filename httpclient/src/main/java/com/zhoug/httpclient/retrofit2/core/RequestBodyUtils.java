package com.zhoug.httpclient.retrofit2.core;

import android.text.TextUtils;
import android.util.Log;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 请求参数工具
 * @Author HK-LJJ
 * @Date 2019/10/24
 * @Description TODO
 */
public class RequestBodyUtils {
    private static final String TAG = ">>>RequestBodyUtils";
    public static final String TYPE_FILE = "application/otcet-stream";
    public static final String TYPE_JSON = "application/json";


    /**
     * @param map 请求参数
     * @return Map<String, RequestBody>
     */
    public static Map<String, RequestBody> partMap(Map<String, String> map) {
        if (map != null && map.size() > 0) {
            Map<String, RequestBody> partMap = new HashMap<>();
            //将String封装成RequestBody类型
            for (Map.Entry<String, String> entry : map.entrySet()) {
                partMap.put(entry.getKey(), RequestBody.create(MediaType.parse(TYPE_JSON), entry.getValue()));
            }
            return partMap;
        }

         return null;
    }

    /**
     *
     * @param map 请求参数
     * @return List<MultipartBody.Part>
     */
    public static List<MultipartBody.Part> partList(Map<String, String> map){
        if (map != null && map.size() > 0) {
            List<MultipartBody.Part> partList=new ArrayList<>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                MultipartBody.Part part=MultipartBody.Part.createFormData(entry.getKey(), entry.getValue() );
                partList.add(part);
            }
            return partList;
        }

        return null;
    }


    /**
     *
     * @param paths 上传的文件路径集合
     * @return List<MultipartBody.Part>
     */
    public static List<MultipartBody.Part> partFileList(List<String> paths){
        if(paths!=null && paths.size()>0){
            List<MultipartBody.Part> partList=new ArrayList<>();
            for(int i=0;i<paths.size();i++){
                String path=paths.get(i);
                if(TextUtils.isEmpty(path)){
                    Log.e(TAG, "paths集合中的第"+i+"个path为空");
                }
                //将文件路径封装成MultipartBody.Part类型
                File file=new File(path);
                RequestBody requestBody = RequestBody.create(MediaType.parse(TYPE_FILE), file);
                MultipartBody.Part part=MultipartBody.Part.createFormData("file"+i, file.getName(),requestBody );
                partList.add(part);
            }
            return partList;
        }

        return null;
    }



}
