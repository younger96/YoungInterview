package com.example.chenqiuyang.younginterview.ipc.serialize;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * User: LJM
 * Date&Time: 2016-02-22 & 14:52
 * Describe: Describe Text
 * 1、复写describeContents方法和writeToParcel方法
 * 2、实例化静态内部对象CREATOR，实现接口Parcelable.Creator 。
 *
 * Parcelable方式的实现原理是将一个完整的对象进行分解，
 * 而分解后的每一部分都是Intent所支持的数据类型，这样也就实现传递对象的功能了
 */
public class PenParcel implements Parcelable{
    private String color;
    private int size;
    
    // 系统自动添加，给createFromParcel里面用
    protected PenParcel(Parcel in) {
        color = in.readString();
        size = in.readInt();
    }


    public static final Creator<PenParcel> CREATOR = new Creator<PenParcel>() {
        /**
         *
         * @param in
         * @return
         * createFromParcel()方法中我们要去读取刚才写出的name和age字段，
         * 并创建一个Person对象进行返回，其中color和size都是调用Parcel的readXxx()方法读取到的，
         * 注意这里读取的顺序一定要和刚才写出的顺序完全相同。
         * 读取的工作我们利用一个构造函数帮我们完成了
         */
        @Override
        public PenParcel createFromParcel(Parcel in) {
            return new PenParcel(in); // 在构造函数里面完成了 读取 的工作
        }
        //供反序列化本类数组时调用的
        @Override
        public PenParcel[] newArray(int size) {
            return new PenParcel[size];
        }
    };
    
    @Override
    public int describeContents() {
        return 0;  // 内容接口描述，默认返回0即可。
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(color);  // 写出 color
        dest.writeInt(size);  // 写出 size
    }
    // ======分割线，写写get和set
    //个人自己添加
    public PenParcel() {
    }
    //个人自己添加
    public PenParcel(String color, int size) {
        this.color = color;
        this.size = size;
    }
    
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
}