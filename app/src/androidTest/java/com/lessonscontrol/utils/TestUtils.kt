package com.lessonscontrol.utils

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.lessonscontrol.data.entities.Lesson
import com.lessonscontrol.data.entities.Student
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

abstract class TestUtils {

    companion object {
        // Copied from stackoverflow
        @Throws(InterruptedException::class)
        fun <T> getValue(liveData: LiveData<T>): T {
            val data = arrayOfNulls<Any>(1)
            val latch = CountDownLatch(1)
            val observer = object : Observer<T> {
                override fun onChanged(t: T?) {
                    data[0] = t
                    latch.countDown()
                    liveData.removeObserver(this)//To change body of created functions use File | Settings | File Templates.
                }
            }
            liveData.observeForever(observer)
            latch.await(2, TimeUnit.SECONDS)

            return data[0] as T
        }

        const val STUDENT_TEST_NAME = "test_name"
        const val STUDENT_TEST_ADDRESS = "test_address"
        const val STUDENT_TEST_EMAIL = "test@test.com"
        const val STUDENT_TEST_PHONE = "12345"

        fun mockStudent(): Student {
            return Student(STUDENT_TEST_NAME,
                    STUDENT_TEST_ADDRESS,
                    STUDENT_TEST_EMAIL,
                    STUDENT_TEST_PHONE)
        }


        const val LESSON_TEST_DAYS = "MON_TUE"
        const val LESSON_TEST_PRICE = 25.0
        const val LESSON_TEST_TYPE = "test_type"
        const val LESSON_NEXT_PAYMENT: Long = 123
        const val LESSON_NEXT_DATE: Long = 123

        fun mockLesson(studentId: Long): Lesson {
            return Lesson(studentId,
                    LESSON_TEST_DAYS,
                    LESSON_TEST_PRICE,
                    LESSON_TEST_TYPE,
                    LESSON_NEXT_PAYMENT,
                    LESSON_NEXT_DATE)
        }
    }
}