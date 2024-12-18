package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.CommunityPost;
import com.example.sprintproject.model.DatabaseManager;

import java.util.ArrayList;

public class CommunityViewModel extends ViewModel {

    private DatabaseManager databaseManager;
    private final MutableLiveData<ArrayList<CommunityPost>> communityPostList;

    public CommunityViewModel() {
        communityPostList = new MutableLiveData<>(new ArrayList<>());
        databaseManager = DatabaseManager.getInstance();
        databaseManager.getAllCommunityPosts(new CommunityPostCallback() {
            @Override
            public void onCallback(ArrayList<CommunityPost> result) {
                communityPostList.setValue(result);
            }
        });
    }

    // Getter for LiveData to observe the list of travel posts
    public LiveData<ArrayList<CommunityPost>> getTravelPosts() {
        return communityPostList;
    }

    // Add a new CommunityPost to the list
    public void addTravelPostToList(CommunityPost post) {
        ArrayList<CommunityPost> currentList = new ArrayList<>(communityPostList.getValue());
        currentList.add(post);
        communityPostList.setValue(currentList); // Update LiveData
        databaseManager.addCommunityPost(post);

    }
}