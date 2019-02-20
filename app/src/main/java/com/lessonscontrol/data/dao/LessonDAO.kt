package com.lessonscontrol.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.lessonscontrol.data.entities.Lesson

/**
 * DAO for [Lesson] objects.
 *
 * @author hblonski
 */
@Dao
interface LessonDAO {

    @Insert
    fun insert(lesson: Lesson)

    @Delete
    fun delete(lesson: Lesson)

    @Update
    fun update(lesson: Lesson)

    @Query("SELECT * FROM lesson WHERE les_student = :studentID ORDER BY les_nextdate ASC")
    fun findLessonsByStudent(studentID: Long): LiveData<List<Lesson>>
}
