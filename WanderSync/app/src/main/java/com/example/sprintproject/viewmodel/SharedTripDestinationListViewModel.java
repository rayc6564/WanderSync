package com.example.sprintproject.viewmodel;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.databinding.ItemLayoutBinding;
import com.example.sprintproject.model.Destination;
import java.util.ArrayList;

public class SharedTripDestinationListViewModel
        extends RecyclerView.Adapter<SharedTripDestinationListViewModel.MyViewHolder> {

    private ArrayList<Destination> dataList;
    private MutableLiveData<Integer> totalDays;

    public SharedTripDestinationListViewModel() {
        dataList = new ArrayList<>();
        totalDays.setValue(0);
    }

    @NonNull
    @Override
    public SharedTripDestinationListViewModel.MyViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        ItemLayoutBinding binding = ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SharedTripDestinationListViewModel.MyViewHolder holder,
                                 int position) {
        Destination model = dataList.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // Method assumes you have the Destination
    public void add(Destination dest) {
        dataList.add(dest);
        totalDays.setValue(totalDays.getValue() + dest.duration());
        notifyItemInserted(dataList.size() - 1);
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemLayoutBinding binding;

        public MyViewHolder(ItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Destination data) {
            binding.setData(data);
            binding.executePendingBindings();
        }
    }
}
