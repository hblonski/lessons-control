package com.lessonscontrol.data.entities

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import android.os.Parcel
import android.os.Parcelable

/**
 * Lesson entity.
 *
 * @author hblonski
 */
@Entity(tableName = "lesson",
        indices = [Index("les_id"),Index("les_student")],
        foreignKeys = [ForeignKey(entity = Student::class,
                parentColumns = ["sdt_id"],
                childColumns = ["les_student"],
                onDelete = CASCADE)])
class Lesson : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "les_id")
    var id: Long = 0

    @ColumnInfo(name = "les_student")
    var student: Long = 0

    /**
     * Days of the week (schedule)
     * Follows the rule:
     * X_X..., X being the first three letters of that day of the week
     * Example: a list containing Sunday, Monday and Saturday would return SUN_MON_SAT
     */
    @ColumnInfo(name = "les_days")
    var days: String? = null

    @ColumnInfo(name = "les_price")
    var price: Double? = null

    @ColumnInfo(name = "les_type")
    var type: String? = null

    //In milliseconds.
    @ColumnInfo(name = "les_nextpayment")
    var nextPayment: Long? = null

    //In milliseconds.
    @ColumnInfo(name = "les_nextdate")
    var nextDate: Long? = null

    constructor(student: Long, days: String, price: Double?, type: String, nextPayment: Long?, nextDate: Long?) {
        this.student = student
        this.days = days
        this.price = price
        this.type = type
        this.nextPayment = nextPayment
        this.nextDate = nextDate
    }


    //Parcelable interface methods implementation. This interface is used to allow a Lesson object
    //to be passed between activities.

    //Constructor that takes a Parcel and gives you an object populated with it's values. The order
    //must be the same used in the writeToParcel method (see below).
    private constructor(`in`: Parcel) {
        this.id = `in`.readLong()
        this.student = `in`.readLong()
        this.days = `in`.readString()
        this.price = `in`.readDouble()
        this.type = `in`.readString()
        this.nextPayment = `in`.readLong()
        this.nextDate = `in`.readLong()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(this.id)
        dest.writeLong(this.student)
        dest.writeString(this.days)
        dest.writeDouble(this.price!!)
        dest.writeString(this.type)
        dest.writeLong(this.nextPayment!!)
        dest.writeLong(this.nextDate!!)
    }

    companion object {

        //Used when passing an student between activities.
        const val LESSON_EXTRA_KEY = "lesson"

        //This is used to regenerate the object. All Parcelables must have a CREATOR
        //that implements these two methods
        @JvmField
        val CREATOR: Parcelable.Creator<Lesson> = object : Parcelable.Creator<Lesson> {
            override fun createFromParcel(`in`: Parcel): Lesson {
                return Lesson(`in`)
            }

            override fun newArray(size: Int): Array<Lesson?> {
                return arrayOfNulls(size)
            }
        }
    }
}
