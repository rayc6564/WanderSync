package com.example.sprintproject.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Dining;
import com.example.sprintproject.model.SortByDiningDate;
import com.example.sprintproject.model.SortByDiningName;
import com.example.sprintproject.viewmodel.DiningListViewModel;
import com.example.sprintproject.viewmodel.DiningViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiningActivity extends FeatureActivityBase {

    private DiningListViewModel diningListViewModel;
    private DiningViewModel diningViewModel;
    private EditText diningNameInput;
    private EditText locationInput;
    private EditText websiteInput;
    private EditText reservationDateInput;
    private EditText reservationTimeInput;
    private RecyclerView recyclerView;
    private CardView formContainer;
    private Button sortButton;
    private Button addDiningButton;
    private Button addButton;
    private Button cancelButton;

    private static final String DATE_REGEX = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-(\\d{4})$";
    private static final String TIME_REGEX = "^([01]?[0-9]|2[0-3]):([0-5][0-9])$";
    private boolean isSortByName = false; // Flag to toggle sorting strategy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dining);
        super.onCreate(savedInstanceState);

        initializeViewModels();

        // Observe dining data and update the list, applying the default sort strategy
        diningViewModel.getDining().observe(this, dinings -> {
            diningListViewModel.updateList(dinings);
            // Automatically sort the data by default when loading
            diningListViewModel.applySortStrategy();
        });

        initializeViews();
        setUpRecyclerView();
        configureButtonActions();
    }

    private void initializeViewModels() {
        diningListViewModel = new DiningListViewModel(this);
        diningViewModel = new ViewModelProvider(this).get(DiningViewModel.class);
    }

    private void initializeViews() {
        formContainer = findViewById(R.id.form_container);
        diningNameInput = findViewById(R.id.dining_name_input);
        locationInput = findViewById(R.id.dining_location_input);
        websiteInput = findViewById(R.id.dining_website_input);
        reservationDateInput = findViewById(R.id.dining_reservation_date_input);
        reservationTimeInput = findViewById(R.id.dining_reservation_time_input);
        sortButton = findViewById(R.id.sort_button);
        addDiningButton = findViewById(R.id.add_dining_button);
        cancelButton = findViewById(R.id.dining_cancel_button);
        addButton = findViewById(R.id.add_button);
        recyclerView = findViewById(R.id.dining_recycler_view);
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(diningListViewModel);
    }

    private void configureButtonActions() {
        addDiningButton.setOnClickListener(v -> addDining());

        cancelButton.setOnClickListener(v -> {
            toggleFormVisibility(false);
            resetForm();
        });

        addButton.setOnClickListener(v -> toggleFormVisibility(true));

        sortButton.setOnClickListener(v -> toggleSortStrategy());
    }

    private void toggleFormVisibility(boolean showForm) {
        if (showForm) {
            formContainer.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            formContainer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void toggleSortStrategy() {
        if (isSortByName) {
            diningListViewModel.setSortStrategy(new SortByDiningDate());
            sortButton.setText("Sort by Name");
        } else {
            diningListViewModel.setSortStrategy(new SortByDiningName());
            sortButton.setText("Sort by Date");
        }
        isSortByName = !isSortByName;
    }

    private void addDining() {
        String name = diningNameInput.getText().toString();
        String location = locationInput.getText().toString();
        String website = websiteInput.getText().toString();
        String reservationDateStr = reservationDateInput.getText().toString();
        String reservationTimeStr = reservationTimeInput.getText().toString();

        if (!validateInput(reservationDateStr, reservationTimeStr)) {
            return;
        }

        try {
            Date reservationDate = new SimpleDateFormat("MM-dd-yyyy",
                    Locale.US).parse(reservationDateStr);
            Date reservationTime = new SimpleDateFormat("HH:mm",
                    Locale.US).parse(reservationTimeStr);

            Dining dining = new Dining(name, location, reservationDate, reservationTime, website);
            diningViewModel.addDiningToList(dining);

            resetForm();
            toggleFormVisibility(false);
            Toast.makeText(this, "Dining Reservation Added Successfully",
                    Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date or time format.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput(String reservationDateStr, String reservationTimeStr) {
        if (diningNameInput.getText().toString().isEmpty()
                || locationInput.getText().toString().isEmpty()
                || websiteInput.getText().toString().isEmpty()
                || reservationDateStr.isEmpty()
                || reservationTimeStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!reservationDateStr.matches(DATE_REGEX)) {
            Toast.makeText(this, "Enter dates in MM-DD-YYYY format", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!reservationTimeStr.matches(TIME_REGEX)) {
            Toast.makeText(this, "Enter time in HH:MM format", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void resetForm() {
        diningNameInput.setText("");
        locationInput.setText("");
        websiteInput.setText("");
        reservationDateInput.setText("");
        reservationTimeInput.setText("");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_dining;
    }
}