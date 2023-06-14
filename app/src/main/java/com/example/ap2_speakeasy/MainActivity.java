package com.example.ap2_speakeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ap2_speakeasy.Sign_up.SignUpActivity;

public class MainActivity extends AppCompatActivity {

    private static final int DELAY_TIME_MS = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Use a Handler to introduce a delay
        new Handler().postDelayed(() -> {
            // Start the LoginActivity after the delay

            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish(); // Optional: Close the MainActivity so the user can't go back to it
        }, DELAY_TIME_MS);
    }
}
