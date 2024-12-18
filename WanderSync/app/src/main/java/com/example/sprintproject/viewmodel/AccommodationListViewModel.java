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
import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.DatabaseManager;
import com.example.sprintproject.model.TimeConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccommodationListViewModel
        extends RecyclerView.Adapter<AccommodationListViewModel.ViewHolder>
        implements Observable {

    private final ArrayList<Accommodation> dataList;
    private final List<Observer> observers;
    private final Context context;
    private final DatabaseManager databaseManager;
    private SortingStrategy sortingStrategy;

    public AccommodationListViewModel(Context context) {
        this.databaseManager = DatabaseManager.getInstance();
        this.context = context;
        this.dataList = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.sortingStrategy = null;
        for (String accommodationID: databaseManager.getCurrentTrip().getAccomidations()) {
            addAccommodation(accommodationID);
        }
    }

    private void addAccommodation(String id) {
        databaseManager.getAccommodationByID(id, new AccomidationCallback() {
            @Override
            public void onCallback(Accommodation accommodation) {
                ArrayList<Accommodation> list = new ArrayList<>();
                list.add(accommodation);
                updateList(list);
            }
        });
    }

    public List<Accommodation> getDataList() {
        return new ArrayList<>(dataList);
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

    public void updateList(ArrayList<Accommodation> newAccommodations) {
        for (Accommodation accommodationData : newAccommodations) {
            if (!dataList.contains(accommodationData)) {
                dataList.add(accommodationData);
            }
        }

        notifyObservers();
    }

    public void setSorting(SortingStrategy strategy) {
        this.sortingStrategy = strategy;
    }

    public void sort() {
        if (sortingStrategy != null) {
            sortingStrategy.sort(dataList);
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Sorting error", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public AccommodationListViewModel.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.accommodation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Accommodation accommodation = dataList.get(position);
        holder.bind(accommodation);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView hotelNameText;
        private final TextView locationText;
        private final TextView checkInText;
        private final TextView checkOutText;
        private final TextView numRoomsText;
        private final TextView roomTypeText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelNameText = itemView.findViewById(R.id.hotel_name_text);
            checkInText = itemView.findViewById(R.id.checkin_text);
            checkOutText = itemView.findViewById(R.id.checkout_text);
            locationText = itemView.findViewById(R.id.location_text);
            numRoomsText = itemView.findViewById(R.id.num_rooms_text);
            roomTypeText = itemView.findViewById(R.id.room_type_text);
        }

        public void bind(Accommodation accommodation) {
            hotelNameText.setText("Hotel: " + accommodation.getHotelName());
            checkInText.setText("Check-in: "
                    + TimeConverter.dateToString(accommodation.getCheckInDate()));
            checkOutText.setText("Check-out: "
                    + TimeConverter.dateToString(accommodation.getCheckOutDate()));
            locationText.setText("Location: " + accommodation.getLocation());
            numRoomsText.setText("Number of Rooms: " + accommodation.getNumsRooms());
            roomTypeText.setText("Room Type: " + accommodation.getRoomType());

            dateTextColorLogic(accommodation);
        }


        // red means expire, black is not
        private void dateTextColorLogic(Accommodation accommodation) {
            try {
                Date checkIn = accommodation.getCheckInDate();
                if (checkIn.before(new Date())) {
                    textColor(Color.RED);
                } else {
                    textColor(Color.BLACK);
                }
            } catch (Exception e) {
                Toast.makeText(itemView.getContext(), "Error, date format not working: "
                        + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        private void textColor(int color) {
            hotelNameText.setTextColor(color);
            locationText.setTextColor(color);
            checkInText.setTextColor(color);
            checkOutText.setTextColor(color);
            numRoomsText.setTextColor(color);
            roomTypeText.setTextColor(color);
        }
    }
}
