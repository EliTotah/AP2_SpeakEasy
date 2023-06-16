package com.example.ap2_speakeasy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.ap2_speakeasy.Sign_up.SignUpActivity;
import com.example.ap2_speakeasy.databinding.ActivitySettingBinding;

import java.lang.reflect.Field;
import java.util.Locale;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;
    private EditText serverAddressEditText;
    private Switch notificationSoundSwitch;
    private Switch vibrationSwitch;
    private Switch darkModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        serverAddressEditText = binding.serverAddressEdittext;
        notificationSoundSwitch = binding.notificationSoundSwitch;
        vibrationSwitch = binding.vibrationSwitch;
        darkModeSwitch = binding.darkModeSwitch;
        Spinner languageSpinner = binding.languageSpinner;

        loadSavedSettings();

        Button saveButton = binding.saveSettingsButton;
        saveButton.setOnClickListener(v -> saveSettings());

        notificationSoundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle notification sound switch state change
            // isChecked will be true if the switch is turned on
            // Perform necessary actions based on the state
        });

        vibrationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle vibration switch state change
            // isChecked will be true if the switch is turned on
            // Perform necessary actions based on the state
        });

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle dark mode switch state change
            // isChecked will be true if the switch is turned on
            // Perform necessary actions based on the state
            toggleNightMode();
            updateThemeButtonLabel();
        });

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                if (selectedLanguage.equals("English")) {
                    updateLanguage("en");
                } else if (selectedLanguage.equals("Hebrew")) {
                    updateLanguage("he");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        ImageButton backButton = binding.backButton;
        backButton.setOnClickListener(v -> onBackPressed());
    }
    private void saveSettings() {
        // Save the settings to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("notification_sound_enabled", notificationSoundSwitch.isChecked());
        editor.putBoolean("vibration_enabled", vibrationSwitch.isChecked());
        editor.putBoolean("dark_mode_enabled", darkModeSwitch.isChecked());

        editor.apply();

        // Execute the method for each change
        if (notificationSoundSwitch.isChecked()) {
            // Method for notification sound enabled
            //performActionForNotificationSoundEnabled();
        } else {
            // Method for notification sound disabled
            //performActionForNotificationSoundDisabled();
        }

        if (vibrationSwitch.isChecked()) {
            // Method for vibration enabled
            //performActionForVibrationEnabled();
        } else {
            // Method for vibration disabled
            //performActionForVibrationDisabled();
        }

        if (darkModeSwitch.isChecked()) {
            // Method for dark mode enabled
            //performActionForDarkModeEnabled();
        } else {
            // Method for dark mode disabled
            //performActionForDarkModeDisabled();
        }

        // Get the server address from the EditText
        String url = serverAddressEditText.getText().toString();
        ServerUrl.getInstance().setUrl(url);

        // Create a new intent to the Sign-up activity
        Intent intent = new Intent(SettingActivity.this, SignUpActivity.class);
        // Add any necessary extras to the intent
        startActivity(intent);

        // Finish the current activity
        finish();
    }

    private void loadSavedSettings() {
        // Load saved settings from SharedPreferences and update the switches accordingly
        // You can use SharedPreferences to store and retrieve the switch states
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        boolean notificationSoundEnabled = sharedPreferences.getBoolean("notification_sound_enabled", false);
        boolean vibrationEnabled = sharedPreferences.getBoolean("vibration_enabled", false);
        boolean darkModeEnabled = sharedPreferences.getBoolean("dark_mode_enabled", false);

        notificationSoundSwitch.setChecked(notificationSoundEnabled);
        vibrationSwitch.setChecked(vibrationEnabled);
        darkModeSwitch.setChecked(darkModeEnabled);
    }

//    private void saveSettings() {
//        // Save the settings to SharedPreferences
//        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        editor.putBoolean("notification_sound_enabled", notificationSoundSwitch.isChecked());
//        editor.putBoolean("vibration_enabled", vibrationSwitch.isChecked());
//        editor.putBoolean("dark_mode_enabled", darkModeSwitch.isChecked());
//
//        editor.apply();
//
//        String url = serverAddressEditText.getText().toString();
//        ServerUrl.getInstance().setUrl(url);
//        onBackPressed();
//    }

    private void toggleNightMode() {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void updateThemeButtonLabel() {
        Button themeButton = binding.darkModeSwitch;
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            themeButton.setText("Night Mode");
        } else {
            themeButton.setText("Day Mode");
        }
    }

    private void updateLanguage(String languageCode) {
        // Update the app's language based on the selected language code
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        // Restart the activity to apply the language changes
        recreate();
    }
}
