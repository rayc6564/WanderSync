package com.example.sprintproject.model;

import java.util.ArrayList;
import java.util.List;

public class Trip implements HasID {
    private String id;
    private String notes;
    private List<String> destinations;
    private List<String> accomidations;
    private List<String> dining;
    private List<String> users;

    public Trip(String id, String notes, List<String> destinations, List<String> accomidations,
                List<String> dining, List<String> users) {
        this.id = id;
        this.notes = notes;
        this.destinations = destinations;
        this.accomidations = accomidations;
        this.dining = dining;
        this.users = users;
    }

    public Trip(String id, List<String> destinations, List<String> accomidations,
                List<String> dining, List<String> users) {
        this.id = id;
        this.destinations = destinations;
        this.accomidations = accomidations;
        this.dining = dining;
        this.users = users;
    }

    public Trip() {
        this.destinations = new ArrayList<>();
        this.accomidations = new ArrayList<>();
        this.dining = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public Trip(String currUID) {
        this();
        users.add(currUID);
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    @Override
    public String getID() {
        return id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<String> getAccomidations() {
        return new ArrayList<>(accomidations);
    }

    public void addAccomidation(String accomidationId) {
        accomidations.add(accomidationId);
    }

    public List<String> getDining() {
        return new ArrayList<>(dining);
    }

    public void addDining(String diningId) {
        dining.add(diningId);
    }

    public List<String> getDestinations() {
        return new ArrayList<>(destinations);
    }

    public void addDestination(String destinationId) {
        destinations.add(destinationId);
    }

    public List<String> getUsers() {
        return users;
    }

    public void addUser(String uid) {
        users.add(uid);
    }

    public void removeUser(User user) {
        users.remove(user.getID());
    }
}
