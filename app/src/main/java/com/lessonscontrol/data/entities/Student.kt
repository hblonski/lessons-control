package com.lessonscontrol.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Student entity.
 *
 * @author hblonski
 */
@Entity(tableName = "student",
        indices = [Index("sdt_id")])
class Student : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sdt_id")
    var id: Long = 0

    @ColumnInfo(name = "sdt_name")
    var name: String

    @ColumnInfo(name = "sdt_address")
    var address: String? = null

    @ColumnInfo(name = "sdt_email")
    var email: String? = null

    @ColumnInfo(name = "sdt_phone")
    var phone: String? = null

    @ColumnInfo(name = "sdt_nextclassdate")
    var nextLessonDate: Long? = null

    constructor(name: String, address: String, email: String, phone: String) {
        this.name = name
        this.address = address
        this.email = email
        this.phone = phone
    }

    //Parcelable interface methods implementation. This interface is used to allow a Student object
    //to be passed between activities.

    //Constructor that takes a Parcel and gives you an object populated with it's values. The order
    //must be the same used in the writeToParcel method (see below).
    private constructor(`in`: Parcel) {
        this.id = `in`.readLong()
        this.name = `in`.readString()
        this.phone = `in`.readString()
        this.email = `in`.readString()
        this.address = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(this.id)
        dest.writeString(this.name)
        dest.writeString(this.phone)
        dest.writeString(this.email)
        dest.writeString(this.address)
    }

    companion object {

        //Used when passing an student between activities.
        const val STUDENT_EXTRA_KEY = "student"

        //This is used to regenerate the object. All Parcelables must have a CREATOR
        //that implements these two methods
        @JvmField
        val CREATOR: Parcelable.Creator<Student> = object : Parcelable.Creator<Student> {
            override fun createFromParcel(`in`: Parcel): Student {
                return Student(`in`)
            }

            override fun newArray(size: Int): Array<Student?> {
                return arrayOfNulls(size)
            }
        }
    }
}
