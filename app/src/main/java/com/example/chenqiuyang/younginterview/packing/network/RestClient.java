package com.example.chenqiuyang.younginterview.packing.network;

import android.content.Context;


import com.example.chenqiuyang.younginterview.packing.network.callback.IError;
import com.example.chenqiuyang.younginterview.packing.network.callback.IFailure;
import com.example.chenqiuyang.younginterview.packing.network.callback.IRequest;
import com.example.chenqiuyang.younginterview.packing.network.callback.ISuccess;
import com.example.chenqiuyang.younginterview.packing.network.callback.RequestCallBacks;
import com.example.chenqiuyang.younginterview.packing.network.download.DownloadHandler;
import com.example.chenqiuyang.younginterview.packing.ui.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by young on 18-3-21.
 */

public class RestClient {
    private final String URL;
    private final Map<String,Object> PARAMS;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;

    //文件上传
    private final File FILE;

    //文件下载
    private final String DOWNLAOD_DIR;
    private final String EXTEMSOIN;

    private final Context CONTEXT;
    private final LoaderStyle LOADING_STYLE;

    public RestClient(String url, Map<String, Object> params,
                      IRequest request, ISuccess success, IFailure failure,
                      IError error, RequestBody body, File file,
                      String download_dir, String extension,
                      Context context, LoaderStyle loaderStyle) {
        this.URL = url;
        this.PARAMS = params;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
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

    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }


    //网络请求分类
    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if(LOADING_STYLE != null){
//            YoungLoader.showLoading(CONTEXT,LOADING_STYLE);
        }

        switch (method) {
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL,BODY);
                break;
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL,BODY);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody
                        = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body
                        = MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call = RestCreator.getRestService().upload(URL,body);
                break;
            default:
                break;
        }

        if (call != null){
            call.enqueue(getRequestCallback());
        }
    }


    //回调
    private Callback<String> getRequestCallback(){
        return new RequestCallBacks(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR,
                LOADING_STYLE
        );
    }


    //开始网络请求
    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        if(BODY == null){
            //根据Params请求
            request(HttpMethod.POST);
        }else {
            //根据Body请求
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null!!");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put(){
        if(BODY == null){
            //根据Params请求
            request(HttpMethod.PUT);
        }else {
            //根据Body请求
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null!!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }

    //文件下载
    public final void downLoad(){
        new DownloadHandler(URL,REQUEST,DOWNLAOD_DIR,
                EXTEMSOIN,SUCCESS,FAILURE,ERROR)
                .handleDownload();

    }


}
