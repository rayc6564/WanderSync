package com.example.sprintproject.model;

import java.text.ParseException;
import java.util.Date;

public class Dining implements HasID {
    private String id;
    private String restaurantName;
    private String location;
    private Date reservationDate;
    private Date reservationTime;
    private String website;
    private String review;

    // Default constructor for Firebase
    public Dining() {

    }

    // Constructor with essential details for Dining reservation using Date objects
    public Dining(String restaurantName, String location, Date reservationDate,
                  Date reservationTime, String website, String review) {
        this.restaurantName = restaurantName;
        this.location = location;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.website = website;
        this.review = review;
    }

    public Dining(String restaurantName, String location, Date reservationDate,
                  Date reservationTime, String website) {
        this(restaurantName, location, reservationDate, reservationTime, website, "");
    }

    // Getters and Setters

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Return formatted string for reservation date
    public String getReservationDate() {
        return TimeConverter.dateToString(reservationDate);
    }

    // Convenience methods for setting date and time from strings
    public void setReservationDate(String reservationDateStr) throws ParseException {
        this.reservationDate = TimeConverter.stringToDate(reservationDateStr);
    }

    // Return formatted string for reservation time
    public String getReservationTime() {
        return TimeConverter.timeToString(reservationTime);
    }

    public void setReservationTime(String reservationTimeStr) throws ParseException {
        this.reservationTime = TimeConverter.stringToTime(reservationTimeStr);
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}