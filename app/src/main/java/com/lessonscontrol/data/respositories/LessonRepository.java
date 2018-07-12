package com.lessonscontrol.data.respositories;

import android.app.Application;
import android.os.AsyncTask;

import com.lessonscontrol.data.AppRoomDatabase;
import com.lessonscontrol.data.dao.LessonDAO;
import com.lessonscontrol.data.entities.Lesson;

/**
 * {@link Lesson} repository.
 *
 * @author hblonski
 */
public class LessonRepository {

    private LessonDAO lessonDAO;

    public LessonRepository(Application application) {
        AppRoomDatabase appRoomDatabase = AppRoomDatabase.getInstance(application);
        lessonDAO = appRoomDatabase.lessonDAO();
    }

    public void insert(Lesson lesson) {
        new InsertAsyncTask(lessonDAO).execute(lesson);
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
