package com.example.ap2_speakeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ap2_speakeasy.API.ChatAPI;
import com.example.ap2_speakeasy.API.UserAPI;
import com.example.ap2_speakeasy.Sign_up.SignUpActivity;
import com.example.ap2_speakeasy.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    public static Context context;
    private UserAPI userAPI;

    private String userToken;
    private String activeUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userAPI = new UserAPI();
        // Initialize the ViewModel
        // Observe changes to the token value
        userAPI.getTokenLiveData().observe(this, token -> {
            if (token != null) {
                userToken = token;
                // Token value has changed, handle it here
                //Log.e("token", token);
//                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//                intent.putExtra("token", token);
//                startActivity(intent);
                // Save the token to preferences or use it as needed

            }
        });

        userAPI.getActiveUserName().observe(this, username -> {
            if (username != null) {
                activeUserName = username;
            }
        });
        binding.signup.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        });

        binding.dnthave.setOnClickListener(view -> {
            // Handle the click event here
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
                try {
                    userAPI.signIn(username, password, callback -> {
                        if (callback == 200) {
                            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                            Log.e("token", userToken);
                            Intent intent = new Intent(LoginActivity.this, ChatContactsActivity.class);
                            intent.putExtra("token", "Bearer " + userToken);
                            intent.putExtra("activeUserName", activeUserName);
                            startActivity(intent);
                        } else if (callback == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "details not correct", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}