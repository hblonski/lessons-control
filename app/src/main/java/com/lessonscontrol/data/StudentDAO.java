package com.lessonscontrol.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * DAO for {@link Student} objects.
 *
 * @author hblonski
 */
@Dao
public interface StudentDAO {

    @Insert
    void insert(Student student);

    @Delete
    void delete(Student student);

    @Query("SELECT * FROM student ORDER BY sdt_name ASC")
    List<Student> getAllStudents();
}
