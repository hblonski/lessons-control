package com.lessonscontrol.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Student entity.
 *
 * @author hblonski
 */
@Entity(tableName = "student",
        indices = {@Index("sdt_id")})
public class Student {

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
}
