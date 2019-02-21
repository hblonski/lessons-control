package com.lessonscontrol.data.dao

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.lessonscontrol.data.AppRoomDatabase
import com.lessonscontrol.data.entities.Student
import com.lessonscontrol.utils.TestUtils
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LessonDAOTest {

    private lateinit var appRoomDatabase: AppRoomDatabase

    private lateinit var student: Student

    @Before
    fun setUp() {
        appRoomDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppRoomDatabase::class.java).build()

        val mockedStudent = TestUtils.mockStudent()
        appRoomDatabase.studentDAO().insert(mockedStudent)

        val allStudents = TestUtils.getValue(appRoomDatabase.studentDAO().allStudents)
        assertFalse(allStudents.isEmpty())
        student = allStudents[0]
    }

    @After
    fun tearDown() {
        appRoomDatabase.close()
    }

    @Test
    fun shouldInsertLesson() {
        val lesson = TestUtils.mockLesson(student.id)
        appRoomDatabase.lessonDAO().insert(lesson)
        val allStudentLessons = TestUtils.getValue(appRoomDatabase.lessonDAO().findLessonsByStudent(student.id))
        assertFalse(allStudentLessons.isEmpty())
        val inserted = allStudentLessons[0]
        assertEquals(student.id, inserted.id)
    }

    @Test
    fun shouldDeleteLesson() {
        var lesson = TestUtils.mockLesson(student.id)
        appRoomDatabase.lessonDAO().insert(lesson)
        var allStudentLessons = TestUtils.getValue(appRoomDatabase.lessonDAO().findLessonsByStudent(student.id))
        assertFalse(allStudentLessons.isEmpty())
        lesson = allStudentLessons[0]
        appRoomDatabase.lessonDAO().delete(lesson)
        allStudentLessons = TestUtils.getValue(appRoomDatabase.lessonDAO().findLessonsByStudent(student.id))
        assertTrue(allStudentLessons.isEmpty())
    }

    @Test
    fun shouldUpdateLesson() {
        var lesson = TestUtils.mockLesson(student.id)
        appRoomDatabase.lessonDAO().insert(lesson)

        var allStudentLessons = TestUtils.getValue(appRoomDatabase.lessonDAO().findLessonsByStudent(student.id))
        lesson = allStudentLessons[0]

        lesson.type = "updated"
        appRoomDatabase.lessonDAO().update(lesson)

        allStudentLessons = TestUtils.getValue(appRoomDatabase.lessonDAO().findLessonsByStudent(student.id))
        assertEquals("updated", allStudentLessons[0].type)
    }

    @Test
    fun findLessonsByStudent() {
        var lesson = TestUtils.mockLesson(student.id)
        appRoomDatabase.lessonDAO().insert(lesson)
        val allStudentLessons = TestUtils.getValue(appRoomDatabase.lessonDAO().findLessonsByStudent(student.id))
        assertEquals(1, allStudentLessons.size)
    }
}