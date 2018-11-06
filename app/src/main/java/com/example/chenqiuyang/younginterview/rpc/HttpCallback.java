package com.skyplan.moment.libcore.rpc;

import android.support.annotation.WorkerThread;

/**
 * Created by lihaibiao on 2018/5/10 16:18
 * E-Mail Address：lihaibiaowork@gmail.com
 */
public abstract class HttpCallback<T> {
    public int errType;
    public int errCode;
    public String message;

    void call(int errType,int errCode,String message,T data){
        this.errType = errType;
        this.errCode = errCode;
        this.message = message;
        this.onHttpCallback(isSuccess(),data);
    }

    public boolean isSuccess(){
        return errType == RpcDef.ERR_TYPE_OK && errCode == RpcDef.CODE_OK;
    }

    protected abstract void onHttpCallback(boolean success,T resp);
}
