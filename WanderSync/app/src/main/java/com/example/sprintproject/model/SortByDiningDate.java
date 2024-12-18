package com.example.sprintproject.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SortByDiningDate implements DiningSortStrategy {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy",
            Locale.US);

    @Override
    public void sort(List<Dining> diningList) {
        Collections.sort(diningList, (d1, d2) -> {
            try {
                Date date1 = DATE_FORMAT.parse(d1.getReservationDate());
                Date date2 = DATE_FORMAT.parse(d2.getReservationDate());
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0; // If parsing fails, consider the dates equal
            }
        });
    }
}