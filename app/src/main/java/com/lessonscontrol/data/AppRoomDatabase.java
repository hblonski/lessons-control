package com.lessonscontrol.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.lessonscontrol.data.dao.LessonDAO;
import com.lessonscontrol.data.dao.ScheduleDAO;
import com.lessonscontrol.data.dao.StudentDAO;
import com.lessonscontrol.data.entities.Lesson;
import com.lessonscontrol.data.entities.Schedule;
import com.lessonscontrol.data.entities.Student;

/**
 * This app's Room Database.
 *
 * @author hblonski
 */
@Database(entities = {Student.class, Lesson.class, Schedule.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase {

    public abstract StudentDAO studentDAO();

    public abstract LessonDAO lessonDAO();

    public abstract ScheduleDAO scheduleDAO();

    private static AppRoomDatabase studentRoomDatabaseInstance;

    public static AppRoomDatabase getInstance(final Context context) {
        if (studentRoomDatabaseInstance == null) {
            synchronized (AppRoomDatabase.class) {
                if (studentRoomDatabaseInstance == null) {
                    studentRoomDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class,
                            "student_database").build();
                }
            }
        }
        return studentRoomDatabaseInstance;
    }
}
