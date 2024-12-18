package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Dining;

public interface DiningCallback extends Callback<Dining> {
    @Override
    void onCallback(Dining result);
}
