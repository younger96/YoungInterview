//package com.example.chenqiuyang.younginterview.rpc;
//
///**
// * Created by lihaibiao on 2018/5/11 11:50
// * E-Mail Address：lihaibiaowork@gmail.com
// */
//public class RpcResponse<T> {
//    public final int errType;
//    public final int errCode;
//    public final String message;
//    public final T data;
//    public final Object extra;
//
//    private RpcResponse(int errType, int errCode) {
//        this(errType,errCode,null);
//    }
//
//    private RpcResponse(int errType, int errCode,T data) {
//        this(errType,errCode,null,data);
//    }
//
//    private RpcResponse(int errType, int errCode, String message, T data) {
//        this(errType,errCode,message,data,null);
//    }
//
//    private RpcResponse(int errType, int errCode, String message, T data,Object extra) {
//        this.errType = errType;
//        this.errCode = errCode;
//        this.message = message;
//        this.data = data;
//        this.extra = extra;
//    }
//
//    public <E> E extra(){
//        return (E) extra;
//    }
//
//    public boolean success(){
//        return errType == RpcDef.ERR_TYPE_OK && errCode == RpcDef.CODE_OK;
//    }
//
//    public static <E> RpcResponse<E> ok(E data){
//        return new RpcResponse<>(RpcDef.ERR_TYPE_OK,RpcDef.CODE_OK,data);
//    }
//
//    public static <E> RpcResponse<E> ok(E data,Object extra){
//        return new RpcResponse<>(RpcDef.ERR_TYPE_OK,RpcDef.CODE_OK,null,data,extra);
//    }
//
//    public static <E> RpcResponse<E> err(int errType,int errCode){
//        return new RpcResponse<>(errType,errCode);
//    }
//
//    public static <E> RpcResponse<E> with(int errType,int errCode,E data){
//        return new RpcResponse<>(errType,errCode,data);
//    }
//
//    public static <E> RpcResponse<E> with(int errType,int errCode,E data,Object extra){
//        return new RpcResponse<>(errType,errCode,null,data,extra);
//    }
//
//    public static <E> RpcResponse<E> errLocal(){
//        return new RpcResponse<>(RpcDef.ERR_TYPE_LOCAL,RpcDef.CODE_OK);
//    }
//
//    public static <E> RpcResponse<E> errLocal(int errCode){
//        return new RpcResponse<>(RpcDef.ERR_TYPE_LOCAL,errCode);
//    }
//
//    public static <E> RpcResponse<E> errLocal(int errCode,String message){
//        return new RpcResponse<>(RpcDef.ERR_TYPE_LOCAL,errCode,message,null);
//    }
//
//    public static <E> RpcResponse<E> errSvr(){
//        return new RpcResponse<>(RpcDef.ERR_TYPE_SVR,RpcDef.CODE_OK);
//    }
//
//    public static <E> RpcResponse<E> errSvr(int errCode){
//        return new RpcResponse<>(RpcDef.ERR_TYPE_SVR,errCode);
//    }
//
//    @Override
//    public String toString() {
//        return "RpcResponse{" +
//                "errType=" + errType +
//                ", errCode=" + errCode +
//                ", message='" + message + '\'' +
//                '}';
//    }
//
//    public String toString2(){
//        return "RpcResponse{" +
//            "errType=" + errType +
//            ", errCode=" + errCode +
//            ", message='" + message + '\'' +
//            ", data=" + String.valueOf(data) +
//            '}';
//    }
//}
