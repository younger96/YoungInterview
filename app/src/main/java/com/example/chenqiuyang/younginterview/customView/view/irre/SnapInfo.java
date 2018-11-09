//package com.example.chenqiuyang.younginterview.customView.view.irre;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//import android.support.annotation.NonNull;
//import android.util.Log;
//import com.google.protobuf.InvalidProtocolBufferException;
//import com.skyplan.moment.db.convert.PBEntityConverter;
//import com.skyplan.moment.libbasic.utils.Utils;
//import java.util.ArrayList;
//import java.util.List;
//import org.greenrobot.greendao.annotation.Convert;
//import org.greenrobot.greendao.annotation.Entity;
//import org.greenrobot.greendao.annotation.Generated;
//import org.greenrobot.greendao.annotation.Id;
//import org.greenrobot.greendao.annotation.Index;
//import org.greenrobot.greendao.annotation.Transient;
//import org.json.JSONObject;
//import spcgi.Spcgicommdef;
//import spcgi.Spsnapcgi;
//import spcgi.Spsnapcgi.SnapExtBuff;
//
///**
// * SnapInfo:【注意】数据库相关字段不可删除，加字段时，记得补充Parcelable序列化的2个方法
// */
//@Entity(indexes = { @Index(value = "svr_id, client_id", unique = true) })
//public class SnapInfo implements Parcelable {
//    public static final String IMAGE_SUFFIX ="?imageslim";
//    /** for {@link #flags} */
//    public static final int FLAG_UNREAD = 1 << 0;
//    public static final int FLAG_IGNORE_FAILED_NOTIFY = 1 << 1;
//
//    /** for {@link #memFlag} */
//    public static final int FLAG_MEM_NEW = 1 << 0;
//
//    /** for {@link #state} */
//    public static final int STATE_OK = 0;
//    public static final int STATE_SENDING = 1;
//    public static final int STATE_FAILED = 2;
//
//    /** for {@link #extras} */
//    public static final int POST_STEP_UNKNOWN = -1;
//    public static final int POST_STEP_SUCCESS = 0;
//    public static final int POST_STEP_GENERATE = 10;
//    public static final int POST_STEP_UPLOAD = 20;
//    public static final int POST_STEP_REQUEST = 30;
//
//    public static final String EXTRA_POST_ARGS = "PostArgs";
//    public static final String KEY_POST_STEP = "post_step";
//    public static final String KEY_STICKER_ARRAY = "sticker_array";
//    public static final String KEY_TEXT_ARRAY = "text_array";
//    public static final String KEY_INPUT_PATH = "input_path";
//    public static final String KEY_OUTPUT_PATH = "output_path";
//
//    @Id(autoincrement = true)
//    private Long id;
//    @Index(unique = true)
//    private long svr_id;//IpPair、snapid
//    @Index(unique = true)
//    private String client_id;//IdPair
//    private int type;// enSnapType
//    private int privateType;//enSnapPrivateType
//    private long nameplate_number;
//    @Index
//    private long uin;
//    @Index
//    private long group_uin;
//    @Index
//    private int create_time;
//    private int update_time;
//    @Convert(columnType = byte[].class, converter = PBEntityConverter.SnapImgCvt.class)
//    private Spsnapcgi.SnapImg snap_img;
//    @Convert(columnType = byte[].class, converter = PBEntityConverter.SnapVideoCvt.class)
//    private Spsnapcgi.SnapVideo snap_video;
//    /** 此字段已弃用，因为旧版数据库使用了此字段，不能删除。使用{@link #snapExt} */
//    @Deprecated
//    private byte[] ext_buff;
//    @Convert(columnType = byte[].class, converter = PBEntityConverter.CommentListCvt.class)
//    private List<Spsnapcgi.Comment> comment_list;
//    @Index
//    private int state;
//    //按位标记
//    private int flags;
//    private int rich_type;
//    @Index
//    private int del_flag;//enDelFlag
//    /** 已弃用，因为旧版数据库使用了此字段，不能删除。使用{@link #snapExt}字段，调用{@link SnapExtBuff#getSnapSourceInfo()} */
//    @Deprecated
//    @Convert(columnType = byte[].class, converter = PBEntityConverter.SnapSourceInfoCvt.class)
//    private Spsnapcgi.SnapSourceInfo sourceInfo;
//    @Convert(columnType = byte[].class, converter = PBEntityConverter.SnapExtBuffCvt.class)
//    private Spsnapcgi.SnapExtBuff snapExt;
//    /** 额外附加数据，JSON类型 */
//    @Convert(columnType = String.class, converter = PBEntityConverter.JSONCvt.class)
//    private JSONObject extras;
//
//    //*******************************以下字段不存数据库
//    @Transient
//    private long coverUin;
//
//    public long getCoverUin() {
//        return coverUin;
//    }
//
//    public void setCoverUin(long coverUin) {
//        this.coverUin = coverUin;
//    }
//
//    @Transient
//    private int snapCount;
//
//    public int getSnapCount() {
//        return snapCount;
//    }
//
//    public void setSnapCount(int snapCount) {
//        this.snapCount = snapCount;
//    }
//
//    @Transient
//    private int unreadCount;
//
//    public int getUnreadCount() {
//        return unreadCount;
//    }
//
//    public void setUnreadCount(int unreadCount) {
//        this.unreadCount = unreadCount;
//    }
//
//    @Transient
//    private int memFlag;
//
//    public int getMemFlag() {
//        return memFlag;
//    }
//
//    public void setMemFlag(int memFlag) {
//        this.memFlag = memFlag;
//    }
//
//    public boolean hasMemFlag(int mark) {
//        return (memFlag & mark) == mark;
//    }
//
//    @Transient
//    private ArrayList<String> publishersHeadUrls;
//
//    public List<String> getPublishersHeadUrls() {
//        return publishersHeadUrls;
//    }
//
//    public void setPublishersHeadUrls(ArrayList<String> publishersHeadUrls) {
//        this.publishersHeadUrls = publishersHeadUrls;
//    }
//
//    @Transient
//    private String nickName;
//
//    public String getNickName() {
//        return nickName;
//    }
//
//    public void setNickName(String nickName) {
//        this.nickName = nickName;
//    }
//
//    @Transient
//    private String headUrl;
//
//    public String getHeadUrl() {
//        return headUrl;
//    }
//
//    public void setHeadUrl(String headUrl) {
//        this.headUrl = headUrl;
//    }
//
//    @Transient
//    private int sex;
//    public int getSex() {
//        return sex;
//    }
//    public void setSex(int sex) {
//        this.sex = sex;
//    }
//
//    @Transient
//    private GroupHeadInfo groupHeadInfo;
//
//    @NonNull
//    public GroupHeadInfo getGroupHeadInfo() {
//        if (groupHeadInfo == null) {
//            return GroupHeadInfo.EMPTY;
//        }
//        return groupHeadInfo;
//    }
//
//    public void setGroupHeadInfo(GroupHeadInfo groupHeadInfo) {
//        this.groupHeadInfo = groupHeadInfo;
//    }
//
//    @Transient
//    private float sendProgress;
//
//    public float getSendProgress() {
//        return sendProgress;
//    }
//
//    public void setSendProgress(float sendProgress) {
//        this.sendProgress = sendProgress;
//    }
//
//    //*********************以下为非get、set方法
//
//    public boolean isImageSnap() {
//        return this.type == Spcgicommdef.enSnapType.SNAP_TYPE_IMG_VALUE;
//    }
//
//    public boolean isVideoSnap() {
//        return this.type == Spcgicommdef.enSnapType.SNAP_TYPE_VIDEO_VALUE;
//    }
//
//    public boolean isNormalRichType() {
//        return rich_type == Spcgicommdef.enSnapRichType.SNAP_RICH_TYPE_NORMAL_VALUE;
//    }
//
//    @NonNull
//    public String fetchThumbUrl() {
//        if (isImageSnap() && snap_img != null) {
//            return Utils.nonNull(snap_img.getThumbUrl());
//        } else if (isVideoSnap() && snap_video != null) {
//            return Utils.nonNull(snap_video.getThumbUrl());
//        }
//        return "";
//    }
//
//    @NonNull
//    public String fetchResUrl() {
//        if (isImageSnap() && snap_img != null) {
//            return Utils.nonNull(snap_img.getImgUrl());
//        } else if (isVideoSnap() && snap_video != null) {
//            return Utils.nonNull(snap_video.getVideoUrl());
//        }
//        return "";
//    }
//
//    public String fetchCoverName() {
//        if (coverUin == uin) {
//            return nickName;
//        } else {
//            return getGroupHeadInfo().nickName;
//        }
//    }
//
//    public String fetchCoverHeadUrl() {
//        if (coverUin == uin) {
//            return headUrl;
//        } else {
//            return getGroupHeadInfo().headImgUrl;
//        }
//    }
//
//    @Override
//    public SnapInfo clone() {
//        //深拷贝
//        Parcel parcel = null;
//        try {
//            parcel = Parcel.obtain();
//            parcel.writeValue(this);
//            parcel.setDataPosition(0);
//            return (SnapInfo) parcel.readValue(SnapInfo.class.getClassLoader());
//        } finally {
//            if (parcel != null) parcel.recycle();
//        }
//    }
//
//    public static SnapInfo valueOf(SnapInfo out, Spsnapcgi.Snap snap) {
//        if (out == null) {
//            out = new SnapInfo();
//        }
//        if (snap.hasId()) {
//            out.setClient_id(snap.getId().getClientId());
//            out.setSvr_id(snap.getId().getSvrId());
//        }
//        out.setType(snap.getType());
//        out.setPrivateType(snap.getPrivateType());
//        out.setUin(snap.getUin());
//        out.setGroup_uin(snap.getGroupUin());
//        out.setCreate_time(snap.getCreateTime());
//        out.setUpdate_time(snap.getUpdateTime());
//        out.setNameplate_number(snap.getNameplateNumber());
//        if (snap.hasImg()) {
//            out.setSnap_img(snap.getImg());
//        }
//        if (snap.hasVideo()) {
//            out.setSnap_video(snap.getVideo());
//        }
//        try {
//            out.setSnapExt(SnapExtBuff.parseFrom(snap.getExtBuff()));
//        } catch (InvalidProtocolBufferException e) {
//            e.printStackTrace();
//        }
//        out.setComment_list(snap.getCommentListList());
//        out.setRich_type(snap.getRichType());
//        out.setDel_flag(Spcgicommdef.enDelFlag.DELFLAG_EXIST_VALUE);
//        return out;
//    }
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public long getSvr_id() {
//        return this.svr_id;
//    }
//
//    public void setSvr_id(long svr_id) {
//        this.svr_id = svr_id;
//    }
//
//    public String getClient_id() {
//        return this.client_id;
//    }
//
//    public void setClient_id(String client_id) {
//        this.client_id = client_id;
//    }
//
//    public int getType() {
//        return this.type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    public long getUin() {
//        return this.uin;
//    }
//
//    public void setUin(long uin) {
//        this.uin = uin;
//    }
//
//    public long getGroup_uin() {
//        return this.group_uin;
//    }
//
//    public void setGroup_uin(long group_uin) {
//        this.group_uin = group_uin;
//    }
//
//    public int getCreate_time() {
//        return this.create_time;
//    }
//
//    public void setCreate_time(int create_time) {
//        this.create_time = create_time;
//    }
//
//    public int getUpdate_time() {
//        return this.update_time;
//    }
//
//    public void setUpdate_time(int update_time) {
//        this.update_time = update_time;
//    }
//
//    public Spsnapcgi.SnapImg getSnap_img() {
//        return this.snap_img;
//    }
//
//    /**
//     * 获取大图的url，加?imageslim后缀用于瘦身
//     * @return
//     */
//    public String getLargetImgUrl(){
//        if(snap_img!=null) {
//            String dataUri = snap_img.getImgUrl();
//            if (dataUri != null && !dataUri.contains("?") && (dataUri.startsWith("http")
//                || dataUri.startsWith("https"))) {
//                dataUri = dataUri + IMAGE_SUFFIX;
//            }
//            return dataUri;
//        }
//        return "";
//    }
//
//    public void setSnap_img(Spsnapcgi.SnapImg snap_img) {
//        this.snap_img = snap_img;
//    }
//
//    public Spsnapcgi.SnapVideo getSnap_video() {
//        return this.snap_video;
//    }
//
//    public void setSnap_video(Spsnapcgi.SnapVideo snap_video) {
//        this.snap_video = snap_video;
//    }
//
//    @Deprecated
//    public byte[] getExt_buff() {
//        return this.ext_buff;
//    }
//
//    @Deprecated
//    public void setExt_buff(byte[] ext_buff) {
//        this.ext_buff = ext_buff;
//    }
//
//    public List<Spsnapcgi.Comment> getComment_list() {
//        return this.comment_list;
//    }
//
//    public void setComment_list(List<Spsnapcgi.Comment> comment_list) {
//        this.comment_list = comment_list;
//    }
//
//    public int getState() {
//        return this.state;
//    }
//
//    public void setState(int state) {
//        this.state = state;
//    }
//
//    public int getFlags() {
//        return this.flags;
//    }
//
//    public void setFlags(int flags) {
//        this.flags = flags;
//    }
//
//    public boolean hasFlag(int mark) {
//        return (flags & mark) == mark;
//    }
//
//    public int getPrivateType() {
//        return this.privateType;
//    }
//
//    public void setPrivateType(int privateType) {
//        this.privateType = privateType;
//    }
//
//    public long getNameplate_number() {
//        return this.nameplate_number;
//    }
//
//    public void setNameplate_number(long nameplate_number) {
//        this.nameplate_number = nameplate_number;
//    }
//
//    public int getRich_type() {
//        return rich_type;
//    }
//
//    public void setRich_type(int rich_type) {
//        this.rich_type = rich_type;
//    }
//
//    public int getDel_flag() {
//        return this.del_flag;
//    }
//
//    public void setDel_flag(int del_flag) {
//        this.del_flag = del_flag;
//    }
//
//    @Deprecated
//    public Spsnapcgi.SnapSourceInfo getSourceInfo() {
//        return this.sourceInfo;
//    }
//
//    @Deprecated
//    public void setSourceInfo(Spsnapcgi.SnapSourceInfo sourceInfo) {
//        this.sourceInfo = sourceInfo;
//    }
//
//    public Spsnapcgi.SnapExtBuff getSnapExt() {
//        return this.snapExt;
//    }
//
//    public void setSnapExt(Spsnapcgi.SnapExtBuff snapExt) {
//        this.snapExt = snapExt;
//    }
//
//    public JSONObject getExtras() {
//        return this.extras;
//    }
//
//    public void setExtras(JSONObject extras) {
//        this.extras = extras;
//    }
//
//    //*************************** Parcelable implements
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    public static final Creator<SnapInfo> CREATOR = new Creator<SnapInfo>() {
//        @Override
//        public SnapInfo createFromParcel(Parcel in) {
//            return new SnapInfo(in);
//        }
//
//        @Override
//        public SnapInfo[] newArray(int size) {
//            return new SnapInfo[size];
//        }
//    };
//
//    protected SnapInfo(Parcel in) {
//        if (in.readByte() == 0) {
//            id = null;
//        } else {
//            id = in.readLong();
//        }
//        svr_id = in.readLong();
//        client_id = in.readString();
//        type = in.readInt();
//        privateType = in.readInt();
//        nameplate_number = in.readLong();
//        uin = in.readLong();
//        group_uin = in.readLong();
//        create_time = in.readInt();
//        update_time = in.readInt();
//        snap_img = new PBEntityConverter.SnapImgCvt().convertToEntityProperty(in.createByteArray());
//        snap_video =
//            new PBEntityConverter.SnapVideoCvt().convertToEntityProperty(in.createByteArray());
//        ext_buff = in.createByteArray();
//        comment_list =
//            new PBEntityConverter.CommentListCvt().convertToEntityProperty(in.createByteArray());
//        state = in.readInt();
//        flags = in.readInt();
//        rich_type = in.readInt();
//        del_flag = in.readInt();
//        sourceInfo =
//            new PBEntityConverter.SnapSourceInfoCvt().convertToEntityProperty(in.createByteArray());
//        snapExt =
//            new PBEntityConverter.SnapExtBuffCvt().convertToEntityProperty(in.createByteArray());
//        extras = new PBEntityConverter.JSONCvt().convertToEntityProperty(in.readString());
//
//        //以下字段不存数据库
//        coverUin = in.readLong();
//        snapCount = in.readInt();
//        unreadCount = in.readInt();
//        memFlag = in.readInt();
//        publishersHeadUrls = in.createStringArrayList();
//        nickName = in.readString();
//        headUrl = in.readString();
//        groupHeadInfo = in.readParcelable(GroupHeadInfo.class.getClassLoader());
//        sendProgress = in.readFloat();
//    }
//
//    @Generated(hash = 2089481767)
//    public SnapInfo() {
//    }
//
//    @Generated(hash = 294240779)
//    public SnapInfo(Long id, long svr_id, String client_id, int type, int privateType,
//            long nameplate_number, long uin, long group_uin, int create_time, int update_time,
//            Spsnapcgi.SnapImg snap_img, Spsnapcgi.SnapVideo snap_video, byte[] ext_buff,
//            List<Spsnapcgi.Comment> comment_list, int state, int flags, int rich_type, int del_flag,
//            Spsnapcgi.SnapSourceInfo sourceInfo, Spsnapcgi.SnapExtBuff snapExt, JSONObject extras) {
//        this.id = id;
//        this.svr_id = svr_id;
//        this.client_id = client_id;
//        this.type = type;
//        this.privateType = privateType;
//        this.nameplate_number = nameplate_number;
//        this.uin = uin;
//        this.group_uin = group_uin;
//        this.create_time = create_time;
//        this.update_time = update_time;
//        this.snap_img = snap_img;
//        this.snap_video = snap_video;
//        this.ext_buff = ext_buff;
//        this.comment_list = comment_list;
//        this.state = state;
//        this.flags = flags;
//        this.rich_type = rich_type;
//        this.del_flag = del_flag;
//        this.sourceInfo = sourceInfo;
//        this.snapExt = snapExt;
//        this.extras = extras;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        if (id == null) {
//            dest.writeByte((byte) 0);
//        } else {
//            dest.writeByte((byte) 1);
//            dest.writeLong(id);
//        }
//        dest.writeLong(svr_id);
//        dest.writeString(client_id);
//        dest.writeInt(type);
//        dest.writeInt(privateType);
//        dest.writeLong(nameplate_number);
//        dest.writeLong(uin);
//        dest.writeLong(group_uin);
//        dest.writeInt(create_time);
//        dest.writeInt(update_time);
//        dest.writeByteArray(new PBEntityConverter.SnapImgCvt().convertToDatabaseValue(snap_img));
//        dest.writeByteArray(
//            new PBEntityConverter.SnapVideoCvt().convertToDatabaseValue(snap_video));
//        dest.writeByteArray(ext_buff);
//        dest.writeByteArray(
//            new PBEntityConverter.CommentListCvt().convertToDatabaseValue(comment_list));
//        dest.writeInt(state);
//        dest.writeInt(this.flags);
//        dest.writeInt(rich_type);
//        dest.writeInt(del_flag);
//        dest.writeByteArray(
//            new PBEntityConverter.SnapSourceInfoCvt().convertToDatabaseValue(sourceInfo));
//        dest.writeByteArray(new PBEntityConverter.SnapExtBuffCvt().convertToDatabaseValue(snapExt));
//        dest.writeString(new PBEntityConverter.JSONCvt().convertToDatabaseValue(extras));
//
//        //以下字段不存数据库
//        dest.writeLong(coverUin);
//        dest.writeInt(snapCount);
//        dest.writeInt(unreadCount);
//        dest.writeInt(memFlag);
//        dest.writeStringList(publishersHeadUrls);
//        dest.writeString(nickName);
//        dest.writeString(headUrl);
//        dest.writeParcelable(groupHeadInfo, flags);
//        dest.writeFloat(sendProgress);
//    }
//
//    @Override
//    public String toString() {
//        return "SnapInfo{" +
//            "id=" + id +
//            ", svr_id=" + svr_id +
//            ", client_id='" + client_id + '\'' +
//            ", type=" + type +
//            ", privateType=" + privateType +
//            ", nameplate_number=" + nameplate_number +
//            ", uin=" + uin +
//            ", group_uin=" + group_uin +
//            ", create_time=" + create_time +
//            ", update_time=" + update_time +
//            ", snap_img=" + snap_img +
//            ", snap_video=" + snap_video +
//            //", ext_buff=" + Arrays.toString(ext_buff) +
//            ", comment_list=" + comment_list +
//            ", state=" + state +
//            ", flags=" + flags +
//            ", rich_type=" + rich_type +
//            ", del_flag=" + del_flag +
//            ", sourceInfo=" + sourceInfo +
//            ", snapExt=" + snapExt +
//            ", extras=" + extras +
//            ", coverUin=" + coverUin +
//            ", snapCount=" + snapCount +
//            ", unreadCount=" + unreadCount +
//            ", memFlag=" + memFlag +
//            ", publishersHeadUrls=" + publishersHeadUrls +
//            ", nickName='" + nickName + '\'' +
//            ", headUrl='" + headUrl + '\'' +
//            ", groupHeadInfo=" + groupHeadInfo +
//            '}';
//    }
//}