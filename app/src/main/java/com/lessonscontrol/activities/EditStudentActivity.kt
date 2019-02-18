package com.lessonscontrol.activities

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.lessonscontrol.activities.dataHandler.ActivityDataHandlerFactory
import com.lessonscontrol.activities.dataHandler.impl.EditStudentActivityDataHandler
import com.lessonscontrol.data.entities.Student
import com.lessonscontrol.data.viewModel.StudentViewModel

/**
 * New student form activity.
 * @author hblonski
 */
class EditStudentActivity : AppCompatActivity() {

    lateinit var studentViewModel: StudentViewModel
        private set

    private val dataHandler = ActivityDataHandlerFactory.createDataHandler(this) as EditStudentActivityDataHandler

    var student: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)

        student = intent.getParcelableExtra(Student.STUDENT_EXTRA_KEY)

        if (student != null) {
            dataHandler.populateFields()
        }

        val doneFAB = findViewById<FloatingActionButton>(R.id.edit_student_form_done_fab)
        doneFAB.setOnClickListener {
            val resultIntent = Intent()

            try {
                dataHandler.updateParcelable()
                resultIntent.putExtra(Student.STUDENT_EXTRA_KEY, student)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } catch (e: IllegalArgumentException) {
                Toast.makeText(
                        applicationContext,
                        getString(R.string.required_fields_empty),
                        Toast.LENGTH_LONG).show()
                Log.e(this@EditStudentActivity.javaClass.toString(),
                        "Required argument missing when trying to create student.")
            }
        }
    }

    companion object {

        /**
         * Request code for this activity. This code will be returned when the activity exits.
         * @see android.support.v4.app.FragmentActivity.startActivityForResult
         */
        const val EDIT_STUDENT_ACTIVITY_REQUEST_CODE = 1
    }
}
