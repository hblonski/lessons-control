package com.lessonscontrol.activities.dataHandler.impl

import android.app.Activity
import android.arch.lifecycle.Observer
import android.view.View
import android.widget.TextView
import com.lessonscontrol.activities.R
import com.lessonscontrol.activities.ViewStudentActivity
import com.lessonscontrol.activities.dataHandler.ActivityDataHandler

class ViewStudentActivityDataHandler(override val activity: Activity) : ActivityDataHandler {

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

    override fun populateFields() {
        (viewStudentActivity.findViewById<View>(R.id.view_name) as TextView).text = viewStudentActivity.student.name
        (viewStudentActivity.findViewById<View>(R.id.view_phone) as TextView).text = viewStudentActivity.student.phone
        (viewStudentActivity.findViewById<View>(R.id.view_mail) as TextView).text = viewStudentActivity.student.email
        (viewStudentActivity.findViewById<View>(R.id.view_address) as TextView).text = viewStudentActivity.student.address
    }
}