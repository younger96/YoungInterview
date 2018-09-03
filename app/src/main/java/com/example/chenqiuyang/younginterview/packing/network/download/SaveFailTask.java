package com.example.chenqiuyang.younginterview.packing.network.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.chenqiuyang.younginterview.packing.app.App;
import com.example.chenqiuyang.younginterview.packing.network.callback.IRequest;
import com.example.chenqiuyang.younginterview.packing.network.callback.ISuccess;
import com.example.chenqiuyang.younginterview.packing.util.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by young on 18-3-22.
 */

public class SaveFailTask extends AsyncTask<Object,Void,File> {
    private static final String TAG = "SaveFailTask";
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFailTask(IRequest iRequest, ISuccess iSuccess) {
        this.REQUEST = iRequest;
        this.SUCCESS = iSuccess;
    }



    //异步将下载好的文件写入磁盘的操作
    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final InputStream inputStream = body.byteStream();
        if(downloadDir == null || downloadDir.equals("")){
            downloadDir = "down_loads";//如果没有设定路径的话就设定默认路径
        }
        if(extension == null || extension.equals("")){
            extension = "apk";//如果没有设定后缀的话就有默认后缀,后面根据文件类型再填充
        }
        return FileUtil.writeToDisk(inputStream,downloadDir,extension.toUpperCase(),extension);
    }


    //回到主线程操作 , 回调
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);

        if(SUCCESS !=null){
            SUCCESS.onSuccess(file.getPath());
        }

        if(REQUEST != null){
            REQUEST.onRequestEnd();
        }


        //   autoInstallAPK(file);
        Log.i(TAG, "onPostExecute: "+file.getName());
        autoInstallApk(file);
    }


    // private void autoInstallAPK(File){} 回到主线程后可以回调的操作,比如打开我们下载的Apk文件来自动安装
    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            Log.i(TAG, "autoInstallApk: "+file.getName());
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            App.getApplicationInstance().startActivity(install);
        }
        Log.i(TAG, "autoInstallApk: "+file.getAbsolutePath());
    }
}
