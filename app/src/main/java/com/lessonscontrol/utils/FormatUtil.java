package com.lessonscontrol.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class FormatUtil {

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public static final String DEFAULT_VALUE ="";

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
}
