package com.lessonscontrol.data.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.lessonscontrol.data.AppRoomDatabase;
import com.lessonscontrol.data.dao.StudentDAO;
import com.lessonscontrol.data.entities.Student;

import java.util.List;

/**
 * {@link Student} repository.
 *
 * @author hblonski
 */
public class StudentRepository {

    private StudentDAO studentDAO;

    private LiveData<List<Student>> allStudents;

    public StudentRepository(Application application) {
        AppRoomDatabase appRoomDatabase = AppRoomDatabase.getInstance(application);
        studentDAO = appRoomDatabase.studentDAO();
        allStudents = studentDAO.getAllStudents();
    }

    public LiveData<List<Student>> getAllStudents() {
        return this.allStudents;
    }

    public void insert(Student student) {
        new InsertAsyncTask(studentDAO).execute(student);
    }

    private static class InsertAsyncTask extends AsyncTask<Student, Void, Void> {

        private StudentDAO studentDAO;

        InsertAsyncTask(StudentDAO studentDAO) {
            this.studentDAO = studentDAO;
        }

        @Override
        protected Void doInBackground(final Student... students) {
            studentDAO.insert(students[0]);
            return null;
        }
    }
}
