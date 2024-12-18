package com.example.sprintproject.viewmodel;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.databinding.ItemLayoutBinding;
import com.example.sprintproject.model.DatabaseManager;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.view.StringToDateConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DestinationListViewModel extends
        RecyclerView.Adapter<DestinationListViewModel.MyViewHolder> implements
        StringToDateConverter {

    private ArrayList<Destination> dataList;
    private static final String DATE_FORMAT = "MM-dd-yyyy";
    private DatabaseManager databaseManager;
    private MutableLiveData<Integer> totalDays = new MutableLiveData<>(0);

    public DestinationListViewModel() {
        databaseManager = DatabaseManager.getInstance();
        dataList = new ArrayList<>();
        for (String destinationID: databaseManager.getCurrentTrip().getDestinations()) {
            addDestination(destinationID, dataList);
        }
    }

    private void addDestination(String id, ArrayList<Destination> list) {
        databaseManager.getDestinationByID(id, new DestinationCallback() {
            @Override
            public void onCallback(Destination destination) {
                totalDays.setValue(totalDays.getValue() + destination.duration());
                System.out.println(totalDays.getValue());
                list.add(destination);
            }
        });
    }

    public Date convertStringToDate(String str) throws ParseException {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        df.setLenient(false);
        return df.parse(str);
    }

    public ArrayList<Destination> getDataList() {
        return dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutBinding binding = ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Destination model = dataList.get(position);
        holder.bind(model);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void add(String destination, String start, String end) {
        Date startDate;
        Date endDate;
        try {
            startDate = convertStringToDate(start);
            endDate = convertStringToDate(end);
        } catch (ParseException e) {
            return;
        }
        Destination dest = new Destination(destination, startDate, endDate);
        dataList.add(dest);
        totalDays.setValue(totalDays.getValue() + dest.duration());
        notifyItemInserted(dataList.size() - 1);
    }

    public int getTotalDuration() {
        int totalDays = 0;
        for (Destination destination : dataList) {
            try {
                totalDays += destination.duration();
            } catch (Exception e) {
                return -1;
            }
        }
        return totalDays;
    }

    public MutableLiveData<Integer> getTotalDays() {
        return totalDays;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemLayoutBinding binding;

        public MyViewHolder(ItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Destination data) {
            binding.setData(data);
            //binding.destinationName.setText(data.getDestination());
            //binding.destinationDays.setText(data.getNumDays());
            binding.executePendingBindings();
        }
    }
}
