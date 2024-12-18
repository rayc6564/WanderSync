package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.DatabaseManager;
import com.example.sprintproject.model.Dining;

import java.util.ArrayList;
import java.util.Collections;

public class DiningViewModel extends ViewModel {
    private DatabaseManager dbManager;

    private final MutableLiveData<ArrayList<Dining>> diningList;

    public DiningViewModel() {
        diningList = new MutableLiveData<>(new ArrayList<>());
        dbManager = DatabaseManager.getInstance();
    }

    public LiveData<ArrayList<Dining>> getDining() {
        return diningList;
    }

    public void addDiningToList(Dining dining) {
        ArrayList<Dining> currentList = new ArrayList<>(diningList.getValue());
        currentList.add(dining);
        diningList.setValue(currentList);
        dbManager.addDining(dining);
        dbManager.getCurrentTrip().addDining(dining.getID());
    }

    // Method to sort the list in ascending order by reservation date
    public void sortDiningListAscending() {
        ArrayList<Dining> sortedList = new ArrayList<>(diningList.getValue());
        Collections.sort(sortedList, (d1, d2) -> {
            String dateTime1 = d1.getReservationDate() + " " + d1.getReservationTime();
            String dateTime2 = d2.getReservationDate() + " " + d2.getReservationTime();
            return dateTime1.compareTo(dateTime2);
        });
        diningList.setValue(sortedList);
    }

    // Method to sort the list in descending order by reservation date
    public void sortDiningListDescending() {
        ArrayList<Dining> sortedList = new ArrayList<>(diningList.getValue());
        Collections.sort(sortedList, (d1, d2) -> {
            String dateTime1 = d1.getReservationDate() + " " + d1.getReservationTime();
            String dateTime2 = d2.getReservationDate() + " " + d2.getReservationTime();
            return dateTime2.compareTo(dateTime1);
        });
        diningList.setValue(sortedList);
    }
}