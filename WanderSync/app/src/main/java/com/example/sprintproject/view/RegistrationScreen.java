package com.example.sprintproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.example.sprintproject.databinding.RegistrationscreenBinding;
import com.example.sprintproject.model.User;
import com.example.sprintproject.viewmodel.RegistrationViewModel;
import com.example.sprintproject.viewmodel.UserCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationScreen extends AppCompatActivity {

    private RegistrationViewModel viewModel;
    private RegistrationscreenBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrationscreen);
        mAuth = FirebaseAuth.getInstance();
        initView();
        initViewModel();

        binding.newRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.emailRegister.getText() == null
                         || binding.passwordRegister.getText() == null) {
                    Toast.makeText(RegistrationScreen.this, "Log in unsuccessful.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = binding.emailRegister.getText().toString().trim();
                String password = binding.passwordRegister.getText().toString().trim();
                if (email.contains(" ") || email.isEmpty() || password.contains(" ")
                        || password.isEmpty()) {
                    Toast.makeText(RegistrationScreen.this, "Registration unsuccessful.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistrationScreen.this,
                                new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI
                                        Log.d("Registration", "signInWithEmail:success");
                                        viewModel.setCurrUser(mAuth.getCurrentUser(),
                                                new UserCallBack() {
                                                    @Override
                                                    public void onCallback(User result) {
                                                        Intent intent = new Intent(
                                                                RegistrationScreen.this,
                                                                DestinationActivity.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("Registration", "signInWithEmail:failure",
                                                task.getException());
                                        Toast.makeText(RegistrationScreen.this,
                                                "Registration unsuccessful.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
            }
        });
        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
    }

    private void initView() {
        binding = RegistrationscreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}
