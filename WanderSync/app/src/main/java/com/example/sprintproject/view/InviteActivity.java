package com.example.sprintproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;

import com.example.sprintproject.R;
import com.example.sprintproject.model.DatabaseManager;
import com.example.sprintproject.model.User;
import com.example.sprintproject.viewmodel.UserCallBack;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InviteActivity extends FeatureActivityBase {
    private DatabaseReference userDatabase;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_invite);
        super.onCreate(savedInstanceState);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userDatabase = FirebaseDatabase.getInstance().getReference("users");

        Button sendInviteButton = findViewById(R.id.sendInviteButton);
        EditText inviteEmail = findViewById(R.id.inviteEmail);

        sendInviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String invitedUserEmail = inviteEmail.getText().toString().trim();
                String id = currentUser.getUid();
                DatabaseManager.getInstance().getUserByUID(id, new UserCallBack() {
                    @Override
                    public void onCallback(User user) {
                        user.inviteUser(invitedUserEmail);
                    }
                });
                Intent intent = new Intent(InviteActivity.this, LogisticsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }
}
