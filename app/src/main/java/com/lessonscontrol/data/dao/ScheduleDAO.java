package com.lessonscontrol.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.lessonscontrol.data.entities.Schedule;

import java.util.List;

/**
 * DAO for {@link Schedule} objects.
 *
 * @author hblonski
 */
@Dao
public interface ScheduleDAO {

    @Insert
    void insert(Schedule schedule);

    @Delete
    void delete(Schedule schedule);

    @Query("SELECT * FROM schedule WHERE sch_lessonid = :lessonID ORdER BY sch_day ASC")
    LiveData<List<Schedule>> findScheduleByLesson(long lessonID);
}
