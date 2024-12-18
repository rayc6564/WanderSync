package com.example.sprintproject.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.CommunityPost;
import com.example.sprintproject.model.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommunityListViewModel extends RecyclerView.Adapter<CommunityListViewModel.ViewHolder>
        implements Observable {

    private final ArrayList<CommunityPost> dataList;

    private final List<Observer> observers;
    private final DatabaseManager databaseManager;
    private final Context context;


    public CommunityListViewModel(Context context, ArrayList<CommunityPost> dataList) {
        this.context = context;
        this.databaseManager = DatabaseManager.getInstance();
        this.dataList = new ArrayList<>();
        this.observers = new ArrayList<>();
        databaseManager.getAllCommunityPosts(new CommunityPostCallback() {
            @Override
            public void onCallback(ArrayList<CommunityPost> result) {
                for (CommunityPost post : result) {
                    addCommunityPost(post);
                }
            }
        });
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

    public void updateList(ArrayList<CommunityPost> newPosts) {
        dataList.clear();
        dataList.addAll(newPosts);
        notifyObservers();
        notifyDataSetChanged();
    }

    public void addCommunityPost(CommunityPost post) {
        if (!dataList.contains(post)) {
            dataList.add(post);
            notifyObservers();
            notifyDataSetChanged();
        }
    }

    public void removeCommunityPost(CommunityPost post) {
        if (dataList.contains(post)) {
            dataList.remove(post);
            notifyObservers();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_item,
                parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommunityPost post = dataList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView startDateText;
        private final TextView endDateText;
        private final TextView destinationText;
        private final TextView accommodationsText;
        private final TextView diningReservationsText;
        private final TextView notesText;
        private final TextView durationText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            startDateText = itemView.findViewById(R.id.start_date_text);
            endDateText = itemView.findViewById(R.id.end_date_text);
            destinationText = itemView.findViewById(R.id.destination_text);
            accommodationsText = itemView.findViewById(R.id.accommodations_text);
            diningReservationsText = itemView.findViewById(R.id.dining_reservations_text);
            notesText = itemView.findViewById(R.id.notes_text);
            durationText = itemView.findViewById(R.id.duration_text);
        }

        public void bind(CommunityPost post) {
            startDateText.setText("Start: " + post.getStart());
            endDateText.setText("End: " + post.getEnd());
            destinationText.setText("Destination: " + post.getDestinations());
            accommodationsText.setText("Accommodations: " + post.getAccommodations());
            diningReservationsText.setText("Dining: " + post.getDining());
            notesText.setText("Notes: " + post.getNotes());
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                Date startDate = sdf.parse(post.getStart());
                Date endDate = sdf.parse(post.getEnd());

                if (startDate != null && endDate != null) {
                    long durationInMillis = endDate.getTime() - startDate.getTime();
                    int durationInDays = (int) (durationInMillis / (1000 * 60 * 60 * 24));
                    durationText.setText("Duration: " + durationInDays + " days");
                } else {
                    durationText.setText("Duration: N/A");
                }
            } catch (Exception e) {
                durationText.setText("Duration: N/A");
            }
        }
    }
}