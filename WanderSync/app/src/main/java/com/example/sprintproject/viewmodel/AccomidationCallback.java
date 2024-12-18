package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Accommodation;

public interface AccomidationCallback extends Callback<Accommodation> {
    @Override
    void onCallback(Accommodation result);
}
