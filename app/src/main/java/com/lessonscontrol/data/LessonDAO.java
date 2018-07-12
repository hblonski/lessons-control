package com.lessonscontrol.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * DAO for {@link Lesson} objects.
 *
 * @author hblonski
 */
@Dao
public interface LessonDAO {

    @Insert
    void insert(Lesson lesson);

    @Delete
    void delete(Lesson lesson);

    @Query("SELECT * FROM lesson WHERE les_id = :studentID ORDER BY les_type ASC")
    List<Lesson> findLessonsByStudent(long studentID);

}
