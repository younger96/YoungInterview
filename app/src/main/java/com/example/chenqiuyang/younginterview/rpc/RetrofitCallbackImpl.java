//package com.example.chenqiuyang.younginterview.rpc;
//
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.text.TextUtils;
//
//
//import com.example.chenqiuyang.younginterview.multiThread.thread_pool.ThreadPoolManager;
//
//import java.lang.reflect.Method;
//
//
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
///**
// * Created by lihaibiao on 2018/6/2 14:29
// * E-Mail Address：lihaibiaowork@gmail.com
// */
//public abstract class RetrofitCallbackImpl<T> implements Callback<ResponseBody>{
//    private static final String TAG = "sendHttpRequest";
//    private final Class<T> respClass;
//    private final HttpModule.HttpReqInfo reqInfo;
//    private final HttpModule.HttpEncipher mHttpEncipher;
//
//    public RetrofitCallbackImpl(HttpModule.HttpReqInfo reqInfo, Class<T> respClass, HttpModule.HttpEncipher encipher) {
//        this.reqInfo = reqInfo;
//        this.respClass = respClass;
//        this.mHttpEncipher = encipher;
//    }
//
//    @Override
//    public final void onResponse(final Call<ResponseBody> call, final Response<ResponseBody> response) {
//        ThreadPoolManager.shorter().execute(new Runnable() {
//            @Override
//            public void run() {
//                handleResponse(call,response);
//            }
//        });
//    }
//
//    @Override
//    public final void onFailure(Call<ResponseBody> call, final Throwable t) {
//        try {
//            SLog.e(TAG, reqInfo.reqTag() + " onFailure url:" + call.request().url().toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ThreadPoolManager.shorter().execute(new Runnable() {
//            @Override
//            public void run() {
//                HttpModule.log(reqInfo,"onFailure msg = " + t.getMessage());
//                notifyResult(RpcDef.ERR_TYPE_LOCAL,RpcDef.CODE_OK,t.getMessage(),null);
//                NetworkUtils.startNetDetect(0, "onFailure:" + t.getMessage());
//                AppToolUtils.onHandleException(t);
//                if (!TextUtils.isEmpty(t.getMessage()) && t.getMessage().contains("reset")) {
//                    HttpModule.setForceHttpsFlag();
//                }
//            }
//        });
//    }
//
//    private void handleResponse(Call<ResponseBody> call, final Response<ResponseBody> response){
//        final int httpSeq = reqInfo.httpSeq;
//        final int httpCode = response.code();
//        final String httpMsg = response.message();
//        int rpcCode = RpcDef.CODE_OK;
//        if(reqInfo.encrypt){
//            try{
//                rpcCode = Integer.parseInt(response.headers().get("X-PHXRPC-Result"));
//            }catch (Exception e){}
//        }
//        HttpModule.log(reqInfo,String.format("handleResponse httpCode = %s,rpcCode = %s,httpMsg = %s",httpCode,rpcCode,httpMsg));
//        if(httpCode == 200 && rpcCode == RpcDef.CODE_SESSION_EXPIRE){
//            //session过期
//            RpcManager.getInstance().setAuthSession(null);
//            final boolean canLoginAuth = CoreProxy.getService(IAccountMgr.class).canLoginAuth();
//            HttpModule.log(reqInfo,"handleResponse session expire,canLoginAuth = " + canLoginAuth);
//            if(!canLoginAuth){
//                //不能auth登录，发事件跳转到登录界面
//                EvDispatcher.dispatch(NormalEvent.TYPE_SESSION_EXPIRE);
//                SkyplanStat.addEventProperty(StatConstants.Prop.SOURCE, StatConstants.Source.LOGIN_CANNOT_AUTH_LOGIN, StatConstants.Event.APP_LOGIN);
//            }else{
//                //去自动登录
//                CoreProxy.getService(IAccountMgr.class).loginAuth("RetrofitCallbackImpl-session expire ",new MgrCallback<Void>(null) {
//                    @Override
//                    protected void onCallback(@NonNull RpcResponse<Void> resp) {
//                        if(resp.success()){
//                            //auth成功，去执行刚才的请求
//                            RpcManager.sendHttpRequest(reqInfo.req,reqInfo.encrypt,reqInfo.httpSeq,reqInfo.callback);
//                        }else{
//                            if(resp.errType == RpcDef.ERR_TYPE_SVR && resp.errCode == RpcDef.CODE_SESSION_EXPIRE){
//                                //auth登录仍然过期
//                                EvDispatcher.dispatch(NormalEvent.TYPE_SESSION_EXPIRE);
//                                SkyplanStat.addEventProperty(StatConstants.Prop.SOURCE, StatConstants.Source.LOGIN_AUTHFAIL, StatConstants.Event.APP_LOGIN);
//                            }else{
//                                //其他状态错误，不处理
//                            }
//                        }
//                    }
//                });
//            }
//            return;//不再往下执行
//        }
//        if(httpCode == 200){
//            T resp = null;
//            int errCode = RpcDef.CODE_PARSE_ERR;
//            String errMsg = "parse resp exception";
//            try {
//                byte[] buff = response.body().bytes();
//                resp = parseResponseBody(buff);
//                if(resp != null){
//                    Method getBaseRespMethod = ProtoBufInvoke.getMethod(respClass, "getBaseResp");//clazzResp.getMethod("getBaseResp");
//                    Spcgicomm.BaseResp baseResp = (Spcgicomm.BaseResp) getBaseRespMethod.invoke(resp);
//                    if(baseResp != null){
//                        errCode = baseResp.getErrCode();
//                        errMsg = baseResp.getErrMsg();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                SLog.e(TAG, "handleResponse Exception,reqTag = " + reqInfo.reqTag(), e);
//            }
//            HttpModule.log(reqInfo,String.format("handleResponse parse complete, httpCode = %s,errCode = %s,errMsg = %s",httpCode,errCode,errMsg));
//            if(errCode == RpcDef.CODE_PARSE_ERR){
//                notifyResult(RpcDef.ERR_TYPE_LOCAL,errCode,errMsg,null);
//            }else if(errCode == RpcDef.CODE_OK){
//                notifyResult(RpcDef.ERR_TYPE_OK,errCode,errMsg,resp);
//            }else{
//                notifyResult(RpcDef.ERR_TYPE_SVR,errCode,errMsg,resp);
//            }
//        }else{
//            //如果httpCode不等于200，则认为是errType是Local
//            notifyResult(RpcDef.ERR_TYPE_LOCAL,httpCode,httpMsg,null);
//            NetworkUtils.startNetDetect(httpCode, httpMsg);
//        }
//    }
//
//    private void notifyResult(final int errType, final int errCode, final String msg,final T resp){
//        onResult(errType,errCode,msg,resp);
//    }
//
//    protected abstract void onResult(int errType,int errCode,String msg,@Nullable T resp);
//
//    @SuppressWarnings("unchecked")
//    private T parseResponseBody(byte[] bytes) throws Exception{
//        final int length = (bytes == null ? -1 : bytes.length);
//        SLog.d(TAG,"parseResponseBody bytes.length = " + length);
//        if(bytes == null || bytes.length == 0){
//            return null;
//        }
//        byte[] respBytes = bytes;
//        if(reqInfo.encrypt){
//            CgiComm.PKG pkg = CgiComm.PKG.parseFrom(bytes);
//            CgiComm.HEADER header = CgiComm.HEADER.parseFrom(pkg.getHeader());
//            byte[] decryptBytes = mHttpEncipher.decrypt(header.getSequence(),pkg.getBody().toByteArray(),pkg.getBodyTag().toByteArray());
//            respBytes = Snappy.uncompress(decryptBytes);
//        }
//        Method parseFromMethod = ProtoBufInvoke.getMethod(respClass, "parseFrom", byte[].class);
//        if(parseFromMethod != null){
//            return (T) parseFromMethod.invoke(null, respBytes);
//        }
//        SLog.e(TAG, "parseResponseBody parseFromMethod is null,reqInfo.encrypt = " + reqInfo.encrypt);
//        return null;
//    }
//}
