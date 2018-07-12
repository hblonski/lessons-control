package com.lessonscontrol.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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
    List<Schedule> findScheduleByLesson(long lessonID);
}
