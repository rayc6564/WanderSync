package com.example.sprintproject.viewmodel;

import android.text.Editable;
import android.view.View;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.example.sprintproject.model.DatabaseManager;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.view.StringToDateConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DestinationViewModel implements StringToDateConverter {
    // Defined to suppress IDE errors related to visibility type
    @IntDef({View.VISIBLE, View.INVISIBLE, View.GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility { }

    @Visibility
    private int formVisibility;
    @Visibility
    private int calculatorVisibility;
    private DatabaseManager dbManager;
    private static final String DATE_FORMAT = "MM-dd-yyyy";

    public DestinationViewModel() {
        formVisibility = View.GONE;
        calculatorVisibility = View.GONE;
        dbManager = DatabaseManager.getInstance();
    }

    @Visibility
    public int getFormVisibility() {
        return formVisibility;
    }

    public void updateFormVisibility() {
        if (formVisibility == View.VISIBLE) {
            formVisibility = View.GONE;
        } else {
            formVisibility = View.VISIBLE;
        }
    }

    @Visibility
    public int getCalculatorVisibility() {
        return calculatorVisibility;
    }

    public void updateCalculatorVisibility() {
        if (calculatorVisibility == View.VISIBLE) {
            calculatorVisibility = View.GONE;
        } else {
            calculatorVisibility = View.VISIBLE;
        }
    }

    public Date convertStringToDate(String str) throws ParseException {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        df.setLenient(false);
        return df.parse(str);
    }

    public String submitLogErrorChecking(String location, String start, String end) {
        if (location == null || start == null || end == null) {
            return "All fields must have inputs";
        }

        location = location.strip();
        start = start.strip();
        end = end.strip();

        Date startDate;
        Date endDate;
        try {
            startDate = convertStringToDate(start);
            endDate = convertStringToDate(end);
        } catch (ParseException e) {
            return "Start and end date must be in the format mm-dd-yyyy";
        }

        if (!location.isEmpty()) {
            Destination dest = new Destination(location, startDate, endDate);
            dbManager.addDestination(dest);
            dbManager.getCurrentTrip().addDestination(dest.getID());
            return null;
        } else {
            return "There must be a location";
        }
    }

    public long timeBetweenDates(String start, String end) {
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = convertStringToDate(start);
            endDate = convertStringToDate(end);
        } catch (ParseException e) {
            return -1;
        }
        long totalTimeInMilliseconds = (endDate.getTime() - startDate.getTime());
        long totalTimeInDays = TimeUnit.DAYS.convert(
                totalTimeInMilliseconds, TimeUnit.MILLISECONDS);
        return totalTimeInDays;
    }

    public String calculateVacationTime(Editable startEdit, Editable endEdit, Editable duration) {
        String startString = startEdit.toString().trim();
        String endString = endEdit.toString().trim();
        String durString = duration.toString().trim();

        Date startDate;
        Date endDate;
        int durInt;

        DateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        df.setLenient(false);

        Calendar calendar = Calendar.getInstance();

        //if all boxes are filled
        if (!startString.isBlank() && !endString.isBlank() && !durString.isBlank()) {
            try {
                startDate = df.parse(startString);
                endDate = df.parse(endString);
                durInt = Integer.parseInt(durString);
            } catch (NumberFormatException nfe) {
                return "Duration must be a number";
            } catch (Exception e) {
                return "Start date must be in the format mm-dd-yyyy";
            }

            if (startDate == null || endDate == null) {
                return "Make sure dates have valid inputs";
            }
            if (startDate.after(endDate)) {
                return "The start date must be before the end date";
            }
            if (durInt <= 0) {
                return "Duration must be more than 0";
            }
            int realDurInt = (int) TimeUnit.DAYS.convert(endDate.getTime() - startDate.getTime(),
                    TimeUnit.MILLISECONDS);
            if (realDurInt == durInt) {
                dbManager.setVacationTime(startDate, endDate, durInt);
                return "Calculation Successful!";
            } else {
                return "Calculation invalid, either change your inputs, or remove one and autofill";
            }
        } else if (!startString.isBlank() && !endString.isBlank()) { //if duration box is blank
            try {
                startDate = df.parse(startString);
                endDate = df.parse(endString);
            } catch (Exception e) {
                return "Start and end date must be in the format mm-dd-yyyy";
            }

            if (startDate == null || endDate == null) {
                return "Make sure dates have valid inputs";
            }
            if (startDate.after(endDate)) {
                return "The start date must be before the end date";
            }

            durInt = (int) TimeUnit.DAYS.convert(endDate.getTime() - startDate.getTime(),
                    TimeUnit.MILLISECONDS);
            durString = String.valueOf(durInt);
            duration.clear();
            duration.append(durString);
            dbManager.setVacationTime(startDate, endDate, durInt);
            return "Calculation Successful!";
        } else if (!startString.isBlank() && !durString.isBlank()) { //if endDate box is blank
            try {
                startDate = df.parse(startString);
                durInt = Integer.parseInt(durString);
            } catch (NumberFormatException nfe) {
                return "Duration must be a number";
            } catch (Exception e) {
                return "Start date must be in the format mm-dd-yyyy";
            }
            if (startDate == null) {
                return "Make sure start date has a valid input";
            }
            if (durInt <= 0) {
                return "Duration must be more than 0";
            }

            calendar.setTime(startDate);
            calendar.add(Calendar.DAY_OF_YEAR, durInt);
            endDate = calendar.getTime();
            endString = df.format(endDate);
            endEdit.clear();
            endEdit.append(endString);
            dbManager.setVacationTime(startDate, endDate, durInt);
            return "Calculation Successful!";
        } else if (!endString.isBlank() && !durString.isBlank()) { //if startDate box is blank
            try {
                endDate = df.parse(endString);
                durInt = Integer.parseInt(durString);
            } catch (NumberFormatException nfe) {
                return "Duration must be a number";
            } catch (Exception e) {
                return "End date must be in the format mm-dd-yyyy";
            }
            if (endDate == null) {
                return "Make sure end date has a valid input";
            }
            if (durInt <= 0) {
                return "Duration must be more than 0";
            }

            calendar.setTime(endDate);
            calendar.add(Calendar.DAY_OF_YEAR, -durInt);
            startDate = calendar.getTime();
            startString = df.format(startDate);
            startEdit.clear();
            startEdit.append(startString);
            dbManager.setVacationTime(startDate, endDate, durInt);
            return "Calculation Successful!";
        }

        return "At least two of the boxes must be filled";
    }
}
