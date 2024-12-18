package com.example.sprintproject.viewmodel;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.DatabaseManager;
import com.example.sprintproject.model.Dining;
import com.example.sprintproject.model.DiningSortStrategy;
import com.example.sprintproject.model.SortByDiningName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiningListViewModel extends RecyclerView.Adapter<DiningListViewModel.ViewHolder>
        implements Observable {

    private final ArrayList<Dining> dataList;
    private final List<Observer> observers;
    private final Context context;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm",
            Locale.getDefault());
    private final DatabaseManager databaseManager;
    private DiningSortStrategy sortStrategy = new SortByDiningName(); // Default sorts by date

    public DiningListViewModel(Context context) {
        this.context = context;
        databaseManager = DatabaseManager.getInstance();
        this.dataList = new ArrayList<>();
        this.observers = new ArrayList<>();
        for (String diningID : databaseManager.getCurrentTrip().getDining()) {
            addDining(diningID);
        }
    }

    private void addDining(String id) {
        databaseManager.getDiningByID(id, new DiningCallback() {
            @Override
            public void onCallback(Dining dining) {
                ArrayList<Dining> list = new ArrayList<>();
                list.add(dining);
                updateList(list);
            }
        });
    }

    public ArrayList<Dining> getDataList() {
        return dataList;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.updateList();
        }
    }

    public void updateList(ArrayList<Dining> newDinings) {
        for (Dining diningData : newDinings) {
            if (!dataList.contains(diningData)) {
                dataList.add(diningData);
            }
        }
        applySortStrategy();
        notifyObservers();
    }

    public void setSortStrategy(DiningSortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
        applySortStrategy();
    }

    public void applySortStrategy() {
        if (sortStrategy != null) {
            sortStrategy.sort(dataList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public DiningListViewModel.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dining_item, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiningListViewModel.ViewHolder holder, int position) {
        Dining dining = dataList.get(position);
        holder.bind(dining);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView diningNameText;
        private final TextView locationText;
        private final TextView websiteText;
        private final TextView reservationDateText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            diningNameText = itemView.findViewById(R.id.dining_name_text);
            locationText = itemView.findViewById(R.id.dining_location_text);
            websiteText = itemView.findViewById(R.id.dining_website_text);
            reservationDateText = itemView.findViewById(R.id.dining_reservation_date_text);
        }

        public void bind(Dining dining) {
            diningNameText.setText(dining.getRestaurantName());
            locationText.setText("Location: " + dining.getLocation());
            websiteText.setText("Website: " + dining.getWebsite());
            reservationDateText.setText("Reservation: " + dining.getReservationDate() + " "
                    + dining.getReservationTime());

            // Apply color based on whether the reservation is expired
            applyDateTextColor(dining);
        }

        private void applyDateTextColor(Dining dining) {
            try {
                Date reservationDate = dateFormat.parse(dining.getReservationDate()
                        + " " + dining.getReservationTime());
                if (reservationDate != null && reservationDate.before(new Date())) {
                    setTextColor(Color.RED);  // Expired reservation
                } else {
                    setTextColor(Color.BLACK);  // Upcoming reservation
                }
            } catch (ParseException e) {
                Toast.makeText(itemView.getContext(), "Date Format Error: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }

        private void setTextColor(int color) {
            diningNameText.setTextColor(color);
            locationText.setTextColor(color);
            websiteText.setTextColor(color);
            reservationDateText.setTextColor(color);
        }
    }
}