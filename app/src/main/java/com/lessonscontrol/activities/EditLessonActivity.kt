package com.lessonscontrol.activities

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.lessonscontrol.activities.dataHandler.ActivityDataHandlerFactory
import com.lessonscontrol.activities.dataHandler.impl.EditLessonActivityDataHandler
import com.lessonscontrol.data.entities.Lesson
import com.lessonscontrol.data.viewModel.LessonViewModel
import com.lessonscontrol.utils.MoneyTextWatcher
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.security.InvalidParameterException

class EditLessonActivity : AppCompatActivity() {

    lateinit var lessonViewModel: LessonViewModel
        private set

    private val dataHandler = ActivityDataHandlerFactory.createDataHandler(this) as EditLessonActivityDataHandler

    var lesson: Lesson? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lesson)

        lessonViewModel = ViewModelProviders.of(this).get(LessonViewModel::class.java)

        val priceInput = findViewById<EditText>(R.id.edit_price)
        priceInput.addTextChangedListener(MoneyTextWatcher(priceInput))

        val receivedLesson = intent.extras.get(Lesson.LESSON_EXTRA_KEY)
        if (receivedLesson != null) {
            lesson = receivedLesson as Lesson
            dataHandler.populateFields()
        }

        configureCalendarListener(findViewById<View>(R.id.edit_next_class) as MaterialCalendarView)
        configureCalendarListener(findViewById<View>(R.id.edit_next_payment) as MaterialCalendarView)

        val doneFAB = findViewById<FloatingActionButton>(R.id.edit_lesson_form_done_fab)
        doneFAB.setOnClickListener {
            val resultIntent = Intent()
            try {
                dataHandler.updateParcelable()
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } catch (e: InvalidParameterException) {
                Toast.makeText(
                        applicationContext,
                        getString(R.string.required_fields_empty),
                        Toast.LENGTH_LONG).show()
                Log.e(this@EditLessonActivity.javaClass.toString(),
                        "Required argument missing when trying to create lesson.")
            }
        }
    }

    private fun configureCalendarListener(materialCalendarView: MaterialCalendarView) {
        val today = CalendarDay.today()
        materialCalendarView.setOnDateChangedListener { cv, calendarDay, _ ->
            if (calendarDay.isBefore(today)) {
                Toast.makeText(
                        applicationContext,
                        R.string.date_must_not_be_in_the_past,
                        Toast.LENGTH_LONG).show()
                cv.clearSelection()
            }
        }
    }

    companion object {

        /**
         * Request code for this activity. This code will be returned when the activity exits.
         * @see android.support.v4.app.FragmentActivity.startActivityForResult
         */
        const val EDIT_LESSON_ACTIVITY_REQUEST_CODE = 3

        const val STUDENT_ID_KEY = "STUDENT_ID"
    }
}
