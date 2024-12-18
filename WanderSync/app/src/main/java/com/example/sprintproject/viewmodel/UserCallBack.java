package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.User;

public interface UserCallBack extends Callback<User> {
    @Override
    default void onCallback(User result) {

    }
}
