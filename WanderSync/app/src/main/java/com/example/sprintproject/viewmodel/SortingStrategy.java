package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Accommodation;

import java.util.ArrayList;

// strategy pattern
public interface SortingStrategy {
    void sort(ArrayList<Accommodation> accommodations);
}

