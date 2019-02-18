package com.lessonscontrol.activities.dataHandler

import android.app.Activity

interface ActivityDataHandler {

    val activity: Activity

    fun observeDataChange()

    fun populateFields()
}