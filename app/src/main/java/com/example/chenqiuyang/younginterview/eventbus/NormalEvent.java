package com.example.chenqiuyang.younginterview.eventbus;

/**
 * Created by lihaibiao on 2018/5/14 12:27
 * E-Mail Address：lihaibiaowork@gmail.com
 */
public class NormalEvent {
    public static final String TYPE_FIRST_LOGIN_SUCCESS = "TYPE_FIRST_LOGIN_SUCCESS";
    public static final String LOGIN_STATE_CHANGED = "LOGIN_STATE_CHANGED";
    public static final String TYPE_SEND_SNAP_STATE_UPDATE = "TYPE_SEND_SNAP_STATE_UPDATE";
    public static final String TYPE_SEND_SNAP_PROGRESS_UPDATE = "TYPE_SEND_SNAP_PROGRESS_UPDATE";
    public static final String TYPE_SYNC_COMPLETE = "TYPE_SYNC_COMPLETE";
    public static final String TYPE_SNAP_DELETED = "TYPE_SNAP_DELETED";
    public static final String TYPE_CREATE_GRUOP_SUCCESS = "TYPE_CREATE_GROUP_SUCCESS";
    public static final String TYPE_CONTACT_LIST_CHANGE = "TYPE_CONTACT_LIST_CHANGE";
    public static final String TYPE_GROUP_LIST_CHANGE = "TYPE_GROUP_LIST_CHANGE";
    public static final String TYPE_CHAT_MSG_SEND_COMPLETE = "TYPE_CHAT_MSG_SEND_COMPLETE";
    public static final String TYPE_CHAT_MSG_SEND_FAILED = "TYPE_CHAT_MSG_SEND_FAILED";
    public static final String TYPE_TALK_DRAFT_CHANGED = "TYPE_TALK_DRAFT_CHANGED";
    public static final String TYPE_TALK_LIST_UPDATE = "TYPE_TALK_LIST_UPDATE";
    public static final String TYPE_CURR_TALK_MSG_UPDATE = "TYPE_CURR_TALK_MSG_UPDATE";
    public static final String TYPE_TOTAL_UNREAD_COUNT_UPDATE = "TYPE_TOTAL_UNREAD_COUNT_UPDATE";
    public static final String TYPE_SESSION_EXPIRE = "TYPE_SESSION_EXPIRE";
    public static final String TYPE_FRIEND_MAP_LOAD_COMPLETE = "TYPE_FRIEND_MAP_LOAD_COMPLETE";
    public static final String TYPE_SEND_SNAP_FAIL = "TYPE_SEND_SNAP_FAIL";
    public static final String SPACE_LIST_TIPS_UPDATE = "SPACE_LIST_TIPS_UPDATE";
    public static final String TYPE_OPEN_CAMERA = "OPEN_CAMERA";
    public static final String GROUP_INFO_UPDATE = "GROUP_INFO_UPDATE";
    public static final String NETWORK_CONNECT_CHANGED = "NETWORK_CONNECT_CHANGED";
    public static final String REPORT_PUSH_DELAYED = "REPORT_PUSH_DELAYED";
    public static final String PROFILE_UPDATED = "PROFILE_UPDATED";
    public static final String NOTIFY_RELIEVE_FRIEND = "NOTIFY_RELIEVE_FRIEND";
    public static final String SEND_MSG_TO_WX_SUCCESS = "SEND_MSG_TO_WX_SUCCESS";
    public static final String SEND_MSG_TO_WX_COMPLETE = "SEND_MSG_TO_WX_COMPLETE";
    public static final String START_SEND_MSG_TO_WX = "START_SEND_MSG_TO_WX";
    public static final String UPGRADE_NOTIFY = "UPGRADE_NOTIFY";
    public static final String CLOSED_SNAP_ITEM_VIEW = "COLOSED_SNAP_ITEM_VIEW";
    public static final String SYNC_OPERATION = "SYNC_OPERATION";
    public static final String SYNC_OPERATION_DELETED = "SYNC_OPERATION_DELETED";
    public static final String CHAT_MSG_SOURCE_UPDATE = "CHAT_MSG_SOURCE_UPDATE";
    public static final String SNAP_STATE_RESET = "SNAP_STATE_RESET";

    public final String type;
    private final Object data;

    public NormalEvent(String type){
        this(type,null);
    }

    public NormalEvent(String type, Object data) {
        this.type = type;
        this.data = data;
    }

    public <T> T data(){
        return (T) data;
    }

    @Override
    public String toString() {
        return "NormalEvent{" +
                "type='" + type + '\'' +
                ", data=" + String.valueOf(data) +
                '}';
    }
}