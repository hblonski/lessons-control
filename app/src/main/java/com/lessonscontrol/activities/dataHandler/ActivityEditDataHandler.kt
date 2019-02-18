package com.lessonscontrol.activities.dataHandler

import java.security.InvalidParameterException

interface ActivityEditDataHandler: ActivityDataHandler {

    @Throws(InvalidParameterException::class)
    fun updateParcelable()
}