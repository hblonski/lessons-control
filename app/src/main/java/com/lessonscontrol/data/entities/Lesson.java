package com.lessonscontrol.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Lesson entity.
 *
 * @author hblonski
 */
@Entity(tableName = "lesson",
        foreignKeys = @ForeignKey(entity = Student.class,
                parentColumns = "id",
                childColumns = "student",
                onDelete = CASCADE))
public class Lesson {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "les_id")
    private long ID;

    @ColumnInfo(name = "les_student")
    private long student;

    @ColumnInfo(name = "les_days")
    private String days;

    @ColumnInfo(name = "les_price")
    private Double price;

    @ColumnInfo(name = "les_type")
    private String type;

    //In milliseconds.
    @ColumnInfo(name = "les_nextpayment")
    private long nextPayment;

    public Lesson(long student, String days, Double price, String type, long nextPayment) {
        this.student = student;
        this.days = days;
        this.price = price;
        this.type = type;
        this.nextPayment = nextPayment;
    }

    @NonNull
    public long getID() {
        return this.ID;
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
}
