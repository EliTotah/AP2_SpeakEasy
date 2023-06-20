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
    private Switch darkModeSwitch;

    private Spinner languageSpinner;
    private SharedPreferences settingsSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        serverAddressEditText = findViewById(R.id.server_address_edittext);
        darkModeSwitch = findViewById(R.id.darkModeSwitch);
        //languageSpinner = findViewById(R.id.languageSpinner);

        loadSavedSettings();

        Button saveButton = findViewById(R.id.saveSettingsButton);
        saveButton.setOnClickListener(v -> saveSettings());


        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            toggleNightMode(isChecked);
            //updateThemeButtonLabel();
        });

//        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedLanguage = parent.getItemAtPosition(position).toString();
//                if (selectedLanguage.equals("English")) {
//                    updateLanguage("en");
//                } else if (selectedLanguage.equals("Hebrew")) {
//                    updateLanguage("he");
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // Do nothing
//            }
//        });

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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("dark_mode", isNightMode);
        editor.apply();
    }

    private void saveSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //editor.putBoolean("dark_mode", darkModeSwitch.isChecked());

        String url = serverAddressEditText.getText().toString();
        editor.putString("server_address", url);

        editor.apply();



        finish();
    }

    private void loadSavedSettings() {
        SharedPreferences settingsSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkModeEnabled = settingsSharedPreferences.getBoolean("dark_mode", false);
        darkModeSwitch.setChecked(darkModeEnabled);
    }

    private void toggleNightMode(boolean isChecked) {
        SharedPreferences.Editor editor = settingsSharedPreferences.edit();
        editor.putBoolean("dark_mode", isChecked);
        editor.apply();
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
}
