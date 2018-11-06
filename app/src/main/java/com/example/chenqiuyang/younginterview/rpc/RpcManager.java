package com.skyplan.moment.libcore.rpc;

import com.google.protobuf.GeneratedMessageLite;

import spcgi.Spcgicomm;
import spcgi.Splogincgi;

/**
 * Created by lihaibiao on 2018/5/10 14:32
 * E-Mail Address：lihaibiaowork@gmail.com
 */
public class RpcManager {

    private volatile static RpcManager INSTANCE = null;
    private final HttpModule mHttpModule;
    private final Object mSessionLock = new Object();
    private Splogincgi.Session mAuthSession;

    public static RpcManager getInstance() {
        if (INSTANCE == null) {
            synchronized (RpcManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RpcManager();
                }
            }
        }
        return INSTANCE;
    }

    private RpcManager() {
        mHttpModule = new HttpModule();
    }

    public void setAuthSession(Splogincgi.Session session){
        synchronized (mSessionLock){
            mAuthSession = session;
        }
    }

    public Splogincgi.Session getAuthSession(){
        synchronized (mSessionLock){
            return mAuthSession;
        }
    }

    public static <T> void sendHttpRequest(GeneratedMessageLite req, final boolean encrypt,
        final HttpCallback<T> callback) {
        getInstance().mHttpModule.sendHttpRequest(req, encrypt, callback);
    }

    public static <T> void sendHttpRequest(GeneratedMessageLite req, final boolean encrypt,int httpSeq,
        final HttpCallback<T> callback) {
        getInstance().mHttpModule.sendHttpRequest(new HttpModule.HttpReqInfo<T>(req,encrypt,httpSeq,callback));
    }

    public static <T> RpcResponse<T> sendHttpRequestSync(GeneratedMessageLite req, final boolean encrypt,
        final HttpCallback<T> callback) {
        return getInstance().mHttpModule.sendHttpRequestSync(req, encrypt, callback);
    }

    public static Spcgicomm.BaseReq createBaseReq() {
        return getInstance().mHttpModule.createBaseReq();
    }
}
