package com.example.chenqiuyang.younginterview.packing.network.download;

import android.os.AsyncTask;

import com.example.chenqiuyang.younginterview.packing.network.RestCreator;
import com.example.chenqiuyang.younginterview.packing.network.callback.IError;
import com.example.chenqiuyang.younginterview.packing.network.callback.IFailure;
import com.example.chenqiuyang.younginterview.packing.network.callback.IRequest;
import com.example.chenqiuyang.younginterview.packing.network.callback.ISuccess;


import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by young on 18-3-22.
 */

public class DownloadHandler  {
    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    //文件下载
    private final String DOWNLAOD_DIR;
    private final String EXTEMSOIN;

    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;


    public DownloadHandler(String url, IRequest request, 
                           String downloadDir, String extension,
                           ISuccess success, IFailure failure, IError error) {
        this.URL = url;
        this.REQUEST = request;
        this.DOWNLAOD_DIR = downloadDir;
        this.EXTEMSOIN = extension;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
    }


    public final void handleDownload(){
        if (REQUEST!=null){
            REQUEST.onRequestStart();
        }

        RestCreator.getRestService().download(URL)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            final ResponseBody responseBody = response.body();
                            final SaveFailTask task = new SaveFailTask(REQUEST,SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                    DOWNLAOD_DIR,EXTEMSOIN,responseBody);

                            //这里一定要注意判断，否则文件下载不全
                            if (task.isCancelled()){
                                if (REQUEST != null){
                                    REQUEST.onRequestEnd();
                                }
                            }
                        }else {
                            if(ERROR != null){
                                ERROR.onError(response.code(),response.message());
                            }
                        }
                        RestCreator.getParams().clear();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if(FAILURE != null){
                            FAILURE.onFailure();
                            RestCreator.getParams().clear();
                        }
                    }
                });
    }



}
