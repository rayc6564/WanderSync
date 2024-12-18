package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.CommunityPost;

import java.util.ArrayList;

public interface CommunityPostCallback extends Callback<ArrayList<CommunityPost>> {
    @Override
    void onCallback(ArrayList<CommunityPost> result);
}
