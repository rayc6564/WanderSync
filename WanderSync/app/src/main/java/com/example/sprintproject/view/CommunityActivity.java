package com.example.sprintproject.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sprintproject.model.CommunityPost;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.DatabaseManager;
import com.example.sprintproject.viewmodel.CommunityListViewModel;
import com.example.sprintproject.viewmodel.CommunityViewModel;
import com.example.sprintproject.viewmodel.Observer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CommunityActivity extends FeatureActivityBase
        implements Observer {

    private CommunityListViewModel communityListViewModel;
    private CommunityViewModel communityViewModel;
    private DatabaseManager databaseManager;
    private EditText startDateInput;
    private EditText endDateInput;
    private EditText destinationInput;
    private EditText accommodationsInput;
    private EditText diningReservationsInput;
    private EditText notesInput;
    private RecyclerView recyclerView;
    private CardView formContainer;
    private Button addPostButton;
    private Button addButton;
    private Button cancelButton;
    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("MM-dd-yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_community);
        super.onCreate(savedInstanceState);

        initializeViewModels();

        databaseManager = DatabaseManager.getInstance();

        communityListViewModel.addObserver(this);

        communityViewModel.getTravelPosts().observe(this, posts -> {
            communityListViewModel.updateList(posts);
        });

        initializeViews();
        setUpRecyclerView();
        configureButtonActions();
    }

    @Override
    public void updateList() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        communityListViewModel.removeObserver(this);
    }

    private void initializeViewModels() {
        communityListViewModel = new CommunityListViewModel(this, new ArrayList<>());
        communityViewModel = new ViewModelProvider(this).get(CommunityViewModel.class);
    }

    private void initializeViews() {
        formContainer = findViewById(R.id.form_container);
        startDateInput = findViewById(R.id.start_date_input);
        endDateInput = findViewById(R.id.end_date_input);
        destinationInput = findViewById(R.id.destination_input);
        accommodationsInput = findViewById(R.id.accommodations_input);
        diningReservationsInput = findViewById(R.id.dining_reservations_input);
        notesInput = findViewById(R.id.notes_input);
        addPostButton = findViewById(R.id.add_post_button);
        cancelButton = findViewById(R.id.cancel_button);
        addButton = findViewById(R.id.add_button);
        recyclerView = findViewById(R.id.community_recycler_view);
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(communityListViewModel);
    }

    private void configureButtonActions() {
        addPostButton.setOnClickListener(v -> addCommunityPost());

        cancelButton.setOnClickListener(v -> {
            toggleFormVisibility(false);
            resetForm();
        });

        addButton.setOnClickListener(v -> toggleFormVisibility(true));
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

    private void addCommunityPost() {
        String startDateStr = startDateInput.getText().toString();
        String endDateStr = endDateInput.getText().toString();
        String destination = destinationInput.getText().toString();
        String accommodations = accommodationsInput.getText().toString();
        String diningReservations = diningReservationsInput.getText().toString();
        String email = "anonymous";
        String notes;
        if (!(notesInput.getText().toString() + "*").substring(0, 1).equals("*")) {
            email = DatabaseManager.getInstance().getLoggedInUser().getEmail();
            notes = notesInput.getText().toString() + "\nPosted by: " + email;
        } else if (notesInput.getText().toString().length() >= 1) {
            notes = notesInput.getText().toString().substring(1) + "\nPosted by: " + email;
        } else {
            notes = "" + "\nPosted by: " + DatabaseManager.getInstance().getLoggedInUser().getEmail();
        }

        if (!validateInput(startDateStr, endDateStr, destination)) {
            return;
        }

        try {
            Date startDate = DATE_FORMAT.parse(startDateStr);
            Date endDate = DATE_FORMAT.parse(endDateStr);
            CommunityPost newPost = new CommunityPost(
                    notes, diningReservations, accommodations, destination,
                    endDateStr, startDateStr, databaseManager.getLoggedInUser().getID()
            );

            communityViewModel.addTravelPostToList(newPost);
            resetForm();
            toggleFormVisibility(false);
            Toast.makeText(this, "Community Post Added Successfully", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date format.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput(String startDateStr, String endDateStr, String destination) {
        if (startDateStr.isEmpty() || endDateStr.isEmpty() || destination.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void resetForm() {
        startDateInput.setText("");
        endDateInput.setText("");
        destinationInput.setText("");
        accommodationsInput.setText("");
        diningReservationsInput.setText("");
        notesInput.setText("");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_community;
    }
}