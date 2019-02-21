package com.lessonscontrol.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lessonscontrol.activities.MainActivity
import com.lessonscontrol.activities.R
import com.lessonscontrol.activities.ViewStudentActivity
import com.lessonscontrol.data.entities.Student
import com.lessonscontrol.utils.FormatUtil

/**
 * Adapter for a [Student] list.
 *
 * @author hblonski
 */
class StudentListAdapter(context: Context) : RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    private var students: List<Student>? = null

    inner class StudentViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.student_name)
        val nextClassView: TextView = itemView.findViewById(R.id.student_nextclass)
        val firstLetter: TextView = itemView.findViewById(R.id.first_letter_icon_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = layoutInflater.inflate(R.layout.student_summary_card, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(studentViewHolder: StudentViewHolder, position: Int) {
        if (this.students != null) {
            val current = this.students!![position]
            studentViewHolder.nameView.text = current.name
            studentViewHolder.firstLetter.text = current.name.toUpperCase()[0].toString()

            val nextLesson = current.nextLessonDate
            val resources = layoutInflater.context.resources
            val nextClassLabel = if (nextLesson != null) FormatUtil.convertDateToString(nextLesson) else resources.getString(R.string.no_date_selected_lowercase)
            studentViewHolder.nextClassView.text = String.format("%s: %s", resources.getString(R.string.next_class), nextClassLabel)

            //Opens the ViewStudentActivity on click.
            studentViewHolder.itemView.setOnClickListener { v ->
                val mainActivity = v.context as MainActivity
                val viewStudentIntent = Intent(mainActivity, ViewStudentActivity::class.java)
                viewStudentIntent.putExtra(Student.STUDENT_EXTRA_KEY, current)
                mainActivity.startActivityForResult(viewStudentIntent, ViewStudentActivity.VIEW_STUDENT_ACTIVITY_REQUEST_CODE)
            }

        } else {
            // Covers the case of data not being ready yet.
            studentViewHolder.nameView.text = "Data not ready."
        }
    }

    fun setStudents(students: List<Student>) {
        this.students = students
        notifyDataSetChanged()
    }

    // getItemCount() is called many times, and when it is first called,
    // students has not been updated (means initially, it's null, and we can't return null).
    override fun getItemCount(): Int {
        return if (this.students != null) this.students!!.size else 0
    }
}
