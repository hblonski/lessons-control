package com.lessonscontrol.data.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

import com.lessonscontrol.data.AppRoomDatabase
import com.lessonscontrol.data.dao.LessonDAO
import com.lessonscontrol.data.entities.Lesson
import com.lessonscontrol.data.entities.Student

/**
 * [Lesson] repository.
 *
 * @author hblonski
 */
class LessonRepository(application: Application) {

    private val lessonDAO: LessonDAO

    private var lessons: LiveData<List<Lesson>>? = null

    init {
        val appRoomDatabase = AppRoomDatabase.getInstance(application)
        lessonDAO = appRoomDatabase!!.lessonDAO()
    }

    fun insert(lesson: Lesson) {
        InsertAsyncTask(lessonDAO).execute(lesson)
    }

    fun update(lesson: Lesson) {
        UpdateAsyncTask(lessonDAO).execute(lesson)
    }

    fun delete(lesson: Lesson) {
        DeleteAsyncTask(lessonDAO).execute(lesson)
    }

    fun findLessonsByStudent(student: Student): LiveData<List<Lesson>> {
        val results = lessonDAO.findLessonsByStudent(student.id)
        lessons = results
        return results
    }

    private class InsertAsyncTask internal constructor(private val lessonDAO: LessonDAO) : AsyncTask<Lesson, Void, Void>() {

        override fun doInBackground(vararg lessons: Lesson): Void? {
            lessonDAO.insert(lessons[0])
            return null
        }
    }

    private class UpdateAsyncTask internal constructor(private val lessonDAO: LessonDAO) : AsyncTask<Lesson, Void, Void>() {

        override fun doInBackground(vararg lessons: Lesson): Void? {
            lessonDAO.update(lessons[0])
            return null
        }
    }

    private class DeleteAsyncTask internal constructor(private val lessonDAO: LessonDAO) : AsyncTask<Lesson, Void, Void>() {

        override fun doInBackground(vararg lessons: Lesson): Void? {
            lessonDAO.delete(lessons[0])
            return null
        }
    }
}
