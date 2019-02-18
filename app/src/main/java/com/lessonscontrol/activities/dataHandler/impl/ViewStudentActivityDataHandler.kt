package com.lessonscontrol.activities.dataHandler.impl

import android.app.Activity
import android.arch.lifecycle.Observer
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import com.lessonscontrol.activities.R
import com.lessonscontrol.activities.ViewStudentActivity
import com.lessonscontrol.activities.dataHandler.ActivityEditDataHandler
import com.lessonscontrol.data.entities.Student

class ViewStudentActivityDataHandler(override val activity: Activity) : ActivityEditDataHandler {

    private val viewStudentActivity = activity as ViewStudentActivity

    override fun observeDataChange() {
        val student = viewStudentActivity.student
        viewStudentActivity.lessonViewModel.findLessonsByStudent(student).observe(viewStudentActivity, Observer { lessons ->
            val studentLessonDate: Long? = student.nextLessonDate
            var newNextLessonDate: Long? = null
            if (lessons != null && !lessons.isEmpty()) {
                newNextLessonDate = lessons[0].nextDate
            }
            if (studentLessonDate !== newNextLessonDate) {
                student.nextLessonDate = newNextLessonDate
                viewStudentActivity.studentViewModel.update(student)
            }
            viewStudentActivity.lessonListAdapter.lessons = lessons
        })
    }

    override fun populateFields(parcelable: Parcelable) {
        val student = parcelable as Student
        (viewStudentActivity.findViewById<View>(R.id.view_name) as TextView).text = student.name
        (viewStudentActivity.findViewById<View>(R.id.view_phone) as TextView).text = student.phone
        (viewStudentActivity.findViewById<View>(R.id.view_mail) as TextView).text = student.email
        (viewStudentActivity.findViewById<View>(R.id.view_address) as TextView).text = student.address
    }
}