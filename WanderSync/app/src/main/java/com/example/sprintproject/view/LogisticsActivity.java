package com.example.sprintproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.graphics.Color;
import android.widget.Toast;

import com.example.sprintproject.R;
import com.example.sprintproject.model.DatabaseManager;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.Trip;
import com.example.sprintproject.model.User;
import com.example.sprintproject.viewmodel.TripCallBack;
import com.example.sprintproject.viewmodel.UserCallBack;
import com.example.sprintproject.viewmodel.DestinationCallback;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.components.Legend;

import java.util.ArrayList;
import java.util.List;

public class LogisticsActivity extends FeatureActivityBase {

    private PieChart pieChart;
    private DatabaseManager databaseManager;
    private int allottedDays;
    private int plannedDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(getLayoutResourceId());
        super.onCreate(savedInstanceState);

        pieChart = findViewById(R.id.pieChart);
        Button visualizeButton = findViewById(R.id.btnVisualizeTripDays);
        databaseManager = DatabaseManager.getInstance();
        Button inviteButton = findViewById((R.id.inviteButton));
        Button notesButton = findViewById(R.id.notesButton);
        pieChart.setVisibility(View.GONE);

        visualizeButton.setOnClickListener(v -> fetchUserData());
        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogisticsActivity.this, InviteActivity.class);
                startActivity(intent);
            }
        });
        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogisticsActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });


    }

    private void fetchUserData() {
        User currentUser = databaseManager.getLoggedInUser();

        if (currentUser != null) {
            databaseManager.getUserByUID(currentUser.getID(), new UserCallBack() {
                @Override
                public void onCallback(User user) {
                    if (user != null) {
                        allottedDays = user.getDuration();
                        fetchUserTrip(user.getTrip());
                    } else {
                        Toast.makeText(LogisticsActivity.this, "User data not found.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchUserTrip(String tripId) {
        databaseManager.getTrip(tripId, new TripCallBack() {
            @Override
            public void onCallback(Trip trip) {
                if (trip != null) {
                    plannedDays = 0;
                    calculatePlannedDaysFromTrip(trip);
                } else {
                    visualizePieChart(0, allottedDays);
                }
            }
        });
    }

    private void calculatePlannedDaysFromTrip(Trip trip) {
        List<String> destinationIds = trip.getDestinations();
        if (destinationIds != null && !destinationIds.isEmpty()) {
            for (String destinationId : destinationIds) {
                databaseManager.getDestinationByID(destinationId, new DestinationCallback() {
                    @Override
                    public void onCallback(Destination destination) {
                        if (destination != null) {
                            plannedDays += destination.duration();

                            // Visualize the chart after processing all destinations
                            if (destinationIds.indexOf(destinationId)
                                    == destinationIds.size() - 1) {
                                visualizePieChart(plannedDays, allottedDays);
                            }
                        } else {
                            Log.e("LogisticsActivity", "Destination data not found.");
                        }
                    }
                });
            }
        } else {
            visualizePieChart(0, allottedDays);
        }
    }

    private void visualizePieChart(int plannedDays, int allottedDays) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(plannedDays, "Planned Days"));
        entries.add(new PieEntry(Math.max(0, allottedDays - plannedDays), "Unplanned Days"));

        Legend legend = pieChart.getLegend();
        legend.setTextSize(16f);

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(Color.RED, Color.GREEN);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(20f);
        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_logistics;
    }
}