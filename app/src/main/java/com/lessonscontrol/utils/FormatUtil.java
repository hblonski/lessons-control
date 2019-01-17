package com.lessonscontrol.utils;

import android.text.TextUtils;

import java.security.InvalidParameterException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public abstract class FormatUtil {

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public static final String DEFAULT_VALUE ="";

    public static final String DAYS_FORMAT_SEPARATOR = "_";

    public static String convertDateToString(Long timeInMillis) {
        if (timeInMillis == null) {
            return DEFAULT_VALUE;
        }
        Date date = new Date(timeInMillis);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(date);
    }

    public static String convertDoubleToMoney(Double value) {
        if (value == null) {
            return DEFAULT_VALUE;
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(value);
    }

    /**
     * Converts a list of {@link MaterialDayPicker} to a String, following the rule:
     * X_X..., X being the first three letters of that day of the week
     * Example: a list containing Sunday, Monday and Saturday would return SUN_MON_SAT
     * @param days a list of {@link MaterialDayPicker}.
     * @return string in the specified format. null if the list is empty or null.
     */
    public static String convertMaterialDayPickerListToString(List<MaterialDayPicker.Weekday> days) {
        if (days == null || days.isEmpty()) {
            return null;
        }
        List<String> ids = new ArrayList<>();
        for (MaterialDayPicker.Weekday day : days) {
            ids.add(getWeekDayID(day));
        }
        return TextUtils.join(DAYS_FORMAT_SEPARATOR, ids);
    }

    private static String getWeekDayID(MaterialDayPicker.Weekday day) {
        switch (day) {
            case SUNDAY:
                return "SUN";
            case MONDAY:
                return "MON";
            case TUESDAY:
                return "TUE";
            case WEDNESDAY:
                return "WED";
            case THURSDAY:
                return "THU";
            case FRIDAY:
                return "FRI";
            case SATURDAY:
                return "SAT";
            default:
                throw new InvalidParameterException("Invalid weekday.");
        }
    }
}
