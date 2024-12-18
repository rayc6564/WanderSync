package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.DatabaseManager;

import java.util.ArrayList;

public class AccommodationViewModel extends ViewModel {
    private DatabaseManager dbManager;

    private final MutableLiveData<ArrayList<Accommodation>> accommodationList;

    public AccommodationViewModel() {
        accommodationList = new MutableLiveData<>(new ArrayList<>());
        dbManager = DatabaseManager.getInstance();

    }

    public LiveData<ArrayList<Accommodation>> getAccommodation() {
        return accommodationList;
    }

    public void addAccommodationToList(Accommodation accommodation) {
        ArrayList<Accommodation> currentList = new ArrayList<>(accommodationList.getValue());
        currentList.add(accommodation);
        accommodationList.setValue(currentList);
        dbManager.addAccommodation(accommodation);
        dbManager.getCurrentTrip().addAccomidation(accommodation.getID());
    }

}
