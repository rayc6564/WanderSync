package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Accommodation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// checkout date sort
public class SortByCheckOutDate implements SortingStrategy {
    @Override
    public void sort(ArrayList<Accommodation> accommodations) {
        Collections.sort(accommodations, Comparator.comparing(Accommodation::getCheckOutDate));
    }
}