package com.lessonscontrol.activities.dataHandler.impl

import android.app.Activity
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.EditText
import android.widget.TextView
import ca.antonious.materialdaypicker.MaterialDayPicker
import com.lessonscontrol.activities.EditLessonActivity
import com.lessonscontrol.activities.R
import com.lessonscontrol.activities.dataHandler.ActivityEditDataHandler
import com.lessonscontrol.data.entities.Lesson
import com.lessonscontrol.utils.FormatUtil
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.security.InvalidParameterException
import java.util.*

class EditLessonActivityDataHandler(override val activity: Activity) : ActivityEditDataHandler {

    private val editLessonActivity = activity as EditLessonActivity

    @Throws(InvalidParameterException::class)
    override fun updateParcelable() {
        val type = (editLessonActivity.findViewById<View>(R.id.edit_type) as EditText).text.toString()
        val materialDayPicker = editLessonActivity.findViewById<MaterialDayPicker>(R.id.edit_days)
        val daysSelected = materialDayPicker.selectedDays

        if (type == null || type.isEmpty()) {
            val typeTextInputLayout = editLessonActivity.findViewById<View>(R.id.input_layout_type) as TextInputLayout
            typeTextInputLayout.error = editLessonActivity.getString(R.string.required_field)
            throw InvalidParameterException("Lesson type is missing.")
        }
        if (daysSelected == null || daysSelected.isEmpty()) {
            val editDaysTextView = editLessonActivity.findViewById<View>(R.id.edit_days_label) as TextView
            editDaysTextView.error = editLessonActivity.getString(R.string.required_field)
            throw InvalidParameterException("Lesson schedule is missing.")
        }

        val value = (editLessonActivity.findViewById<View>(R.id.edit_price) as EditText)
                .text
                .toString()
                .replace("[^\\d,]".toRegex(), "")
                .replace(",".toRegex(), ".")
        if (value == null || value.isEmpty()) {
            val priceInputLayout = editLessonActivity.findViewById<View>(R.id.input_layout_price) as TextInputLayout
            priceInputLayout.error = editLessonActivity.getString(R.string.required_field)
            throw InvalidParameterException("Lesson price is missing.")
        }

        val price = java.lang.Double.valueOf(value)
        val daysOfWeek = FormatUtil.convertMaterialDayPickerListToString(daysSelected)
        val studentId = editLessonActivity.intent.extras.getLong(EditLessonActivity.STUDENT_ID_KEY)

        val nextClassMaterialCalendarView = editLessonActivity.findViewById<View>(R.id.edit_next_class) as MaterialCalendarView
        val nextClassSelectedDate = nextClassMaterialCalendarView.selectedDate
        if (nextClassSelectedDate == null) {
            val nextClassLabelTextView = editLessonActivity.findViewById<View>(R.id.label_next_class) as TextView
            nextClassLabelTextView.error = editLessonActivity.getString(R.string.required_field)
            throw InvalidParameterException("Lesson date is missing.")
        }
        val nextClassDateAsMillis = nextClassSelectedDate.date.time

        val nextPaymentMaterialCalendarView = editLessonActivity.findViewById<View>(R.id.edit_next_payment) as MaterialCalendarView
        val nextPaymentSelectedDate = nextPaymentMaterialCalendarView.selectedDate
        if (nextPaymentSelectedDate == null) {
            val nextPaymentLabelTextView = editLessonActivity.findViewById<View>(R.id.label_nextPayment) as TextView
            nextPaymentLabelTextView.error = editLessonActivity.getString(R.string.required_field)
            throw InvalidParameterException("Lesson payment date is missing.")
        }
        val nextPaymentDateAsMillis = nextPaymentSelectedDate.date.time

        if (editLessonActivity.lesson == null) {
            editLessonActivity.lesson = Lesson(studentId, daysOfWeek, price, type, nextPaymentDateAsMillis, nextClassDateAsMillis)
            editLessonActivity.lessonViewModel.insert(editLessonActivity.lesson)
        } else {
            editLessonActivity.lesson!!.days = daysOfWeek
            editLessonActivity.lesson!!.price = price
            editLessonActivity.lesson!!.type = type
            editLessonActivity.lesson!!.nextDate = nextClassDateAsMillis
            editLessonActivity.lesson!!.nextPayment = nextPaymentDateAsMillis
            editLessonActivity.lessonViewModel.update(editLessonActivity.lesson)
        }
    }

    override fun observeDataChange() {
        //Empty, since EditLessonActivity does not have a RecyclerView
    }

    override fun populateFields() {
        (editLessonActivity.findViewById<View>(R.id.edit_type) as EditText).setText(editLessonActivity.lesson!!.type)

        val price = String.format("$%s", FormatUtil.convertDoubleToMoney(editLessonActivity.lesson!!.price))
        (editLessonActivity.findViewById<View>(R.id.edit_price) as EditText).setText(price)

        val materialDayPicker = editLessonActivity.findViewById<MaterialDayPicker>(R.id.edit_days)
        materialDayPicker.selectedDays = FormatUtil.convertStringToMaterialDayPickerList(editLessonActivity.lesson!!.days)

        val nextClassDate = editLessonActivity.lesson!!.nextDate
        if (nextClassDate != null) {
            (editLessonActivity.findViewById<View>(R.id.edit_next_class) as MaterialCalendarView).setSelectedDate(Date(nextClassDate))
        }

        val nextPaymentDate = editLessonActivity.lesson!!.nextPayment
        if (nextPaymentDate != null) {
            (editLessonActivity.findViewById<View>(R.id.edit_next_payment) as MaterialCalendarView).setSelectedDate(Date())
        }
    }
}