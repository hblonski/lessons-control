package com.lessonscontrol.data.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

import com.lessonscontrol.data.AppRoomDatabase
import com.lessonscontrol.data.dao.StudentDAO
import com.lessonscontrol.data.entities.Student

/**
 * [Student] repository.
 *
 * @author hblonski
 */
class StudentRepository(application: Application) {

    private val studentDAO: StudentDAO

    val allStudents: LiveData<List<Student>>

    init {
        val appRoomDatabase = AppRoomDatabase.getInstance(application)
        studentDAO = appRoomDatabase!!.studentDAO()
        allStudents = studentDAO.allStudents
    }

    fun insert(student: Student) {
        InsertAsyncTask(studentDAO).execute(student)
    }

    fun delete(student: Student) {
        DeleteAsyncTask(studentDAO).execute(student)
    }

    fun update(student: Student) {
        UpdateAsyncTask(studentDAO).execute(student)
    }

    private class InsertAsyncTask internal constructor(private val studentDAO: StudentDAO) : AsyncTask<Student, Void, Void>() {

        override fun doInBackground(vararg students: Student): Void? {
            studentDAO.insert(students[0])
            return null
        }
    }

    private class UpdateAsyncTask internal constructor(private val studentDAO: StudentDAO) : AsyncTask<Student, Void, Void>() {

        override fun doInBackground(vararg students: Student): Void? {
            studentDAO.update(students[0])
            return null
        }
    }

    private class DeleteAsyncTask internal constructor(private val studentDAO: StudentDAO) : AsyncTask<Student, Void, Void>() {

        override fun doInBackground(vararg students: Student): Void? {
            studentDAO.delete(students[0])
            return null
        }
    }
}
