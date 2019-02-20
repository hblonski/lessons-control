package com.lessonscontrol.data

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context

import com.lessonscontrol.data.dao.LessonDAO
import com.lessonscontrol.data.dao.StudentDAO
import com.lessonscontrol.data.entities.Lesson
import com.lessonscontrol.data.entities.Student

/**
 * This app's Room Database.
 *
 * @author hblonski
 */
@Database(entities = [Student::class, Lesson::class], version = 2)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun studentDAO(): StudentDAO

    abstract fun lessonDAO(): LessonDAO

    companion object {

        private var studentRoomDatabaseInstance: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase? {
            if (studentRoomDatabaseInstance == null) {
                synchronized(AppRoomDatabase::class.java) {
                    if (studentRoomDatabaseInstance == null) {
                        studentRoomDatabaseInstance = Room.databaseBuilder(context.applicationContext,
                                AppRoomDatabase::class.java,
                                "student_database")
                                .addMigrations(MIGRATION_1_2)
                                .build()
                    }
                }
            }
            return studentRoomDatabaseInstance
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE schedule")
                database.execSQL("ALTER TABLE lesson ADD COLUMN les_nextdate INTEGER")
            }
        }
    }
}
