package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Destination;

public interface DestinationCallback  extends Callback<Destination> {
    @Override
    default void onCallback(Destination result) {

    }
}
