package com.lessonscontrol.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.lessonscontrol.data.entities.Student

/**
 * DAO for [Student] objects.
 *
 * @author hblonski
 */
@Dao
interface StudentDAO {

    @get:Query("SELECT * FROM student ORDER BY sdt_name DESC")
    val allStudents: LiveData<List<Student>>

    @Insert
    fun insert(student: Student)

    @Delete
    fun delete(student: Student)

    @Update
    fun update(student: Student)
}
