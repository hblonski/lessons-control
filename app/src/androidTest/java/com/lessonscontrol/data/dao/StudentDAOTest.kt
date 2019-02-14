package com.lessonscontrol.data.dao

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.lessonscontrol.data.AppRoomDatabase
import com.lessonscontrol.utils.TestUtils
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StudentDAOTest {

    private lateinit var appRoomDatabase: AppRoomDatabase

    @Before
    fun initDB() {
        appRoomDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppRoomDatabase::class.java).build()
    }

    @After
    fun closeDB() {
        appRoomDatabase.close()
    }

    @Test
    fun should_insertStudent() {
        val student = TestUtils.mockStudent()
        appRoomDatabase.studentDAO().insert(student)

        val allStudents = TestUtils.getValue(appRoomDatabase.studentDAO().allStudents)
        assertFalse(allStudents.isEmpty())
        val inserted = allStudents[0]
        assertEquals(TestUtils.STUDENT_TEST_NAME, inserted.name)
        assertEquals(TestUtils.STUDENT_TEST_ADDRESS, inserted.address)
        assertEquals(TestUtils.STUDENT_TEST_EMAIL, inserted.email)
        assertEquals(TestUtils.STUDENT_TEST_PHONE, inserted.phone)
    }

    @Test
    fun should_deleteStudent() {
        val student = TestUtils.mockStudent()
        appRoomDatabase.studentDAO().insert(student)

        var allStudents = TestUtils.getValue(appRoomDatabase.studentDAO().allStudents)
        assertFalse(allStudents.isEmpty())
        val inserted = allStudents[0]
        appRoomDatabase.studentDAO().delete(inserted)
        allStudents = TestUtils.getValue(appRoomDatabase.studentDAO().allStudents)
        assertTrue(allStudents.isEmpty())
    }

    @Test
    fun should_updateStudent() {
        var student = TestUtils.mockStudent()
        appRoomDatabase.studentDAO().insert(student)

        var allStudents = TestUtils.getValue(appRoomDatabase.studentDAO().allStudents)
        student = allStudents[0]

        student.name = "updated"
        appRoomDatabase.studentDAO().update(student)

        allStudents = TestUtils.getValue(appRoomDatabase.studentDAO().allStudents)
        assertEquals("updated", allStudents[0].name)
    }

    @Test
    fun should_getAllStudents() {
        assertNotNull(appRoomDatabase.studentDAO().allStudents)

        val student = TestUtils.mockStudent()
        appRoomDatabase.studentDAO().insert(student)
        appRoomDatabase.studentDAO().insert(student)

        var allStudents = TestUtils.getValue(appRoomDatabase.studentDAO().allStudents)
        assertEquals(2, allStudents.size)
    }
}