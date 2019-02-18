package com.lessonscontrol.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.lessonscontrol.activities.dataHandler.ActivityDataHandlerFactory
import com.lessonscontrol.activities.dataHandler.impl.MainActivityDataHandler
import com.lessonscontrol.adapter.StudentListAdapter
import com.lessonscontrol.data.viewModel.StudentViewModel

/**
 * Main activity.
 *
 * @author hblonski
 */
class MainActivity : AppCompatActivity() {

    lateinit var studentViewModel: StudentViewModel
        private set

    lateinit var studentListAdapter: StudentListAdapter
        private set

    private val dataHandler = ActivityDataHandlerFactory.createDataHandler(this) as MainActivityDataHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)

        val newStudentFAB = findViewById<FloatingActionButton>(R.id.new_student_fab)

        //Sets the FAB to open the new student activity (form screen).
        newStudentFAB.setOnClickListener {
            val newStudentActivityIntent = Intent(this@MainActivity, EditStudentActivity::class.java)
            this@MainActivity.startActivityForResult(newStudentActivityIntent, EditStudentActivity.EDIT_STUDENT_ACTIVITY_REQUEST_CODE)
        }

        studentListAdapter = StudentListAdapter(this)
        val recyclerView = findViewById<RecyclerView>(R.id.students_recycler_view)
        recyclerView.adapter = studentListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        dataHandler.observeDataChange()
    }
}
