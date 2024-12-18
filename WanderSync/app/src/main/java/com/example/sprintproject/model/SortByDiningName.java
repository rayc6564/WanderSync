package com.example.sprintproject.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByDiningName implements DiningSortStrategy {
    @Override
    public void sort(List<Dining> diningList) {
        Collections.sort(diningList, Comparator.comparing(dining ->
                dining.getRestaurantName().toLowerCase()));
    }
}