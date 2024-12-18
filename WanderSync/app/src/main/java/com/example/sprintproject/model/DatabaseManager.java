package com.example.sprintproject.model;



import androidx.annotation.NonNull;

import com.example.sprintproject.viewmodel.AccomidationCallback;
import com.example.sprintproject.viewmodel.CommunityPostCallback;
import com.example.sprintproject.viewmodel.DestinationCallback;
import com.example.sprintproject.viewmodel.DiningCallback;
import com.example.sprintproject.viewmodel.TripCallBack;
import com.example.sprintproject.viewmodel.UserCallBack;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseManager {
    private static volatile DatabaseManager databaseManager;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy",
            Locale.getDefault());

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private User loggedInUser;
    private Trip currentTrip;

    public static DatabaseManager getInstance() {
        if (databaseManager == null) {
            synchronized (DatabaseReference.class) {
                if (databaseManager == null) {
                    databaseManager = new DatabaseManager();
                }
            }
        }
        return databaseManager;
    }

    private DatabaseManager() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public void addCommunityPost(CommunityPost communityPost) {
        DatabaseReference post = this.databaseReference.child("communityposts");
        String key = post.push().getKey();
        communityPost.setID(key);
        post.child(key).setValue(communityPost);
    }

    private void addToTrip(HasID obj, String child) {
        DatabaseReference db = this.databaseReference.child(child);
        String key = db.push().getKey();
        obj.setID(key);
        db.child(key).setValue(obj);
        currentTrip.addDestination(key);
        databaseReference.child("trips").child(currentTrip.getID())
                .child(child).child(key).setValue(key);
    }

    public void addNotes(String notes) {
        databaseReference.child("trips").child(currentTrip.getID())
                .child("notes").setValue(notes);
    }

    public void addAccommodation(Accommodation accommodation) {
        addToTrip(accommodation, "accommodations");
    }

    public void addDining(Dining dining) {
        addToTrip(dining, "dinings");
    }

    public void addDestination(Destination destination) {
        addToTrip(destination, "destinations");
    }

    public void addUserToTrip(User user) {
        databaseReference.child("trips").child(currentTrip.getID())
                .child("users").child(user.getID()).setValue(user.getID());
        databaseReference.child("trips").child(user.getTrip()).child("users")
                .child(user.getID()).removeValue();
        databaseReference.child("users").child(user.getID()).child("trip")
                .setValue(currentTrip.getID());
    }

    public void createTrip(Trip trip, String initUser, TripCallBack callBack) {
        setCurrentTrip(trip);
        DatabaseReference trips = this.databaseReference.child("trips");
        String key = trips.push().getKey();
        trip.setID(key);
        trips.child(key).setValue(trip);
        databaseReference.child("trips").child(currentTrip.getID())
                .child("users").child(initUser).setValue(initUser);
        callBack.onCallback(trip);
    }

    public void addUser(User user) {
        databaseReference.child("users").child(user.getID()).setValue(user);
    }

    public void loginUser(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    public Trip getCurrentTrip() {
        return currentTrip;
    }

    public void setCurrentTrip(Trip currentTrip) {
        this.currentTrip = currentTrip;
    }

    public void setVacationTime(Date startDate, Date endDate, int duration) {
        loggedInUser.setStartDate(startDate);
        loggedInUser.setEndDate(endDate);
        loggedInUser.setDuration(duration);
        databaseReference.child("users").child(loggedInUser.getID()).setValue(loggedInUser);
    }

    public void getDestinationByID(String id, final DestinationCallback destinationCallback) {
        DatabaseReference destinations = databaseReference.child("destinations");
        Query query = destinations.orderByChild("id").equalTo(id).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Destination destination;
                try {
                    destination = new Destination(snapshot.child(id).child("location")
                            .getValue(String.class),
                            snapshot.child(id).child("startDate").getValue(String.class),
                            snapshot.child(id).child("endDate").getValue(String.class));
                } catch (ParseException e) {
                    return;
                }
                destinationCallback.onCallback(destination);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }
    public void getUserByUID(String uid, final UserCallBack userCallBack) {
        DatabaseReference users = databaseReference.child("users");
        Query query = users.orderByChild("id").equalTo(uid).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = new User(snapshot.child(uid).getKey(),
                        snapshot.child(uid).child("email").getValue(String.class),
                        snapshot.child(uid).child("startDate").getValue(String.class),
                        snapshot.child(uid).child("endDate").getValue(String.class),
                        snapshot.child(uid).child("duration").getValue(Integer.class),
                        snapshot.child(uid).child("trip").getValue(String.class));
                userCallBack.onCallback(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    public void getUserByEmail(String email, final UserCallBack userCallBack) {
        DatabaseReference users = databaseReference.child("users");
        Query query = users.orderByChild("email").equalTo(email).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String uid = userSnapshot.getKey();
                    ArrayList<String> destinations = new ArrayList<>();
                    for (DataSnapshot destSnapshot
                            : snapshot.child(uid).child("destinations").getChildren()) {
                        destinations.add(destSnapshot.getKey());
                    }
                    User user = new User(snapshot.child(uid).getKey(),
                            snapshot.child(uid).child("email").getValue(String.class),
                            snapshot.child(uid).child("startDate").getValue(String.class),
                            snapshot.child(uid).child("endDate").getValue(String.class),
                            snapshot.child(uid).child("duration").getValue(Integer.class),
                            snapshot.child(uid).child("trip").getValue(String.class));
                    userCallBack.onCallback(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    public void getTrip(String id, final TripCallBack tripCallBack) {
        DatabaseReference users = databaseReference.child("trips");
        Query query = users.orderByChild("id").equalTo(id).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot tripSnapshot : snapshot.getChildren()) {
                    String tripID = tripSnapshot.getKey();
                    ArrayList<String> destinations = new ArrayList<>();
                    for (DataSnapshot destSnapshot
                            : snapshot.child(tripID).child("destinations").getChildren()) {
                        destinations.add(destSnapshot.getKey());
                    }
                    ArrayList<String> accommodations = new ArrayList<>();
                    for (DataSnapshot destSnapshot
                            : snapshot.child(tripID).child("accommodations").getChildren()) {
                        accommodations.add(destSnapshot.getKey());
                    }
                    ArrayList<String> dining = new ArrayList<>();
                    for (DataSnapshot destSnapshot
                            : snapshot.child(tripID).child("dinings").getChildren()) {
                        dining.add(destSnapshot.getKey());
                    }
                    ArrayList<String> users = new ArrayList<>();
                    for (DataSnapshot destSnapshot
                            : snapshot.child(tripID).child("users").getChildren()) {
                        users.add(destSnapshot.getKey());
                    }
                    String notes = snapshot.child(tripID).child("notes").getValue(String.class);
                    Trip trp = new Trip(tripID, notes, destinations, accommodations, dining, users);
                    tripCallBack.onCallback(trp);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    public void getAllCommunityPosts(CommunityPostCallback listener) {
        ArrayList<CommunityPost> list = new ArrayList<>();
        databaseReference.child("communityposts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            CommunityPost communityPost = snapshot.getValue(CommunityPost.class);
                            list.add(communityPost);
                        }
                        listener.onCallback(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }



    public void getAccommodationByID(String id, final AccomidationCallback accomidationCallback) {
        DatabaseReference accommodations = databaseReference.child("accommodations");
        Query query = accommodations.orderByChild("id").equalTo(id).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Date checkIn = new Date(
                        snapshot.child(id).child("checkInDate").child("year").
                                getValue(Integer.class),
                        snapshot.child(id).child("checkInDate").child("month").
                                getValue(Integer.class),
                        snapshot.child(id).child("checkInDate").child("date").
                                getValue(Integer.class));
                Date checkOut = new Date(
                        snapshot.child(id).child("checkOutDate").child("year").
                                getValue(Integer.class),
                        snapshot.child(id).child("checkOutDate").child("month").
                                getValue(Integer.class),
                        snapshot.child(id).child("checkOutDate").child("date").
                                getValue(Integer.class));
                Accommodation accommodation = new Accommodation(
                        snapshot.child(id).child("hotelName").getValue(String.class),
                        checkIn, checkOut,
                        snapshot.child(id).child("location").getValue(String.class),
                        snapshot.child(id).child("numsRooms").getValue(String.class),
                        snapshot.child(id).child("roomType").getValue(String.class));
                accomidationCallback.onCallback(accommodation);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    public void getDiningByID(String id, final DiningCallback diningCallback) {
        DatabaseReference accommodations = databaseReference.child("dinings");
        Query query = accommodations.orderByChild("id").equalTo(id).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Dining dining;
                try {
                    Date date = TimeConverter.stringToDate(snapshot.child(id).
                            child("reservationDate").getValue(String.class));
                    Date time = TimeConverter.stringToTime(snapshot.child(id).
                            child("reservationTime").getValue(String.class));
                    dining = new Dining(
                            snapshot.child(id).child("restaurantName").getValue(String.class),
                            snapshot.child(id).child("location").getValue(String.class),
                            date, time,
                            snapshot.child(id).child("website").getValue(String.class),
                            snapshot.child(id).child("review").getValue(String.class));
                } catch (ParseException ignored) {
                    return;
                }

                diningCallback.onCallback(dining);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }
}