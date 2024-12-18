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
import com.example.sprintproject.databinding.LoginscreenBinding;
import com.example.sprintproject.model.User;
import com.example.sprintproject.viewmodel.LogInViewModel;
import com.example.sprintproject.viewmodel.UserCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity {
    private LogInViewModel viewModel;
    private LoginscreenBinding binding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        mAuth = FirebaseAuth.getInstance();
        initView();
        initViewModel();

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.emailInput.getText() == null
                        || binding.passwordInput.getText() == null) {
                    Toast.makeText(LoginScreen.this, "Log in successful.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = binding.emailInput.getText().toString().trim();
                String password = binding.passwordInput.getText().toString().trim();
                if (email.contains(" ") || email.isEmpty() || password.contains(" ")
                        || password.isEmpty()) {
                    Toast.makeText(LoginScreen.this, "Log in unsuccessful.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginScreen.this,
                                new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI
                                        Log.d("LogIn", "signInWithEmail:success");
                                        viewModel.setCurrUser(mAuth.getCurrentUser(),
                                                    new UserCallBack() {
                                                @Override
                                                public void onCallback(User result) {
                                                    Intent intent = new Intent(LoginScreen.this,
                                                            DestinationActivity.class);
                                                    startActivity(intent);
                                                }
                                            });
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("LogIn", "signInWithEmail:failure",
                                                task.getException());
                                        Toast.makeText(LoginScreen.this,
                                                "Log in unsuccessful.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
            }
        });
        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Take to register screen
                Intent intent = new Intent(LoginScreen.this, RegistrationScreen.class);
                startActivity(intent);
            }
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(LogInViewModel.class);
    }

    private void initView() {
        binding = LoginscreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}
