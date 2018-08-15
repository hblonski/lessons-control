package com.lessonscontrol.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Student entity.
 *
 * @author hblonski
 */
@Entity(tableName = "student",
        indices = {@Index("sdt_id")})
public class Student implements Parcelable {

    //Used when passing an student between activities.
    public static final String STUDENT_EXTRA_KEY = "student";

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "sdt_id")
    private long ID;

    @NonNull
    @ColumnInfo(name = "sdt_name")
    private String name;

    @ColumnInfo(name = "sdt_address")
    private String address;

    @ColumnInfo(name = "sdt_email")
    private String email;

    @ColumnInfo(name = "sdt_phone")
    private String phone;

    public Student(String name, String address, String email, String phone) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public long getID() {
        return this.ID;
    }

    public void setID(@NonNull long ID) {
        this.ID = ID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //Parcelable interface methods implementation. This interface is used to allow a Student object
    //to be passed between activities.

    //Constructor that takes a Parcel and gives you an object populated with it's values. The order
    //must be the same used in the writeToParcel method (see below).
    private Student(Parcel in) {
        this.ID = in.readLong();
        this.name = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.address = in.readString();
    }

    //This is used to regenerate the object. All Parcelables must have a CREATOR
    //that implements these two methods
    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.ID);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.email);
        dest.writeString(this.address);
    }
}
