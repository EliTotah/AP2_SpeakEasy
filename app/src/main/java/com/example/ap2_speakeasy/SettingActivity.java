package com.example.ap2_speakeasy;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
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
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity {
    private EditText serverAddressEditText;
    private Switch notificationSoundSwitch;
    private Switch vibrationSwitch;
    private Switch darkModeSwitch;
    private SharedPreferences settingsSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        serverAddressEditText = findViewById(R.id.server_address_edittext);
        notificationSoundSwitch = findViewById(R.id.notificationSoundSwitch);
        vibrationSwitch = findViewById(R.id.vibrationSwitch);
        darkModeSwitch = findViewById(R.id.darkModeSwitch);
        Spinner languageSpinner = findViewById(R.id.languageSpinner);

        loadSavedSettings();

        Button saveButton = findViewById(R.id.saveSettingsButton);
        saveButton.setOnClickListener(v -> saveSettings());

        notificationSoundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                enableNotificationSound();
            } else {
                disableNotificationSound();
            }
        });

        vibrationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                enableVibration();
            } else {
                disableVibration();
            }
        });

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            toggleNightMode(isChecked);
            //updateThemeButtonLabel();
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

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());
        settingsSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Set default values for the preferences (before creating a listener!)
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, true);
        SharedPreferences.OnSharedPreferenceChangeListener listener = (preferences, key) -> {
            if (key.equals("dark_mode")) {
                changeTheme(preferences.getBoolean(key, false));
            }
        };
        settingsSharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    private void changeTheme(boolean isNightMode) {
        if (isNightMode) {
            getDelegate().setLocalNightMode(MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(MODE_NIGHT_NO);
        }
    }

    private void saveSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("notification_sound_enabled", notificationSoundSwitch.isChecked());
        editor.putBoolean("vibration_enabled", vibrationSwitch.isChecked());
        editor.putBoolean("dark_mode_enabled", darkModeSwitch.isChecked());

        String url = serverAddressEditText.getText().toString();
        editor.putString("server_address", url);

        editor.apply();

        boolean isNotificationSoundEnabled = notificationSoundSwitch.isChecked();
        boolean isVibrationEnabled = vibrationSwitch.isChecked();

        if (isNotificationSoundEnabled) {
            enableNotificationSound();
        } else {
            disableNotificationSound();
        }

        if (isVibrationEnabled) {
            enableVibration();
        } else {
            disableVibration();
        }

        finish();
    }

    private void loadSavedSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        boolean notificationSoundEnabled = sharedPreferences.getBoolean("notification_sound_enabled", false);
        boolean vibrationEnabled = sharedPreferences.getBoolean("vibration_enabled", false);
        boolean darkModeEnabled = sharedPreferences.getBoolean("dark_mode_enabled", false);

        notificationSoundSwitch.setChecked(notificationSoundEnabled);
        vibrationSwitch.setChecked(vibrationEnabled);
        darkModeSwitch.setChecked(darkModeEnabled);
    }

    private void toggleNightMode(boolean is_Chacked) {
        SharedPreferences.Editor editor = settingsSharedPreferences.edit();
        if (is_Chacked) {
            editor.putBoolean("dark_mode", true);
        }
        editor.putBoolean("dark_mode", false);
        editor.apply();
    }

    private void updateThemeButtonLabel() {
        Button themeButton = findViewById(R.id.darkModeSwitch);
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            themeButton.setText("Night Mode");
        } else {
            themeButton.setText("Day Mode");
        }
    }

    private void updateLanguage(String languageCode) {
        String currentLanguage = Locale.getDefault().getLanguage();
        if (!currentLanguage.equals(languageCode)) {
            Locale newLocale = new Locale(languageCode);
            Locale.setDefault(newLocale);

            Resources res = getResources();
            Configuration config = res.getConfiguration();
            config.setLocale(newLocale);

            // Update the configuration of the current resources
            res.updateConfiguration(config, res.getDisplayMetrics());

            // Restart the activity to apply the new language
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }




    private void enableVibration() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            long[] pattern = {0, 400, 200, 400};
            vibrator.vibrate(pattern, -1);
        }
    }

    private void disableVibration() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.cancel();
        }
    }

    private void disableNotificationSound() {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
        }
    }

    private void enableNotificationSound() {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION), 0);
        }
    }
}
