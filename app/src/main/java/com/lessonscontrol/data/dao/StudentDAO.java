package com.lessonscontrol.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.lessonscontrol.data.entities.Student;

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

    @Update
    void update(Student student);

    @Query("SELECT * FROM student ORDER BY sdt_name ASC")
    LiveData<List<Student>> getAllStudents();
}
