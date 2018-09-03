package com.example.chenqiuyang.younginterview.packing.network.rx;

import android.content.Context;

import com.example.chenqiuyang.younginterview.packing.network.HttpMethod;
import com.example.chenqiuyang.younginterview.packing.network.RestCreator;
import com.example.chenqiuyang.younginterview.packing.ui.LoaderStyle;


import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by young on 18-3-21.
 */

public class RxRestClient {
    private final String URL;
    private final Map<String,Object> PARAMS;
    private final RequestBody BODY;

    //文件上传
    private final File FILE;

    //文件下载
    private final String DOWNLAOD_DIR;
    private final String EXTEMSOIN;

    private final Context CONTEXT;
    private final LoaderStyle LOADING_STYLE;

    public RxRestClient(String url, Map<String, Object> params,
                        RequestBody body, File file,
                        String download_dir, String extension,
                        Context context, LoaderStyle loaderStyle) {
        this.URL = url;
        this.PARAMS = params;
        this.BODY = body;

        //下载
        DOWNLAOD_DIR = download_dir;
        EXTEMSOIN = extension;

        //上传
        FILE = file;

        //loading
        CONTEXT = context;
        LOADING_STYLE = loaderStyle;
    }

    public static RxRestClientBuilder builder(){
        return new RxRestClientBuilder();
    }


    //网络请求分类
    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;


        if(LOADING_STYLE != null){
//            YoungLoader.showLoading(CONTEXT,LOADING_STYLE);
        }

        switch (method) {
            case GET:
                observable = service.get(URL,PARAMS);
                break;
            case POST:
                observable = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL,BODY);
                break;
            case PUT:
                observable = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL,BODY);
                break;
            case DELETE:
                observable = service.delete(URL,PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody
                        = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body
                        = MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                observable = RestCreator.getRxRestService().upload(URL,body);
                break;
            default:
                break;
        }

        return observable;

    }


    //开始网络请求
    public final Observable<String> get(){
        return request(HttpMethod.GET);
    }

    public final Observable<String> post(){
        if(BODY == null){
            //根据Params请求
            return   request(HttpMethod.POST);
        }else {
            //根据Body请求
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null!!");
            }
            return   request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> put(){
        if(BODY == null){
            //根据Params请求
            return    request(HttpMethod.PUT);
        }else {
            //根据Body请求
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null!!");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete(){
        return request(HttpMethod.DELETE);
    }

    //文件下载
    public final Observable<ResponseBody> downLoad(){
        return RestCreator.getRxRestService().download(URL,PARAMS);
    }


}
