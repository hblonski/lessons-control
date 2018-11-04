package com.lessonscontrol.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.lessonscontrol.data.dao.LessonDAO;
import com.lessonscontrol.data.dao.StudentDAO;
import com.lessonscontrol.data.entities.Lesson;
import com.lessonscontrol.data.entities.Student;

/**
 * This app's Room Database.
 *
 * @author hblonski
 */
@Database(entities = {Student.class, Lesson.class}, version = 2)
public abstract class AppRoomDatabase extends RoomDatabase {

    public abstract StudentDAO studentDAO();

    public abstract LessonDAO lessonDAO();

    private static AppRoomDatabase studentRoomDatabaseInstance;

    public static AppRoomDatabase getInstance(final Context context) {
        if (studentRoomDatabaseInstance == null) {
            synchronized (AppRoomDatabase.class) {
                if (studentRoomDatabaseInstance == null) {
                    studentRoomDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class,
                            "student_database")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return studentRoomDatabaseInstance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE schedule");
            database.execSQL("ALTER TABLE lesson ADD COLUMN les_nextdate INTEGER");
        }
    };
}
