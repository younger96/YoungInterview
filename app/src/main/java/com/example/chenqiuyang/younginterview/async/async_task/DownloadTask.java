package com.example.chenqiuyang.younginterview.async.async_task;//package com.example.updateinfo;

import android.app.Dialog;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * (后台返回值，进度，结果返回值)
 */
class DownloadTask extends AsyncTask<Void, Integer, Boolean> {
    private Dialog progressDialog;

    public DownloadTask(Dialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    //开始前的操作
    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    //耗时操作
    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            while (true) {
                int downloadPercent = doDownload();
                publishProgress(downloadPercent);
                if (downloadPercent >= 100) {
                    break;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //耗时操作
    private int doDownload() {
        return 0;
    }

    //动态显示
    @Override
    protected void onProgressUpdate(Integer... values) {
//        progressDialog.setMessage("当前下载进度：" + values[0] + "%");
    }


    //使用结束后传递的方法
    @Override
    protected void onPostExecute(Boolean result) {
        progressDialog.dismiss();
        if (result) {
//            Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
        }
    }
}