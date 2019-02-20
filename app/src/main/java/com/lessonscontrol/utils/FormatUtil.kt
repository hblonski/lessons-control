package com.lessonscontrol.utils

import android.content.res.Resources
import ca.antonious.materialdaypicker.MaterialDayPicker
import java.security.InvalidParameterException
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object FormatUtil {

    private const val DATE_FORMAT = "dd/MM/yyyy"

    private const val DEFAULT_VALUE = ""

    private const val DAYS_FORMAT_SEPARATOR = "_"

    private const val DAYS_DISPLAY_SEPARATOR = ","

    fun convertDateToString(timeInMillis: Long?): String {
        if (timeInMillis == null) {
            return DEFAULT_VALUE
        }
        val date = Date(timeInMillis)
        val formatter = SimpleDateFormat(DATE_FORMAT)
        return formatter.format(date)
    }

    fun convertDoubleToMoney(value: Double?): String {
        if (value == null) {
            return DEFAULT_VALUE
        }
        val formatter = NumberFormat.getCurrencyInstance()
        return formatter.format(value)
    }

    /**
     * Converts a list of [MaterialDayPicker] to a String, following the rule:
     * X_X..., X being the first three letters of that day of the week
     * Example: a list containing Sunday, Monday and Saturday would return SUN_MON_SAT
     *
     * @param days a list of [MaterialDayPicker].
     * @return string in the specified format. null if the list is empty or null.
     */
    fun convertMaterialDayPickerListToString(days: List<MaterialDayPicker.Weekday>?): String {
        if (days == null || days.isEmpty()) {
            return DEFAULT_VALUE
        }
        return days.joinToString(separator = DAYS_FORMAT_SEPARATOR) { day -> getWeekDayID(day) }
    }

    fun convertStringToMaterialDayPickerList(selectedDays: String?): List<MaterialDayPicker.Weekday>? {
        if (selectedDays == null || selectedDays.isEmpty()) {
            return null
        }
        val listSelectedDaysIDs = selectedDays.split(DAYS_FORMAT_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return listSelectedDaysIDs.map { day -> getWeekDayFromID(day) }
    }

    fun formatWeekDayIDsForDisplay(ids: String?, resources: Resources, contextPackageName: String): String {
        if (ids == null || ids.isEmpty()) {
            return DEFAULT_VALUE
        }
        val individualIDsArray = ids.split(DAYS_FORMAT_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return individualIDsArray.joinToString(DAYS_DISPLAY_SEPARATOR) { id -> resources.getString(resources.getIdentifier(id, "string", contextPackageName)) }
    }

    private fun getWeekDayFromID(id: String): MaterialDayPicker.Weekday {
        return when (id) {
            "SUN" -> MaterialDayPicker.Weekday.SUNDAY
            "MON" -> MaterialDayPicker.Weekday.MONDAY
            "TUE" -> MaterialDayPicker.Weekday.TUESDAY
            "WED" -> MaterialDayPicker.Weekday.WEDNESDAY
            "THU" -> MaterialDayPicker.Weekday.THURSDAY
            "FRI" -> MaterialDayPicker.Weekday.FRIDAY
            "SAT" -> MaterialDayPicker.Weekday.SATURDAY
            else -> throw InvalidParameterException("Invalid weekday ID.")
        }
    }

    private fun getWeekDayID(day: MaterialDayPicker.Weekday): String {
        return when (day) {
            MaterialDayPicker.Weekday.SUNDAY -> "SUN"
            MaterialDayPicker.Weekday.MONDAY -> "MON"
            MaterialDayPicker.Weekday.TUESDAY -> "TUE"
            MaterialDayPicker.Weekday.WEDNESDAY -> "WED"
            MaterialDayPicker.Weekday.THURSDAY -> "THU"
            MaterialDayPicker.Weekday.FRIDAY -> "FRI"
            MaterialDayPicker.Weekday.SATURDAY -> "SAT"
            else -> throw InvalidParameterException("Invalid weekday.")
        }
    }
}
