package com.example.sprintproject.model;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Destination implements HasID {
    private String id;
    private String location;
    private Date startDate;
    private Date endDate;

    private static final String DEFAULT_START = "01-01-2024";
    private static final String DEFAULT_END = "01-05-2024";


    public Destination() {
    }

    public Destination(String location, Date startDate, Date endDate) {
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Destination(String location, String startDate, String endDate) throws ParseException {
        // Check if startDate or endDate are null or empty, and use default values if so
        if (startDate == null || startDate.isEmpty()) {
            startDate = DEFAULT_START;
        }
        if (endDate == null || endDate.isEmpty()) {
            endDate = DEFAULT_END;
        }
        this.location = location;
        this.startDate = TimeConverter.stringToDate(startDate);
        this.endDate = TimeConverter.stringToDate(endDate);
    }

    public Destination(String location) throws ParseException {
        this(location, DEFAULT_START, DEFAULT_END);
    }

    public int duration() {
        long totalTimeInMilliseconds = (this.endDate.getTime() - this.startDate.getTime());
        long totalTimeInDays = TimeUnit.DAYS.convert(
                totalTimeInMilliseconds, TimeUnit.MILLISECONDS);
        return (int) totalTimeInDays;
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return TimeConverter.dateToString(this.startDate);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return TimeConverter.dateToString(this.endDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
