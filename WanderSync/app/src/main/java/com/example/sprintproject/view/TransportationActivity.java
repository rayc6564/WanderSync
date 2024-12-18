package com.example.sprintproject.view;

import android.os.Bundle;
import com.example.sprintproject.R;

public class TransportationActivity extends FeatureActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(getLayoutResourceId());
        super.onCreate(savedInstanceState);
        // Any specific logic for TransportationActivity can go here
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_transportation;  // Specific layout for TransportationActivity
    }
}