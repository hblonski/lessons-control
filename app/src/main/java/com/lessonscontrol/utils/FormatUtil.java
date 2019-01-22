package com.lessonscontrol.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.text.TextUtils;

import java.security.InvalidParameterException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public abstract class FormatUtil {

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public static final String DEFAULT_VALUE = "";

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
     *
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

    public static List<MaterialDayPicker.Weekday> convertStringToMaterialDayPickerList(String selectedDays) {
        if (selectedDays == null || selectedDays.isEmpty()) {
            return null;
        }
        String[] listSelectedDaysIDs = selectedDays.split(DAYS_FORMAT_SEPARATOR);
        List<MaterialDayPicker.Weekday> days = new ArrayList<>();

        for (String id : listSelectedDaysIDs) {
            days.add(getWeekDayFromID(id));
        }

        return days;
    }

    public static String formatWeekDayIDsForDisplay(String ids, Resources resources, String contextPackageName) {
        if (ids == null || ids.isEmpty()) {
            return "";
        }
        List<String> formatted = new ArrayList<>();
        String[] individualIDsArray = ids.split(DAYS_FORMAT_SEPARATOR);
        for (String id : individualIDsArray) {
            formatted.add(resources.getString(resources.getIdentifier(id, "string", contextPackageName)));
        }
        return TextUtils.join(", ", formatted);
    }

    private static MaterialDayPicker.Weekday getWeekDayFromID(String id) {
        switch (id) {
            case "SUN":
                return MaterialDayPicker.Weekday.SUNDAY;
            case "MON":
                return MaterialDayPicker.Weekday.MONDAY;
            case "TUE":
                return MaterialDayPicker.Weekday.TUESDAY;
            case "WED":
                return MaterialDayPicker.Weekday.WEDNESDAY;
            case "THU":
                return MaterialDayPicker.Weekday.THURSDAY;
            case "FRI":
                return MaterialDayPicker.Weekday.FRIDAY;
            case "SAT":
                return MaterialDayPicker.Weekday.SATURDAY;
            default:
                throw new InvalidParameterException("Invalid weekday ID.");
        }
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

    public static Bitmap convertVectorDrawableToBitMap(VectorDrawableCompat vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }
}
