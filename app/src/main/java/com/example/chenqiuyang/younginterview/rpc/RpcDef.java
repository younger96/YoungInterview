//package com.example.chenqiuyang.younginterview.rpc;
//
//import spcgi.Spcgicommdef;
//
///**
// * Created by lihaibiao on 2018/5/10 14:27
// * E-Mail Address：lihaibiaowork@gmail.com
// */
//public interface RpcDef {
//    public static final int ERR_TYPE_OK =  0;
//    public static final int ERR_TYPE_SVR = 1;
//    public static final int ERR_TYPE_LOCAL = 2;
//
//    //from cgicommdef.proto
//
//    public static final int CODE_OK = Spcgicommdef.enCgiErrorCode.kOK_VALUE;// Comm::kOK
//    int CODE_PARSE_ERR = 64;
//    int CODE_NETWORK_UNAVAILABLE = 65;
//    int CODE_NOT_LOGIN = 66;
//    public static final int CODE_NOT_EXIST = Spcgicommdef.enCgiErrorCode.kNotExist_VALUE;// Comm::kReadNotExist
//    public static final int CODE_ERR_ARGS = Spcgicommdef.enCgiErrorCode.kErrArgs_VALUE;// Comm::kErrArgs
//    public static final int CODE_ERR_SYS = Spcgicommdef.enCgiErrorCode.kErrSys_VALUE;// Comm::kErrSys
//
//    // -10000]
//    public static final int CODE_SESSION_EXPIRE = Spcgicommdef.enCgiErrorCode.kErrSession_Expire_VALUE;// Comm::kErrSys
//    int CODE_REQUEST_TOO_LARGE = Spcgicommdef.enCgiErrorCode.kErrRequest_TooLarge_VALUE;
//
//}
