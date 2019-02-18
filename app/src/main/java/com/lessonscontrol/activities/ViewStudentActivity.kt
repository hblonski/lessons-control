package com.lessonscontrol.activities

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageButton
import com.lessonscontrol.activities.dataHandler.ActivityDataHandlerFactory
import com.lessonscontrol.activities.dataHandler.impl.ViewStudentActivityDataHandler
import com.lessonscontrol.adapter.LessonListAdapter
import com.lessonscontrol.data.entities.Student
import com.lessonscontrol.data.viewModel.LessonViewModel
import com.lessonscontrol.data.viewModel.StudentViewModel

class ViewStudentActivity : AppCompatActivity() {

    lateinit var lessonListAdapter: LessonListAdapter
        private set

    lateinit var student: Student
        private set

    lateinit var lessonViewModel: LessonViewModel
        private set

    lateinit var studentViewModel: StudentViewModel
        private set

    private val dataHandler = ActivityDataHandlerFactory.createDataHandler(this) as ViewStudentActivityDataHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_student)

        lessonViewModel = ViewModelProviders.of(this).get(LessonViewModel::class.java)
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)
        student = intent.getParcelableExtra(Student.STUDENT_EXTRA_KEY)

        dataHandler.populateFields()

        val lessonsRecyclerView = findViewById<RecyclerView>(R.id.lessons_recycler_view)
        lessonListAdapter = LessonListAdapter(this)
        lessonsRecyclerView.adapter = lessonListAdapter
        lessonsRecyclerView.layoutManager = LinearLayoutManager(this)
        dataHandler.observeDataChange()

        val editStudentButton = findViewById<ImageButton>(R.id.button_edit_student)
        editStudentButton.setOnClickListener {
            val editStudentActivityIntent = Intent(this@ViewStudentActivity, EditStudentActivity::class.java)
            editStudentActivityIntent.putExtra(Student.STUDENT_EXTRA_KEY, student)
            this@ViewStudentActivity.startActivityForResult(editStudentActivityIntent, EditStudentActivity.EDIT_STUDENT_ACTIVITY_REQUEST_CODE)
        }

        val deleteStudentButton = findViewById<ImageButton>(R.id.button_delete_student)
        deleteStudentButton.setOnClickListener {
            AlertDialog.Builder(this@ViewStudentActivity)
                    .setTitle(resources.getString(R.string.delete_student))
                    .setPositiveButton(android.R.string.yes) { _, _ ->
                        studentViewModel.delete(student)
                        finish()
                    }.setNegativeButton(android.R.string.no, null).show()
        }

        val addLessonButton = findViewById<ImageButton>(R.id.button_add_lesson)
        addLessonButton.setOnClickListener {
            val newLessonIntent = Intent(this@ViewStudentActivity, EditLessonActivity::class.java)
            newLessonIntent.putExtra(EditLessonActivity.STUDENT_ID_KEY, student.id)
            this@ViewStudentActivity.startActivityForResult(newLessonIntent, EditLessonActivity.EDIT_LESSON_ACTIVITY_REQUEST_CODE)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        //Updates the screen if the EditStudentActivity returns success (student updated)
        if (requestCode == EditStudentActivity.EDIT_STUDENT_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            student = data.getParcelableExtra(Student.STUDENT_EXTRA_KEY)
            dataHandler.populateFields()
        }
    }

    companion object {
        /**
         * Request code for this activity. This code will be returned when the activity exits.
         *
         * @see android.support.v4.app.FragmentActivity.startActivityForResult
         */
        const val VIEW_STUDENT_ACTIVITY_REQUEST_CODE = 2
    }
}
