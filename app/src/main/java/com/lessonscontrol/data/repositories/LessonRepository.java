package com.lessonscontrol.data.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.lessonscontrol.data.AppRoomDatabase;
import com.lessonscontrol.data.dao.LessonDAO;
import com.lessonscontrol.data.dao.StudentDAO;
import com.lessonscontrol.data.entities.Lesson;
import com.lessonscontrol.data.entities.Student;

import java.util.List;

/**
 * {@link Lesson} repository.
 *
 * @author hblonski
 */
public class LessonRepository {

    private LessonDAO lessonDAO;

    private LiveData<List<Lesson>> lessons;

    public LessonRepository(Application application) {
        AppRoomDatabase appRoomDatabase = AppRoomDatabase.getInstance(application);
        lessonDAO = appRoomDatabase.lessonDAO();
    }

    public void insert(Lesson lesson) {
        new InsertAsyncTask(lessonDAO).execute(lesson);
    }

    public LiveData<List<Lesson>> findLessonsByStudent(Student student) {
        lessons = lessonDAO.findLessonsByStudent(student.getID());
        return lessons;
    }

    private static class InsertAsyncTask extends AsyncTask<Lesson, Void, Void> {

        private LessonDAO lessonDAO;

        InsertAsyncTask(LessonDAO lessonDAO) {
            this.lessonDAO = lessonDAO;
        }

        @Override
        protected Void doInBackground(final Lesson... lessons) {
            lessonDAO.insert(lessons[0]);
            return null;
        }
    }
}
