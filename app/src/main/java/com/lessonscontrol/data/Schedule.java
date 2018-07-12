package com.lessonscontrol.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

/**
 * Relationship between days of week and {@link Lesson}.
 *
 * @author hblonski
 */
@Entity(tableName = "schedule",
        primaryKeys = {"sch_lessonid", "sch_day"})
public class Schedule {

    @ColumnInfo(name = "sch_lessonid")
    @NonNull
    private long lessonID;

    @ColumnInfo(name = "sch_day")
    @NonNull
    private int day;

    public Schedule(long lessonID, int day) {
        this.lessonID = lessonID;
        this.day = day;
    }

    public long getLessonID() {
        return this.lessonID;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
