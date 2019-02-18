package com.lessonscontrol.activities.dataHandler.impl

import android.app.Activity
import android.arch.lifecycle.Observer
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import com.lessonscontrol.activities.MainActivity
import com.lessonscontrol.activities.R
import com.lessonscontrol.activities.dataHandler.ActivityDataHandler

class MainActivityDataHandler (override val activity: Activity) : ActivityDataHandler {

    private val mainActivity = activity as MainActivity

    override fun observeDataChange() {
        mainActivity.studentViewModel.allStudents.observe(mainActivity, Observer { students ->
            mainActivity.studentListAdapter.setStudents(students)
            handleEmptyDataList(students)
        })
    }

    override fun populateFields() {
        //Empty, since the Main Activity shows no info of any specific Parcelable. It only observes
        //the students list.
    }

    private fun handleEmptyDataList(dataList: List<Parcelable>?) {
        val noInfoFound = mainActivity.findViewById<TextView>(R.id.no_info_text_view)
        if (dataList == null || dataList.isEmpty()) {
            noInfoFound.visibility = View.VISIBLE
        } else {
            noInfoFound.visibility = View.GONE
        }
    }
}