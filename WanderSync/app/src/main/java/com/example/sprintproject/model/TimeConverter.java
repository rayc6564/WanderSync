package com.example.sprintproject.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class TimeConverter {
    private static final String DATE_FORMAT = "MM-dd-yyyy";
    private static final String TIME_FORMAT = "HH:mm";
    private static final DateFormat DF = new SimpleDateFormat(DATE_FORMAT, Locale.US);
    private static final DateFormat TF = new SimpleDateFormat(TIME_FORMAT, Locale.US);

    public static Date stringToDate(String date) throws ParseException {
        return DF.parse(date);
    }

    public static String dateToString(Date date) {
        return DF.format(date);
    }

    public static Date stringToTime(String time) throws ParseException {
        return TF.parse(time);
    }

    public static String timeToString(Date time) {
        return TF.format(time);
    }
}