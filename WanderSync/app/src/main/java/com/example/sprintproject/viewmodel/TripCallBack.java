package com.example.sprintproject.viewmodel;
import com.example.sprintproject.model.Trip;

public interface TripCallBack extends Callback<Trip> {
    @Override
    default void onCallback(Trip result) {

    }
}
