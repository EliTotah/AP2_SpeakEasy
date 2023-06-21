package com.example.ap2_speakeasy;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.example.ap2_speakeasy.Sign_up.ContactInfoActivity;
import com.example.ap2_speakeasy.Sign_up.SignUpActivity;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingActivity extends AppCompatActivity {
    private EditText serverAddressEditText;
    private Switch darkModeSwitch;

    private Spinner languageSpinner;
    private SharedPreferences settingsSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        isReturn.getInstance().setIsReturn(false);
        serverAddressEditText = findViewById(R.id.server_address_edittext);
        darkModeSwitch = findViewById(R.id.darkModeSwitch);
        settingsSharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        loadSavedSettings();
        serverAddressEditText.setText(settingsSharedPreferences.getString("url", "http://10.0.2.2"));
        Button saveButton = findViewById(R.id.saveSettingsButton);
        saveButton.setOnClickListener(v -> saveServer());


        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            toggleNightMode(isChecked);
            //updateThemeButtonLabel();
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("dark_mode", isNightMode);
        editor.apply();
    }

    private void saveServer() {
        Pattern pattern = Pattern.compile("^(http|https)://");
        Matcher matcher = pattern.matcher(serverAddressEditText.getText().toString());

        if (!matcher.find()) {
            Toast.makeText(getApplicationContext(),
                    "Invalid URL, has to start with http / https",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        AP2_SpeakEasy ap2_speakEasy = new AP2_SpeakEasy();
        ap2_speakEasy.setString("server", serverAddressEditText.getText().toString());
        //ap2_speakEasy.getEditor().clear();
        //ap2_speakEasy.getEditor().apply();
        //SharedPreferences.Editor editor = settingsSharedPreferences.edit();
        isReturn.getInstance().setIsReturn(true);
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

}
