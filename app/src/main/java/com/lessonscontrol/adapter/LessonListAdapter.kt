package com.lessonscontrol.adapter

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.lessonscontrol.activities.EditLessonActivity
import com.lessonscontrol.activities.R
import com.lessonscontrol.data.entities.Lesson
import com.lessonscontrol.data.viewModel.LessonViewModel
import com.lessonscontrol.utils.FormatUtil

/**
 * Adapter for a [Lesson] list.
 *
 * @author hblonski
 */
class LessonListAdapter(context: Context) : RecyclerView.Adapter<LessonListAdapter.LessonViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    var lessons: List<Lesson>? = null
        set(lessons) {
            field = lessons
            notifyDataSetChanged()
        }

    inner class LessonViewHolder (val lessonItemView: View) : RecyclerView.ViewHolder(lessonItemView) {
        val typeView: TextView = lessonItemView.findViewById(R.id.view_type)
        val daysView: TextView = lessonItemView.findViewById(R.id.view_days)
        val priceView: TextView = lessonItemView.findViewById(R.id.view_price)
        val nextClassView: TextView = lessonItemView.findViewById(R.id.view_next_class)
        val nextPaymentView: TextView = lessonItemView.findViewById(R.id.view_next_payment)

        init {
            //When the card is created, it is expanded, so we call this method to collapse it.
            expandOrCollapseCard()
            lessonItemView.setOnClickListener { expandOrCollapseCard() }
        }

        /**
         * Switches the visibility of some card components, expanding it if it is collapsed, or
         * collapsing if it is expanded.
         */
        private fun expandOrCollapseCard() {
            switchVisibility(daysView)
            switchVisibility(nextPaymentView)
            switchVisibility(priceView)
            val lastSeparator = lessonItemView.findViewById<View>(R.id.view_next_class_separator)
            switchVisibility(lastSeparator)
        }

        /**
         * Switches the field's visibility between visible and invisible. It will do the same for
         * the field label/separator, as seen on the lesson_details_card layout.
         * @param view the view.
         */
        private fun switchVisibility(view: View) {
            //Field label
            val label = getViewByIdName(lessonItemView.resources.getResourceName(view.id) + "_label") as TextView?
            //Field separator
            val separator = getViewByIdName(lessonItemView.resources.getResourceName(view.id) + "_separator")

            if (view.visibility == View.GONE) {
                view.visibility = View.VISIBLE

                if (label != null) {
                    label.visibility = View.VISIBLE
                }
                if (separator != null) {
                    separator.visibility = View.VISIBLE
                }
            } else {
                view.visibility = View.GONE
                if (label != null) {
                    label.visibility = View.GONE
                }
                if (separator != null) {
                    separator.visibility = View.GONE
                }
            }
        }

        private fun getViewByIdName(idName: String): View? {
            return lessonItemView.findViewById(lessonItemView.resources.getIdentifier(idName, "id", layoutInflater.context.packageName))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val itemView = layoutInflater.inflate(R.layout.lesson_details_card, parent, false)
        return LessonViewHolder(itemView)
    }

    override fun onBindViewHolder(lessonViewHolder: LessonViewHolder, position: Int) {
        if (this.lessons != null) {
            val context = layoutInflater.context as AppCompatActivity
            val resources = context.resources

            val current = this.lessons!![position]
            lessonViewHolder.typeView.text = current.type
            context.resources
            lessonViewHolder.daysView.text = FormatUtil.formatWeekDayIDsForDisplay(current.days, resources, context.packageName)

            val noDateSelected = resources.getString(R.string.no_date_selected)
            val nextClassDate = current.nextDate
            val nextClassDateAsString = if (nextClassDate != null) FormatUtil.convertDateToString(nextClassDate) else noDateSelected
            lessonViewHolder.nextClassView.text = nextClassDateAsString

            val nextPaymentDate = current.nextPayment
            val nextClassPaymentAsString = if (nextPaymentDate != null) FormatUtil.convertDateToString(nextPaymentDate) else noDateSelected
            lessonViewHolder.nextPaymentView.text = nextClassPaymentAsString

            lessonViewHolder.priceView.text = FormatUtil.convertDoubleToMoney(current.price)

            val editButton = lessonViewHolder.lessonItemView.findViewById<ImageButton>(R.id.button_edit_lesson)
            editButton.setOnClickListener {
                val editLessonActivityIntent = Intent(context, EditLessonActivity::class.java)
                editLessonActivityIntent.putExtra(Lesson.LESSON_EXTRA_KEY, current)
                context.startActivityForResult(editLessonActivityIntent, EditLessonActivity.EDIT_LESSON_ACTIVITY_REQUEST_CODE)
            }

            val deleteLessonButton = lessonViewHolder.lessonItemView.findViewById<ImageButton>(R.id.button_delete_lesson)
            deleteLessonButton.setOnClickListener {
                AlertDialog.Builder(context)
                        .setTitle(resources.getString(R.string.delete_lesson))
                        .setPositiveButton(android.R.string.yes) { dialogInterface, i -> ViewModelProviders.of(context).get(LessonViewModel::class.java).delete(current) }.setNegativeButton(android.R.string.no, null).show()
            }
        } else {
            // Covers the case of data not being ready yet.
            lessonViewHolder.typeView.text = "Data not ready."
        }
    }

    // getItemCount() is called many times, and when it is first called,
    // lessons has not been updated (means initially, it's null, and we can't return null).
    override fun getItemCount(): Int {
        return if (this.lessons != null) this.lessons!!.size else 0
    }
}
