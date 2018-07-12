package com.lessonscontrol.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

/**
 * Relationship between days of week and {@link Lesson}.
 *
 * @author hblonski
 */
@Entity(tableName = "les_schedule",
        primaryKeys = {"lesson_id", "day"})
public class Schedule {

    @ColumnInfo(name = "lesson_id")
    private long lessonID;

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
