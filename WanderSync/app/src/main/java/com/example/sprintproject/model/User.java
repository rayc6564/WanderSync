package com.example.sprintproject.model;

import com.example.sprintproject.viewmodel.TripCallBack;
import com.example.sprintproject.viewmodel.UserCallBack;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class User implements HasID {
    private String id;
    private String email;
    private Date startDate;
    private Date endDate;
    private int duration;
    private String trip;
    private final DateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

    public User(String uid, String email, Date startDate, Date endDate, int duration,
                String trip) {
        this.id = uid;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.trip = trip;
    }

    public User(String uid, String email, String startDate, String endDate, int duration,
                String trip) {
        this.id = uid;
        this.email = email;
        try {
            this.startDate = df.parse(startDate);
            this.endDate = df.parse(endDate);
        } catch (Exception e) {
            this.startDate = new Date(0, 0, 1);
            this.endDate = new Date(0, 0, 1);
        }
        this.duration = duration;
        this.trip = trip;
    }

    public User(String uid, String email, String trip) {
        this(uid, email, "01-01-2000", "01-01-2000", 0, trip);
    }

    public User() { }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    /**
     * returns date in string format to store in database
     *
     * @return date in String format
     */
    public String getStartDate() {
        return df.format(startDate);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * returns date in string format to store in database
     *
     * @return date in String format
     */
    public String getEndDate() {
        return df.format(endDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        User user = this;
        DatabaseManager.getInstance().getTrip(this.trip, new TripCallBack() {
            @Override
            public void onCallback(Trip result) {
                result.removeUser(user);
            }
        });
        this.trip = trip;
        DatabaseManager.getInstance().getTrip(this.trip, new TripCallBack() {
            @Override
            public void onCallback(Trip result) {
                result.addUser(user.getID());
            }
        });
    }

    public void inviteUser(String email) {
        DatabaseManager.getInstance().getUserByEmail(email, new UserCallBack() {
            @Override
            public void onCallback(User user) {
                DatabaseManager.getInstance().addUserToTrip(user);
            }
        });
    }
}
