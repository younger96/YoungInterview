package com.example.chenqiuyang.younginterview.act_frag.mvvm.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserBean implements Parcelable{
    private String name; //姓名
    private String password; //年龄

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserBean() {
    }

    public UserBean(String username, String password) {
        this.name = username;
        this.password = password;
    }

    public UserBean(Parcel parcel) {
        name = parcel.readString();
        password = parcel.readString();
    }
    public static final Creator<UserBean> CREATOR=new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel parcel) {
            return new UserBean(parcel);
        }

        @Override
        public UserBean[] newArray(int i) {
            return new UserBean[i];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.password);
    }
}