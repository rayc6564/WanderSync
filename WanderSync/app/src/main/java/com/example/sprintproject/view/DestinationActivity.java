package com.example.sprintproject.view;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityDestinationBinding;
import com.example.sprintproject.viewmodel.DestinationListViewModel;
import com.example.sprintproject.viewmodel.DestinationViewModel;

public class DestinationActivity extends FeatureActivityBase {

    private static final String DATE_FORMAT = "MM-dd-yyyy";
    private RecyclerView recyclerView;
    private DestinationViewModel destinationViewModel;
    private DestinationListViewModel destinationListViewModel;
    private ActivityDestinationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutResourceId());
        super.onCreate(savedInstanceState);
        Button logButton = findViewById(R.id.log_travel_button);
        View logFormBackground = findViewById(R.id.log_form_background);
        View travelLocation = findViewById(R.id.travel_location);
        View locationInput = findViewById(R.id.location_input);
        View startDate = findViewById(R.id.start_date);
        View startInput = findViewById(R.id.start_input);
        View endDate = findViewById(R.id.end_date);
        View endInput = findViewById(R.id.end_input);
        Button cancelButton = findViewById(R.id.log_cancel_button);
        Button submitButton = findViewById(R.id.log_submit_button);
        Button calculateVacationButton = findViewById(R.id.calculate_vacation_button);
        Button calculateButton = findViewById(R.id.calculate_button);
        recyclerView = binding.recyclerView;
        destinationViewModel = new DestinationViewModel();
        destinationListViewModel = new DestinationListViewModel();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(destinationListViewModel);
        binding.setVariable(BR.destViewModel, destinationViewModel);
        binding.setVariable(BR.destListViewModel, destinationListViewModel);
        binding.setLifecycleOwner(this);


        // Appear or disappear the Log Travel form
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinationViewModel.updateFormVisibility();
                logFormBackground.setVisibility(destinationViewModel.getFormVisibility());
                travelLocation.setVisibility(destinationViewModel.getFormVisibility());
                locationInput.setVisibility(destinationViewModel.getFormVisibility());
                startInput.setVisibility(destinationViewModel.getFormVisibility());
                startDate.setVisibility(destinationViewModel.getFormVisibility());
                endInput.setVisibility(destinationViewModel.getFormVisibility());
                endDate.setVisibility(destinationViewModel.getFormVisibility());
                cancelButton.setVisibility(destinationViewModel.getFormVisibility());
                submitButton.setVisibility(destinationViewModel.getFormVisibility());
                binding.locationInput.setText("");
                binding.startInput.setText("");
                binding.endInput.setText("");
            }
        });
        // Disappear the log travel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinationViewModel.updateFormVisibility();
                logFormBackground.setVisibility(destinationViewModel.getFormVisibility());
                travelLocation.setVisibility(destinationViewModel.getFormVisibility());
                locationInput.setVisibility(destinationViewModel.getFormVisibility());
                startInput.setVisibility(destinationViewModel.getFormVisibility());
                startDate.setVisibility(destinationViewModel.getFormVisibility());
                endInput.setVisibility(destinationViewModel.getFormVisibility());
                endDate.setVisibility(destinationViewModel.getFormVisibility());
                cancelButton.setVisibility(destinationViewModel.getFormVisibility());
                submitButton.setVisibility(destinationViewModel.getFormVisibility());
                binding.locationInput.setText("");
                binding.startInput.setText("");
                binding.endInput.setText("");
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = binding.locationInput.getText().toString();
                String startDate = binding.startInput.getText().toString();
                String endDate = binding.endInput.getText().toString();
                // Checks if fields are valid and add to database
                String returnString = destinationViewModel.submitLogErrorChecking(
                        location, startDate, endDate);
                //If fields are valid
                if (returnString == null) {
                    // Calculate destination time in days
                    long totalTimeInDays = destinationViewModel.timeBetweenDates(
                            startDate, endDate);
                    if (totalTimeInDays < 0) {
                        Toast.makeText(DestinationActivity.this,
                                "Your dates may be in the wrong order", Toast.LENGTH_LONG).show();
                    } else {
                        // Reset the TextEdit text
                        binding.locationInput.setText("");
                        binding.startInput.setText("");
                        binding.endInput.setText("");
                        // Add data to screen
                        try {
                            destinationListViewModel.add(location.strip(), startDate, endDate);

                            // Update total days TextView
                            //int totalDays = destinationListViewModel.getTotalDuration();
                            //binding.totalDaysTextView.setText(totalDays + " Days");
                        } catch (Exception e) {
                            // Handle any issues that arise during the add operation
                            Toast.makeText(DestinationActivity.this,
                                    "Error adding destination.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(DestinationActivity.this,
                            returnString, Toast.LENGTH_LONG).show();
                }
            }
        });

        // Appear or disappear calculation form
        calculateVacationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinationViewModel.updateCalculatorVisibility();
                binding.calculatorBackground.setVisibility(
                        destinationViewModel.getCalculatorVisibility());
                binding.calculatorStartDate.setVisibility(
                        destinationViewModel.getCalculatorVisibility());
                binding.calculatorStartInput.setVisibility(
                        destinationViewModel.getCalculatorVisibility());
                binding.calculatorEndDate.setVisibility(
                        destinationViewModel.getCalculatorVisibility());
                binding.calculatorEndInput.setVisibility(
                        destinationViewModel.getCalculatorVisibility());
                binding.calculatorDuration.setVisibility(
                        destinationViewModel.getCalculatorVisibility());
                binding.calculatorDurationInput.setVisibility(
                        destinationViewModel.getCalculatorVisibility());
                binding.calculateButton.setVisibility(
                        destinationViewModel.getCalculatorVisibility());
                binding.calculatorStartInput.setText("");
                binding.calculatorEndInput.setText("");
                binding.calculatorDurationInput.setText("");
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable startDate = binding.calculatorStartInput.getText();
                Editable endDate = binding.calculatorEndInput.getText();
                Editable duration = binding.calculatorDurationInput.getText();

                String retVal = destinationViewModel.calculateVacationTime(startDate,
                        endDate, duration);
                Toast.makeText(DestinationActivity.this,
                        retVal, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_destination;  // Specific layout for DestinationActivity
    }

    // Repopulate from database
    @Override
    protected void onResume() {
        super.onResume();
    }
}