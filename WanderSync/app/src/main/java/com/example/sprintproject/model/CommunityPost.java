package com.example.sprintproject.model;

public class CommunityPost implements HasID {
    private String id;
    private String userID;
    private String start;
    private String end;
    private String destinations;
    private String accommodations;
    private String dining;
    private String notes;

    public CommunityPost(String notes, String dining, String accommodations, String destinations,
                         String end, String start, String userID) {
        this.notes = notes;
        this.dining = dining;
        this.accommodations = accommodations;
        this.destinations = destinations;
        this.end = end;
        this.start = start;
        this.userID = userID;
    }

    public CommunityPost(String userID) {
        this.userID = userID;
    }

    public CommunityPost() { }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getDestinations() {
        return destinations;
    }

    public String getAccommodations() {
        return accommodations;
    }

    public String getDining() {
        return dining;
    }

    public String getNotes() {
        return notes;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setDestinations(String destinations) {
        this.destinations = destinations;
    }

    public void setAccommodations(String accommodations) {
        this.accommodations = accommodations;
    }

    public void setDining(String dining) {
        this.dining = dining;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
