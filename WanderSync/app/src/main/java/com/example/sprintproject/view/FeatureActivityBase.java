package com.example.sprintproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sprintproject.R;

public abstract class FeatureActivityBase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout (each subclass will set its own layout in onCreate)
        //setContentView(getLayoutResourceId());

        // Set up the navigation bar with listeners
        setupNavigationBar();
    }

    // Method for subclasses to provide their specific layout
    protected abstract int getLayoutResourceId();

    // Common method to set up navigation for all feature screens
    private void setupNavigationBar() {
        ImageView logisticsButton = findViewById(R.id.nav_logistics);
        ImageView destinationButton = findViewById(R.id.nav_destination);
        ImageView diningButton = findViewById(R.id.nav_dining);
        ImageView accommodationsButton = findViewById(R.id.nav_accommodations);
        ImageView transportationButton = findViewById(R.id.nav_transportation);
        ImageView communityButton = findViewById(R.id.nav_community);

        // Listener for Logistics Button
        logisticsButton.setOnClickListener(v -> {
            Intent intent = new Intent(FeatureActivityBase.this, LogisticsActivity.class);
            startActivity(intent);
        });

        // Listener for Destination Button
        destinationButton.setOnClickListener(v -> {
            Intent intent = new Intent(FeatureActivityBase.this, DestinationActivity.class);
            startActivity(intent);
        });

        // Listener for Dining Button
        diningButton.setOnClickListener(v -> {
            Intent intent = new Intent(FeatureActivityBase.this, DiningActivity.class);
            startActivity(intent);
        });

        // Listener for Accommodations Button
        accommodationsButton.setOnClickListener(v -> {
            Intent intent = new Intent(FeatureActivityBase.this, AccommodationsActivity.class);
            startActivity(intent);
        });

        // Listener for Transportation Button
        transportationButton.setOnClickListener(v -> {
            Intent intent = new Intent(FeatureActivityBase.this, TransportationActivity.class);
            startActivity(intent);
        });

        // Listener for Community Button
        communityButton.setOnClickListener(v -> {
            Intent intent = new Intent(FeatureActivityBase.this, CommunityActivity.class);
            startActivity(intent);
        });
    }
}
