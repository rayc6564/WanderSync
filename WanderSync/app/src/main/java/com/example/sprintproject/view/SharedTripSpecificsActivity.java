package com.example.sprintproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.databinding.ActivitySharedTripSpecificsBinding;
import com.example.sprintproject.viewmodel.SharedTripDestinationListViewModel;


public class SharedTripSpecificsActivity extends AppCompatActivity {

    private ActivitySharedTripSpecificsBinding binding;
    private SharedTripDestinationListViewModel sharedTripDestinationListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySharedTripSpecificsBinding.inflate(getLayoutInflater());
        sharedTripDestinationListViewModel = new SharedTripDestinationListViewModel();
        setContentView(binding.getRoot());

        // Get String of Trip duration here
        binding.setDuration("temp duration");
        // Get String of Note here
        binding.setNote("temp note");
        // Get each Destination in trip here
        // For each Destination in trip, call sharedTripDestinationListViewModel.add(Destination);
        // Hopefully that works

        // Get each Accomodation in the trip here

        // Get each dining reservation in the trip here


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SharedTripSpecificsActivity.this,
                        CommunityActivity.class);
                startActivity(intent);
            }
        });
    }
}