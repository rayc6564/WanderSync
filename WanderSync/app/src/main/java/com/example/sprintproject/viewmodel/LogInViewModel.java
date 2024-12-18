package com.example.sprintproject.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.DatabaseManager;
import com.example.sprintproject.model.Trip;
import com.example.sprintproject.model.User;
import com.google.firebase.auth.FirebaseUser;

public class LogInViewModel extends ViewModel {
    private DatabaseManager dbManager;

    public LogInViewModel() {
        dbManager = DatabaseManager.getInstance();
    }

    public void setCurrUser(FirebaseUser currUser, final UserCallBack userCallBack) {
        dbManager.getUserByUID(currUser.getUid(), new UserCallBack() {
            @Override
            public void onCallback(User user) {
                dbManager.loginUser(user);
                dbManager.getTrip(user.getTrip(), new TripCallBack() {
                    @Override
                    public void onCallback(Trip trip) {
                        dbManager.setCurrentTrip(trip);
                        userCallBack.onCallback(user);
                    }
                });
            }
        });
    }
}
