package com.lessonscontrol.activities.dataHandler

import android.app.Activity
import android.os.Parcelable

interface ActivityDataHandler {

    val activity: Activity

    fun observeDataChange()

    fun populateFields(parcelable: Parcelable)
}