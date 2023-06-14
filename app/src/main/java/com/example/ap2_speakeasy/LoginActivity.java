package com.example.ap2_speakeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ap2_speakeasy.API.UserAPI;
import com.example.ap2_speakeasy.Sign_up.SignUpActivity;
import com.example.ap2_speakeasy.databinding.ActivityChatContactsBinding;
import com.example.ap2_speakeasy.databinding.ActivityLoginBinding;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signup.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        });

        binding.loginButton.setOnClickListener(view -> {
            // Get username and password from the UI
            String username = binding.editTextUsername.getText().toString();
            String password = binding.editTextPassword.getText().toString();
            if (username.isEmpty()) {
                // Show error message
                binding.editTextUsername.setError("Username is empty");
            } else if (password.isEmpty()) {
                // Show error message
                binding.editTextPassword.setError("Password is empty");
            } else {
                UserAPI userAPI = new UserAPI();
                userAPI.login(username,password);
            }
        });
    }
}