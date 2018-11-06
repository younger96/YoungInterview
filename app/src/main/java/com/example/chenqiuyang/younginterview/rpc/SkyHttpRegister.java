//package com.example.chenqiuyang.younginterview.rpc;
//
//import android.text.TextUtils;
//
//import com.google.protobuf.GeneratedMessageLite;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import okhttp3.RequestBody;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.http.Body;
//import retrofit2.http.Header;
//import retrofit2.http.POST;
//import retrofit2.http.Url;
//import spcgi.Spbasecgi;
//import spcgi.Spcardcgi;
//import spcgi.Spcontactcgi;
//import spcgi.Spgroupcgi;
//import spcgi.Splogincgi;
//import spcgi.Spmsgcgi;
//import spcgi.Spreportcgi;
//import spcgi.Spsnapcgi;
//import spcgi.Spsynccgi;
//
///**
// * Created by lihaibiao on 2018/5/10 14:29
// * E-Mail Address：lihaibiaowork@gmail.com
// */
//class SkyHttpRegister {
//    private static final String TAG = "SkyHttpRegister";
//    private final static String HEADER_UIN = "X-PHXRPC-Uin";
//    private static final Map<Class<? extends GeneratedMessageLite>,String> sReqPathMatcher = new HashMap<>();
//    private static final Map<Class<? extends GeneratedMessageLite>,Class<? extends GeneratedMessageLite>> sRespClassMap = new HashMap<>();
//    static {
//        register(Splogincgi.LoginReq.class, Splogincgi.LoginResp.class,"/skyplan-bin/login/login");
//        register(Splogincgi.AuthReq.class, Splogincgi.AuthResp.class,"/skyplan-bin/login/auth");
//        register(Splogincgi.LogoutReq.class, Splogincgi.LogoutResp.class,"/skyplan-bin/login/logout");
//        register(Splogincgi.SendSmsCodeReq.class, Splogincgi.SendSmsCodeResp.class,"/skyplan-bin/login/send-sms-code");
//        register(Splogincgi.LoadConfigReq.class, Splogincgi.LoadConfigResp.class,"/skyplan-bin/login/load-config");
//
//        register(Spsnapcgi.GetCoverSnapListReq.class, Spsnapcgi.GetCoverSnapListResp.class,"/skyplan-bin/snap/get-cover-snap-list");
//        register(Spsnapcgi.GetMySnapListReq.class, Spsnapcgi.GetMySnapListResp.class,"/skyplan-bin/snap/get-my-snap");
//        register(Spsynccgi.SyncReq.class, Spsynccgi.SyncResp.class,"/skyplan-bin/sync/sync");
//        register(Spsnapcgi.CreateSnapReq.class, Spsnapcgi.CreateSnapResp.class,"/skyplan-bin/snap/create-snap");
//        register(Spsnapcgi.GetSnapDetailReq.class, Spsnapcgi.GetSnapDetailResp.class, "/skyplan-bin/snap/get-snap-detail");
//        //register(Spsnapcgi.UpdateCommentReq.class, Spsnapcgi.UpdateCommentResp.class, "/skyplan-bin/snap/update-comment");
//        register(Spsnapcgi.AddCommentReq.class, Spsnapcgi.AddCommentResp.class, "/skyplan-bin/snap/add-comment");
//        register(Spsnapcgi.DelCommentReq.class, Spsnapcgi.DelCommentResp.class, "/skyplan-bin/snap/del-comment");
//        register(Spbasecgi.GetUploadCdnKeyReq.class, Spbasecgi.GetUploadCdnKeyResp.class, "/skyplan-bin/base/get-upload-cdn-key");
//        register(Spsnapcgi.DelSnapReq.class, Spsnapcgi.DelSnapResp.class,"/skyplan-bin/snap/del-snap");
//        register(Spbasecgi.ModSelfProfileReq.class, Spbasecgi.ModSelfProfileResp.class,"/skyplan-bin/base/mod-self-profile");
//        register(Spsnapcgi.ShareSnapReq.class, Spsnapcgi.ShareSnapResp.class, "/skyplan-bin/snap/share-snap");
//
//        //contact
//        register(Spcontactcgi.SearchFriendByPhotoReq.class, Spcontactcgi.SearchFriendByPhotoResp.class,"/skyplan-bin/contact/search-friend-by-photo");
//        register(Spcontactcgi.AddFriendReq.class, Spcontactcgi.AddFriendResp.class,"/skyplan-bin/contact/add-friend");
//        register(Spcontactcgi.DelFriendReq.class, Spcontactcgi.DelFriendResp.class,"/skyplan-bin/contact/del-friend");
//        register(Spcontactcgi.AcceptFriendReq.class, Spcontactcgi.AcceptFriendResp.class,"/skyplan-bin/contact/accept-friend");
//        register(Spcontactcgi.GetContactReq.class, Spcontactcgi.GetContactResp.class,"/skyplan-bin/contact/get-contact");
//        register(Spcontactcgi.GetAddFriendLinkReq.class, Spcontactcgi.GetAddFriendLinkResp.class,"/skyplan-bin/contact/get-add-friend-link");
//        register(Spcontactcgi.GetOpenProfileReq.class, Spcontactcgi.GetOpenProfileResp.class,"/skyplan-bin/contact/get-open-profile");
//        register(Spcontactcgi.BlockUserReq.class, Spcontactcgi.BlockUserResp.class,"/skyplan-bin/contact/block-user");
//
//        //group
//        register(Spgroupcgi.CreateGroupReq.class, Spgroupcgi.CreateGroupResp.class,"/skyplan-bin/group/create-group");
//        register(Spgroupcgi.ModGroupAttrReq.class, Spgroupcgi.ModGroupAttrResp.class,"/skyplan-bin/group/mod-group-attr");
//        register(Spgroupcgi.QuitGroupReq.class, Spgroupcgi.QuitGroupResp.class,"/skyplan-bin/group/quit-group");
//        register(Spgroupcgi.GetGroupContactReq.class, Spgroupcgi.GetGroupContactResp.class,"/skyplan-bin/group/get-group-contact");
//        register(Spgroupcgi.GetGroupMemberProfileReq.class, Spgroupcgi.GetGroupMemberProfileResp.class,"/skyplan-bin/group/get-group-member-profile");
//        register(Spgroupcgi.ShareGroupToWxaReq.class, Spgroupcgi.ShareGroupToWxaResp.class,"/skyplan-bin/group/share-group-to-wxa");
//
//        //msg
//        register(Spmsgcgi.SendChatMsgReq.class, Spmsgcgi.SendChatMsgResp.class,"/skyplan-bin/msg/send-chat-msg");
//
//        //cardmark
//        register(Spcardcgi.GetAllCardEditInfoReq.class, Spcardcgi.GetAllCardEditInfoResp.class,"/skyplan-bin/card/get-all-card-edit-info");
//        register(Spcardcgi.CardMarkReq.class, Spcardcgi.CardMarkResp.class,"/skyplan-bin/card/card-mark");
//
//        //memoir
//        register(Spsnapcgi.DelMemoirSnapReq.class, Spsnapcgi.DelMemoirSnapResp.class,"/skyplan-bin/snap/del-memoir-snap");
//
//        //sticker
//        register(Spcardcgi.LoadStickersReq.class, Spcardcgi.LoadStickersResp.class,"/skyplan-bin/card/load-stickers");
//
//        //report
//        register(Spreportcgi.WifiInfoReportReq.class, Spreportcgi.WifiInfoReportResp.class, "/skyplan-bin/report/wifi-info-report");
//        register(Spreportcgi.OpReportReq.class, Spreportcgi.OpReportResp.class,"/skyplan-bin/report/op-report");
//
//        //location
//        register(Spcardcgi.PlaceAbroadSearchReq.class, Spcardcgi.PlaceAbroadSearchResp.class, "/skyplan-bin/card/place-abroad-search");
//        register(Spcardcgi.PlaceSearchReq.class, Spcardcgi.PlaceSearchResp.class, "/skyplan-bin/card/place-search");
//
//        //stranger
//        register(Spsnapcgi.GetPublicUserTopReq.class, Spsnapcgi.GetPublicUserTopResp.class,"/skyplan-bin/snap/get-public-user-top");
//        register(Spsnapcgi.GetPublicUserBottomReq.class, Spsnapcgi.GetPublicUserBottomResp.class,"/skyplan-bin/snap/get-public-user-bottom");
//        register(Spsnapcgi.GetPublicUserSnapListReq.class, Spsnapcgi.GetPublicUserSnapListResp.class,"/skyplan-bin/snap/get-public-user-snap-list");
//
//    }
//
//    public static String getReqUrl(Class reqClazz){
//        String reqPath = sReqPathMatcher.get(reqClazz);
//        if(TextUtils.isEmpty(reqPath)){
//            throw new IllegalArgumentException(String.format(TAG + " path not register,reqClazz = %s",reqClazz));
//        }
//        return reqPath;
//    }
//
//    @SuppressWarnings("unchecked")
//    public static Class<? extends GeneratedMessageLite> getRespClazz(Class<? extends GeneratedMessageLite> reqClazz){
//        return sRespClassMap.get(reqClazz);
//    }
//
//    private static void register(Class<? extends GeneratedMessageLite> reqClazz,Class<? extends GeneratedMessageLite> respClazz,String path){
//        sReqPathMatcher.put(reqClazz,path);
//        sRespClassMap.put(reqClazz,respClazz);
//    }
//
//    public interface Dispatcher{
//        @POST
//        Call<ResponseBody> dispatch(@Url String cgi_url, @Body RequestBody buf, @Header(HEADER_UIN) String uin);
//    }
//}
