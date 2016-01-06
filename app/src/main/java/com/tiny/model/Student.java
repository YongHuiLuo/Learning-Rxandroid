package com.tiny.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created JackLuo
 * 实现主要功能：
 * 创建时间： on 2016/1/5.
 * 修改者： 修改日期： 修改内容：
 */
public class Student implements Parcelable {

    public String name;
    public List<String> cources;

    public Student(String name, List<String> cources) {
        this.name = name;
        this.cources = cources;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeStringList(this.cources);
    }

    public Student() {
    }

    protected Student(Parcel in) {
        this.name = in.readString();
        this.cources = in.createStringArrayList();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
