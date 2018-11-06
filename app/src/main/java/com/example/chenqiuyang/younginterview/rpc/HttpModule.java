//package com.example.chenqiuyang.younginterview.rpc;
//
//import android.os.Build;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.text.TextUtils;
//
//import com.franmontiel.persistentcookiejar.ClearableCookieJar;
//import com.franmontiel.persistentcookiejar.PersistentCookieJar;
//import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
//import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
//import com.google.protobuf.ByteString;
//import com.google.protobuf.GeneratedMessageLite;
//import com.jiechic.library.android.snappy.Snappy;
//import com.skyplan.moment.libbasic.utils.AppUtils;
//import com.skyplan.moment.libbasic.utils.NetworkUtils;
//import com.skyplan.moment.libbasic.utils.SLog;
//import com.skyplan.moment.libbasic.utils.ThreadPoolManager;
//import com.skyplan.moment.libbasic.utils.TimeUtils;
//import com.skyplan.moment.libcomm.deviceinfo.DeviceInfo;
//import com.skyplan.moment.libcomm.storage.StorageMgr;
//import com.skyplan.moment.libcore.BuildConfig;
//import com.skyplan.moment.libcore.CoreApp;
//import com.skyplan.moment.libcore.CoreProxy;
//import com.skyplan.moment.libcore.service.IAccountMgr;
//import com.skyplan.moment.utils.IDSeqGenerator;
//
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.spec.GCMParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
//import cgicomm.CgiComm;
//import okhttp3.Dispatcher;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.RequestBody;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Retrofit;
//import spcgi.Spcgicomm;
//import spcgi.Spcgicommdef;
//import spcgi.Splogincgi;
//
///**
// * Created by lihaibiao on 2018/6/2 13:23
// * E-Mail Address：lihaibiaowork@gmail.com
// */
//public class HttpModule {
//    private static final String TAG = "HttpModule";
//    public static final long HTTP_TIME_OUT = 15 * 1000;
//    public static final int DEFAULT_RETRY_COUNT = 3;
//    private final SkyHttpRegister.Dispatcher mHttpDispatcher;
//    private final HttpEncipher mHttpEncipher;
//    private static final String HTTP = "http://";
//    private static final String HTTPS = "https://";
//    private static final String DOMAIN = genDomainParam();
//
//    public static final String FLAVOR_MARKET = "market";
//    public static final String FLAVOR_BETA = "beta";
//    public static final String FLAVOR_TEST = "test";
//
//    private static boolean mForceHttps = false;
//
//    /**
//     * buildType == debug
//     *      可以让用户直接选择域名，如果sp那里没有存储选择的值，则用echo-dev.skyplan.online
//     * buildType == release
//     *      flavor == test
//     *          可以让用户直接选择域名，如果sp那里没有存储选择的值，则用echo.skyplan.online
//     *      flavor == beta
//     *          不能选择域名，用echo.skyplan.online，但或者以后可以通过推送触发更改
//     *      flavor == market
//     *          不能选择域名，用echo.skyplan.online，但或者以后可以通过推送触发更改
//     */
//    @NonNull
//    private static String genDomainParam() {
//        if (BuildConfig.DEBUG) {
//            return StorageMgr.getDomainHost();
//        } else {
//            switch (AppUtils.getAppFlavor()) {
//                case FLAVOR_TEST:
//                    return StorageMgr.getDomainHost();
//                case FLAVOR_MARKET:
//                case FLAVOR_BETA:
//                default:
//                    return "echo.skyplan.online";
//            }
//        }
//    }
//
//    public HttpModule() {
//        Dispatcher dispatcher = new Dispatcher();
//        dispatcher.setMaxRequests(128);
//        dispatcher.setMaxRequestsPerHost(64);
//        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(CoreApp.getContext()));
//        OkHttpClient client = new OkHttpClient.Builder()
//            .connectTimeout(HTTP_TIME_OUT, TimeUnit.MILLISECONDS)
//            .readTimeout(HTTP_TIME_OUT, TimeUnit.MILLISECONDS)
//            .writeTimeout(HTTP_TIME_OUT, TimeUnit.MILLISECONDS)
//            .dispatcher(dispatcher)
//            .cookieJar(cookieJar)
//            .build();
//        Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl(HTTPS + DOMAIN + "/")//这个不会起作用，请求的url都是在请求的时候拼接的
//            .client(client)
//            .build();
//        mHttpDispatcher = retrofit.create(SkyHttpRegister.Dispatcher.class);
//        mHttpEncipher = new HttpEncipher();
//    }
//
//    public Spcgicomm.BaseReq createBaseReq() {
//        final long uin = StorageMgr.uin();
//        Spcgicomm.ChannelInfo channelInfo = Spcgicomm.ChannelInfo.newBuilder().setKey(AppUtils.getAppChannel()).build();
//        Spcgicomm.BaseReq baseReq = Spcgicomm.BaseReq.newBuilder()
//            .setUin(uin)
//            .setClientVersion(AppUtils.getAppServerVersion())  //how
//            .setDeviceid(DeviceInfo.getMMGUID())
//            .setDeviceType(Spcgicommdef.enDeviceType.DEVICETYPE_ANDRIOD_VALUE) //how
//            .setChannel(channelInfo)
//            .setNetwork(
//                NetworkUtils.getNetworkTypeDesc() + "-" + NetworkUtils.getNetworkOperatorName())
//            .setTimestamp((int) (TimeUtils.getSystemTimeStampCalibrateByServer() / 1000))
//            .setPin("@%*SHRB984957s0d")
//            .setDeviceModel(Build.BRAND + " # " + Build.MODEL)
//            .build();
//        return baseReq;
//    }
//
//    /**
//     * @param encrypt 是否需要加密，login和auth不需要加密
//     * @return RpcResponse
//     */
//    @NonNull
//    public <T> RpcResponse<T> sendHttpRequestSync(final GeneratedMessageLite req, final boolean encrypt,
//        @Nullable final HttpCallback<T> callback) {
//        final CountDownLatch downLatch = new CountDownLatch(1);
//        final RpcResponse<T>[] result = new RpcResponse[]{RpcResponse.<T>errLocal()};
//        sendHttpRequest(req, encrypt, new HttpCallback<T>() {
//            @Override
//            protected void onHttpCallback(boolean success, T resp) {
//                if (callback != null) callback.call(errType, errCode, message, resp);
//                result[0] = success ? RpcResponse.ok(resp) : RpcResponse.<T>with(errType,errCode,null);
//                downLatch.countDown();
//            }
//        });
//        try {
//            downLatch.await(HTTP_TIME_OUT, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return result[0];
//    }
//
//    public <T> void sendHttpRequest(final GeneratedMessageLite req, final boolean encrypt,
//        final HttpCallback<T> callback) {
//        sendHttpRequest(new HttpReqInfo<>(req, encrypt, callback));
//    }
//
//    static void log(HttpReqInfo reqInfo,String msg){
//        String log = "sendHttpRequest " + reqInfo.reqTag();
//        if(!TextUtils.isEmpty(msg)){
//            log = log + "," + msg;
//        }
//        SLog.i(TAG,log);
//    }
//
//    public static void resetForceHttpsFlag() {
//        mForceHttps = false;
//    }
//
//    public static void setForceHttpsFlag() {
//        mForceHttps = true;
//    }
//
//    public <T> void sendHttpRequest(final HttpReqInfo<T> reqInfo) {
//        final GeneratedMessageLite req = reqInfo.req;
//        String requestPath = SkyHttpRegister.getReqUrl(req.getClass());
//        final Class<T> respClazz = (Class<T>) SkyHttpRegister.getRespClazz(req.getClass());
//        log(reqInfo,"");
//        if (respClazz == null) {
//            throw new IllegalArgumentException(TAG
//                + " sendHttpRequest respClazz is null,is register ? reqClazz = "
//                + req.getClass());
//        }
//        if (reqInfo.encrypt && !mForceHttps) {
//            requestPath = HTTP + DOMAIN + requestPath;
//        } else {
//            requestPath = HTTPS + DOMAIN + requestPath;
//        }
//        final String finalRequestPath = requestPath;
//        ThreadPoolManager.shorter().execute(new Runnable() {
//            @Override
//            public void run() {
//                final boolean isNetConnected = NetworkUtils.isConnected();
//                if(!isNetConnected){
//                    log(reqInfo,"isNetConnected = false");
//                    reqInfo.callback.call(RpcDef.ERR_TYPE_LOCAL,RpcDef.CODE_NETWORK_UNAVAILABLE,"network not connected",null);
//                    return;
//                }
//                final boolean isLogin = CoreProxy.getService(IAccountMgr.class).isLogin();
//                if(reqInfo.encrypt && !isLogin){
//                    log(reqInfo,"reqInfo.encrypt = true,but current not login");
//                    reqInfo.callback.call(RpcDef.ERR_TYPE_LOCAL,RpcDef.CODE_NOT_LOGIN,"not login",null);
//                    return;
//                }
//                try {
//                    byte[] reqBytes = reqToBytes(reqInfo);
//                    log(reqInfo,"reqBytes.length = " + reqBytes.length);
//                    RequestBody body =
//                        RequestBody.create(MediaType.parse("application/octet-stream"), reqBytes);
//                    Call<ResponseBody> dispatchCall =
//                        mHttpDispatcher.dispatch(finalRequestPath, body, StorageMgr.uin() > 0 ? String.valueOf(StorageMgr.uin()) : "");
//                    dispatchCall.enqueue(
//                        new RetrofitCallbackImpl<T>(reqInfo, respClazz, mHttpEncipher) {
//                            @Override
//                            protected void onResult(int errType, int errCode, String msg,
//                                @Nullable T resp) {
//                                log(reqInfo,String.format("onResult errType = %s,errCode = %s",errType,errCode));
//                                if ((errType == RpcDef.ERR_TYPE_SVR
//                                    && errCode == RpcDef.CODE_ERR_SYS)//服务端返回特定状态码，可重试
//                                    || (errType == RpcDef.ERR_TYPE_LOCAL
//                                    && errCode != RpcDef.CODE_PARSE_ERR)//本地环境错误，可重试
//                                    ) {
//                                    if (reqInfo.currRetry >= reqInfo.maxRetry) {
//                                        //重试超限
//                                        reqInfo.callback.call(errType, errCode, msg, resp);
//                                    } else {
//                                        HttpReqInfo retryInfo = reqInfo.retryCopy();
//                                        log(retryInfo,"do retry");
//                                        sendHttpRequest(retryInfo);
//                                    }
//                                } else {
//                                    reqInfo.callback.call(errType, errCode, msg, resp);
//                                }
//                            }
//                        });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    SLog.e(TAG, "sendHttpRequest reqToBytes Exception,reqTag = " + reqInfo.reqTag(), e);
//                    reqInfo.callback.call(RpcDef.ERR_TYPE_LOCAL, RpcDef.CODE_PARSE_ERR,
//                        "reqToBytes Exception", null);
//                }
//            }
//        });
//    }
//
//    private byte[] reqToBytes(HttpReqInfo info)
//        throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException,
//        InvalidKeyException, IOException {
//        if (!info.encrypt) {
//            return info.req.toByteArray();
//        } else {
//            Splogincgi.Session session = RpcManager.getInstance().getAuthSession();
//            if (session == null) {
//                throw new IllegalArgumentException("reqToBytes session is null");
//            }
//            byte[] compressBytes = Snappy.compress(info.req.toByteArray());
//            byte[] cipherBody = mHttpEncipher.encrypt(info.httpSeq, compressBytes,session);
//            CgiComm.HEADER header = CgiComm.HEADER.newBuilder()
//                .setUin(StorageMgr.uin())
//                .setSequence(info.httpSeq)
//                .setVersion(1)
//                .build();
//            final int bodyLength = cipherBody.length - HttpEncipher.AES_AAD_LENGTH;
//            CgiComm.PKG pkg = CgiComm.PKG.newBuilder()
//                .setHeader(header.toByteString())
//                .setBody(ByteString.copyFrom(cipherBody, 0, bodyLength))
//                .setBodyTag(
//                    ByteString.copyFrom(cipherBody, bodyLength, HttpEncipher.AES_AAD_LENGTH))
//                .build();
//            return pkg.toByteArray();
//        }
//    }
//
//    static class HttpReqInfo<T> {
//        public final GeneratedMessageLite req;
//        public final boolean encrypt;
//        public final HttpCallback<T> callback;
//        public final int httpSeq;
//        public int maxRetry = DEFAULT_RETRY_COUNT;
//        public int currRetry;
//        //public long delayed;
//
//        HttpReqInfo(GeneratedMessageLite req, boolean encrypt, HttpCallback<T> callback) {
//            this(req,encrypt,IDSeqGenerator.seq(),callback);
//        }
//
//        HttpReqInfo(GeneratedMessageLite req, boolean encrypt, int httpSeq,HttpCallback<T> callback) {
//            this.req = req;
//            this.encrypt = encrypt;
//            this.callback = callback;
//            this.httpSeq = httpSeq;
//        }
//
//        public HttpReqInfo<T> retryCopy() {
//            HttpReqInfo info = new HttpReqInfo<>(this.req, this.encrypt, this.httpSeq,this.callback);
//            info.currRetry = this.currRetry + 1;
//            return info;
//        }
//
//        public String reqTag(){
//            return String.format("[%s]%s-%s",httpSeq,req.getClass().getSimpleName(),currRetry);
//        }
//    }
//
//    static class HttpEncipher {
//        private static final String TRANSFORMATION = "AES/GCM/NoPadding";
//        public static final int AES_AAD_LENGTH = 16;
//        private static final int IV_LENGTH = 12;
//        private Cipher mCipher;
//
//        HttpEncipher() {
//            try {
//                this.mCipher = Cipher.getInstance(TRANSFORMATION);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        public synchronized byte[] encrypt(int httpSeq, byte[] srcContent, Splogincgi.Session session)
//            throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException,
//            IllegalBlockSizeException {
//            if (mCipher == null) {
//                throw new NullPointerException("mCipher is null");
//            }
//            if (session == null) {
//                throw new IllegalArgumentException("session is null");
//            }
//            final byte[] aesKey = session.getAesKey().toByteArray();
//            final byte[] ivBuff = new byte[IV_LENGTH];
//            byte[] aesNonce = session.getAesNonce().toByteArray();
//            System.arraycopy(aesNonce, 0, ivBuff, 0, aesNonce.length);
//            byte[] seqBytes =
//                ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(httpSeq).array();
//            System.arraycopy(seqBytes, 0, ivBuff, aesNonce.length, seqBytes.length);
//            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
//            GCMParameterSpec gcmSpec = new GCMParameterSpec(AES_AAD_LENGTH * 8, ivBuff);
//            mCipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);
//            final byte[] aesAad = session.getAesAad().toByteArray();
//            mCipher.updateAAD(aesAad);
//            return mCipher.doFinal(srcContent);
//        }
//
//        public synchronized byte[] decrypt(int seq, byte[] body, byte[] bodyTag)
//            throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException,
//            IllegalBlockSizeException {
//            if (mCipher == null) {
//                throw new NullPointerException("mCipher is null");
//            }
//            if (body == null || bodyTag == null) {
//                throw new IllegalArgumentException("body or bodyTag is null");
//            }
//            final Splogincgi.Session session = RpcManager.getInstance().getAuthSession();
//            if (session == null) {
//                throw new IllegalArgumentException("session is null");
//            }
//            final byte[] aesKey = session.getAesKey().toByteArray();
//            final byte[] ivBuff = new byte[IV_LENGTH];
//            byte[] aesNonce = session.getAesNonce().toByteArray();
//            System.arraycopy(aesNonce, 0, ivBuff, 0, aesNonce.length);
//            byte[] seqBytes =
//                ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(seq).array();
//            System.arraycopy(seqBytes, 0, ivBuff, aesNonce.length, seqBytes.length);
//            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
//            GCMParameterSpec gcmSpec = new GCMParameterSpec(AES_AAD_LENGTH * 8, ivBuff);
//            mCipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);
//            byte[] bodyBuff = new byte[body.length + bodyTag.length];
//            System.arraycopy(body, 0, bodyBuff, 0, body.length);
//            System.arraycopy(bodyTag, 0, bodyBuff, body.length, bodyTag.length);
//            mCipher.updateAAD(session.getAesAad().toByteArray());
//            return mCipher.doFinal(bodyBuff);
//        }
//    }
//}
