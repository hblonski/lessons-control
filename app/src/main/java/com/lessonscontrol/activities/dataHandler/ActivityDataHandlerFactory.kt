package com.lessonscontrol.activities.dataHandler

import android.app.Activity
import com.lessonscontrol.activities.EditLessonActivity
import com.lessonscontrol.activities.EditStudentActivity
import com.lessonscontrol.activities.MainActivity
import com.lessonscontrol.activities.ViewStudentActivity
import com.lessonscontrol.activities.dataHandler.impl.EditLessonActivityDataHandler
import com.lessonscontrol.activities.dataHandler.impl.EditStudentActivityDataHandler
import com.lessonscontrol.activities.dataHandler.impl.MainActivityDataHandler
import com.lessonscontrol.activities.dataHandler.impl.ViewStudentActivityDataHandler
import java.security.InvalidParameterException

object ActivityDataHandlerFactory {
    fun createDataHandler(activity: Activity) : ActivityDataHandler {
        return when (activity) {
            is MainActivity -> MainActivityDataHandler(activity)
            is ViewStudentActivity -> ViewStudentActivityDataHandler(activity)
            is EditStudentActivity -> EditStudentActivityDataHandler(activity)
            is EditLessonActivity -> EditLessonActivityDataHandler(activity)
            else -> throw InvalidParameterException("Activity does not have an implemented Data Handler.")
        }
    }
}