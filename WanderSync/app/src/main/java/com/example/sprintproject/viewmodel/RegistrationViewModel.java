package com.example.sprintproject.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.DatabaseManager;
import com.example.sprintproject.model.Trip;
import com.example.sprintproject.model.User;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationViewModel extends ViewModel {
    private DatabaseManager dbManager;

    public RegistrationViewModel() {
        dbManager = DatabaseManager.getInstance();
    }

    public void setCurrUser(FirebaseUser currUser, UserCallBack callBack) {
        Trip trip = new Trip();
        trip.addUser(currUser.getUid());
        dbManager.createTrip(trip, currUser.getUid(), new TripCallBack() {
            @Override
            public void onCallback(Trip result) {
                User user = new User(currUser.getUid(), currUser.getEmail(), trip.getID());
                dbManager.addUser(user);
                dbManager.loginUser(user);
                callBack.onCallback(user);
            }
        });
    }
}
