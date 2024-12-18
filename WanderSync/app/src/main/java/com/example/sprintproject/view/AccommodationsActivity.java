package com.example.sprintproject.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.viewmodel.AccommodationListViewModel;
import com.example.sprintproject.viewmodel.AccommodationViewModel;
import com.example.sprintproject.model.TimeConverter;
import com.example.sprintproject.viewmodel.Observer;
import com.example.sprintproject.viewmodel.SortByCheckInDate;
import com.example.sprintproject.viewmodel.SortByCheckOutDate;

import java.text.ParseException;
import java.util.Date;

public class AccommodationsActivity extends FeatureActivityBase implements Observer {

    private AccommodationListViewModel accommodationListViewModel;
    private AccommodationViewModel accommodationViewModel;
    private EditText hotelNameInput;
    private EditText locationInput;
    private EditText checkInInput;
    private EditText checkOutInput;
    private Spinner numRoomsSelection;
    private Spinner roomTypeSelection;
    private RecyclerView recyclerView;
    private static final String REGEX = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-(\\d{4})$";
    private CardView formContainer;
    private Button filterButton;
    private Button addAccommodationButton;
    private Button addButton;
    private Button cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_accommodations);
        super.onCreate(savedInstanceState);
        // Any specific logic for AccommodationsActivity can go here

        modelClassInitialize();

        accommodationListViewModel.addObserver(this);

        accommodationViewModel.getAccommodation().observe(this, accommodations -> {
            accommodationListViewModel.updateList(accommodations);
        });

        findViewInitialize();
        recyclerViewSetUp();
        buttonAction();
        dropDownSelection();

    }

    @Override
    public void updateList() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    // if activity is no longer need to be observe, stop it
    @Override
    protected void onDestroy() {
        super.onDestroy();
        accommodationListViewModel.removeObserver(this);
    }

    private void buttonAction() {
        addAccommodationButton.setOnClickListener(v -> {
            addAccommodation();
            accommodationListViewModel.setSorting(new SortByCheckInDate());
        });

        cancelButton.setOnClickListener(v -> {
            showVisibility(false);
            resets();
        });

        addButton.setOnClickListener(v -> showVisibility(true));

        filterButton.setOnClickListener(v -> {
            if (filterButton.getText().equals("Filter By Check-In Date")) {
                accommodationListViewModel.setSorting(new SortByCheckInDate());
                filterButton.setText("Filter By Check-Out Date");
            } else {
                accommodationListViewModel.setSorting(new SortByCheckOutDate());
                filterButton.setText("Filter By Check-In Date");
            }
            accommodationListViewModel.sort();
        });


    }

    private void modelClassInitialize() {
        accommodationListViewModel = new AccommodationListViewModel(this);
        accommodationViewModel = new ViewModelProvider(this).get(AccommodationViewModel.class);
    }

    private void findViewInitialize() {
        formContainer = findViewById(R.id.form_container);
        hotelNameInput = findViewById(R.id.hotel_name_input);
        checkInInput = findViewById(R.id.checkin_input);
        checkOutInput = findViewById(R.id.checkout_input);
        locationInput = findViewById(R.id.location_input);
        numRoomsSelection = findViewById(R.id.num_rooms);
        roomTypeSelection = findViewById(R.id.room_type);
        filterButton = findViewById(R.id.filter_button);
        addAccommodationButton = findViewById(R.id.add_accommodation_button);
        cancelButton = findViewById(R.id.accommodation_cancel_button);
        addButton = findViewById(R.id.add_button);
        recyclerView = findViewById(R.id.accommodations_recycler_view);
    }

    private void recyclerViewSetUp() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(accommodationListViewModel);
    }

    private void showVisibility(boolean showForm) {
        if (showForm) {
            formContainer.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            formContainer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void dropDownSelection() {
        dropDown(numRoomsSelection, R.array.num_rooms_dropDown);
        dropDown(roomTypeSelection, R.array.room_type_dropDown);
    }

    private void dropDown(Spinner selection, int id) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                id, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selection.setAdapter(adapter);
        selection.setSelection(0);
    }

    private void addAccommodation() {
        try {
            String hotelName = hotelNameInput.getText().toString();
            String location = locationInput.getText().toString();
            String checkInStr = checkInInput.getText().toString();
            String checkOutStr = checkOutInput.getText().toString();
            Date checkInDate = TimeConverter.stringToDate(checkInStr);
            Date checkOutDate = TimeConverter.stringToDate(checkOutStr);
            String numRooms = numRoomsSelection.getSelectedItem().toString();
            String roomType = roomTypeSelection.getSelectedItem().toString();

            if (!inputVerify()) {
                return;
            }

            Accommodation accommodation = new Accommodation(hotelName, checkInDate,
                    checkOutDate, location, numRooms, roomType);
            accommodationViewModel.addAccommodationToList(accommodation);
            resets();
            showVisibility(false);
        } catch (ParseException e) {
            Toast.makeText(this, "Incorrect Date format (MM-dd-yyyy) or dates",
                           Toast.LENGTH_SHORT).show();
        }
    }

    // to verify input
    private boolean inputVerify() {
        if (hotelNameInput.toString().isEmpty() || checkInInput.getText().toString().isEmpty()
                || checkOutInput.getText().toString().isEmpty()
                || locationInput.getText().toString().isEmpty()) {
          
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!checkInInput.getText().toString().matches(REGEX)
                || !checkOutInput.getText().toString().matches(REGEX)) {
            Toast.makeText(this, "Enter dates in MM-DD-YYYY format", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void resets() {
        hotelNameInput.setText("");
        checkInInput.setText("");
        checkOutInput.setText("");
        locationInput.setText("");
        numRoomsSelection.setSelection(0);
        roomTypeSelection.setSelection(0);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_accommodations;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}