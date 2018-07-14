package com.lessonscontrol.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.lessonscontrol.data.AppRoomDatabase;
import com.lessonscontrol.data.dao.ScheduleDAO;
import com.lessonscontrol.data.entities.Schedule;

/**
 * {@link Schedule} repository.
 *
 * @author hblonski
 */
public class ScheduleRepository {

    private ScheduleDAO scheduleDAO;

    public ScheduleRepository(Application application) {
        AppRoomDatabase appRoomDatabase = AppRoomDatabase.getInstance(application);
        scheduleDAO = appRoomDatabase.scheduleDAO();
    }

    public void insert(Schedule schedule) {
        new InsertAsyncTask(scheduleDAO).execute(schedule);
    }

    private static class InsertAsyncTask extends AsyncTask<Schedule, Void, Void> {

        private ScheduleDAO scheduleDAO;

        InsertAsyncTask(ScheduleDAO scheduleDAO) {
            this.scheduleDAO = scheduleDAO;
        }

        @Override
        protected Void doInBackground(final Schedule... schedules) {
            scheduleDAO.insert(schedules[0]);
            return null;
        }
    }
}
