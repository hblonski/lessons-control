package com.lessonscontrol.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Lesson entity.
 *
 * @author hblonski
 */
@Entity(tableName = "lesson",
        indices = {@Index("les_id"), @Index("les_student")},
        foreignKeys = @ForeignKey(entity = Student.class,
                parentColumns = "sdt_id",
                childColumns = "les_student",
                onDelete = CASCADE))
public class Lesson implements Parcelable {

    //Used when passing an student between activities.
    public static final String LESSON_EXTRA_KEY = "lesson";

    public static final long NO_DATE_SELECTED = -1;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "les_id")
    private long ID;

    @ColumnInfo(name = "les_student")
    private long student;

    /**
     * Days of the week (schedule)
     * Follows the rule:
     * X_X..., X being the first three letters of that day of the week
     * Example: a list containing Sunday, Monday and Saturday would return SUN_MON_SAT
     */
    @ColumnInfo(name = "les_days")
    private String days;

    @ColumnInfo(name = "les_price")
    private Double price;

    @ColumnInfo(name = "les_type")
    private String type;

    //In milliseconds.
    @ColumnInfo(name = "les_nextpayment")
    private long nextPayment;

    //In milliseconds.
    @ColumnInfo(name = "les_nextdate")
    private long nextDate;

    public Lesson(long student, String days, Double price, String type, long nextPayment, long nextDate) {
        this.student = student;
        this.days = days;
        this.price = price;
        this.type = type;
        this.nextPayment = nextPayment;
        this.nextDate = nextDate;
    }

    @NonNull
    public long getID() {
        return this.ID;
    }

    public void setID(@NonNull long ID) {
        this.ID = ID;
    }

    public long getStudent() {
        return this.student;
    }

    public void setStudent(long student) {
        this.student = student;
    }

    public String getDays() {
        return this.days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getNextPayment() {
        return this.nextPayment;
    }

    public void setNextPayment(long nextPayment) {
        this.nextPayment = nextPayment;
    }

    public long getNextDate() {
        return this.nextDate;
    }

    public void setNextDate(long nextDate) {
        this.nextDate = nextDate;
    }


    //Parcelable interface methods implementation. This interface is used to allow a Lesson object
    //to be passed between activities.

    //Constructor that takes a Parcel and gives you an object populated with it's values. The order
    //must be the same used in the writeToParcel method (see below).
    private Lesson(Parcel in) {
        this.ID = in.readLong();
        this.student = in.readLong();
        this.days = in.readString();
        this.price = in.readDouble();
        this.type = in.readString();
        this.nextPayment = in.readLong();
        this.nextDate = in.readLong();
    }

    //This is used to regenerate the object. All Parcelables must have a CREATOR
    //that implements these two methods
    public static final Parcelable.Creator<Lesson> CREATOR = new Parcelable.Creator<Lesson>() {
        public Lesson createFromParcel(Parcel in) {
            return new Lesson (in);
        }

        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.ID);
        dest.writeLong(this.student);
        dest.writeString(this.days);
        dest.writeDouble(this.price);
        dest.writeString(this.type);
        dest.writeLong(this.nextPayment);
        dest.writeLong(this.nextDate);
    }
}
