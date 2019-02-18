package com.lessonscontrol.activities.dataHandler.impl

import android.app.Activity
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.EditText
import com.lessonscontrol.activities.EditStudentActivity
import com.lessonscontrol.activities.R
import com.lessonscontrol.activities.dataHandler.ActivityEditDataHandler
import com.lessonscontrol.data.entities.Student
import java.security.InvalidParameterException

class EditStudentActivityDataHandler(override val activity: Activity) : ActivityEditDataHandler {

    private val editStudentActivity = activity as EditStudentActivity

    /**
     * Updates the activity [Student] or, if it is null, creates a new one using UI input data.
     */
    @Throws(InvalidParameterException::class)
    override fun updateParcelable() {
        val name = (editStudentActivity.findViewById<View>(R.id.edit_name) as EditText).text.toString()
        val email = (editStudentActivity.findViewById<View>(R.id.edit_mail) as EditText).text.toString()
        val address = (editStudentActivity.findViewById<View>(R.id.edit_address) as EditText).text.toString()
        val phone = (editStudentActivity.findViewById<View>(R.id.edit_phone) as EditText).text.toString()

        when {
            name.isEmpty() -> {
                (editStudentActivity.findViewById<View>(R.id.input_layout_name) as TextInputLayout).error = editStudentActivity.getString(R.string.required_field)
                throw IllegalArgumentException("Student name is missing")
            }
            editStudentActivity.student != null -> {
                editStudentActivity.student!!.name = name
                editStudentActivity.student!!.address = address
                editStudentActivity.student!!.email = email
                editStudentActivity.student!!.phone = phone
                editStudentActivity.studentViewModel.update(editStudentActivity.student)
            }
            else -> {
                editStudentActivity.student = Student(name, address, email, phone)
                editStudentActivity.studentViewModel.insert(editStudentActivity.student)
            }
        }
    }

    override fun observeDataChange() {
        //Empty, since EditStudentActivity does not have a RecyclerView
    }

    override fun populateFields() {
        (editStudentActivity.findViewById<View>(R.id.edit_name) as EditText).setText(editStudentActivity.student!!.name)
        (editStudentActivity.findViewById<View>(R.id.edit_phone) as EditText).setText(editStudentActivity.student!!.phone)
        (editStudentActivity.findViewById<View>(R.id.edit_mail) as EditText).setText(editStudentActivity.student!!.email)
        (editStudentActivity.findViewById<View>(R.id.edit_address) as EditText).setText(editStudentActivity.student!!.address)
    }
}