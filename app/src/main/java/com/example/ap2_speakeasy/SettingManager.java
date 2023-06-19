package com.example.ap2_speakeasy;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.example.ap2_speakeasy.Dao.AppDB;

public  class SettingManager {

    private static SettingManager settingManager;

    public static synchronized SettingManager getSettingManager() {
        if (settingManager == null) {
            settingManager = new SettingManager();
        }
        return settingManager;
    }
        private static final String PREFS_NAME = "settings";

        private static SharedPreferences getSharedPreferences(Context context) {
            return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }

        public static boolean isNotificationSoundEnabled(Context context) {
            return getSharedPreferences(context).getBoolean("notification_sound_enabled", false);
        }

        public static boolean isVibrationEnabled(Context context) {
            return getSharedPreferences(context).getBoolean("vibration_enabled", false);
        }

        public static boolean isDarkModeEnabled(Context context) {
            return getSharedPreferences(context).getBoolean("dark_mode_enabled", false);
        }

        public static String getServerAddress(Context context) {
            return getSharedPreferences(context).getString("server_address", "");
        }

        public static void saveSettings(Context context, boolean notificationSoundEnabled,
                                        boolean vibrationEnabled, boolean darkModeEnabled, String serverAddress) {
            SharedPreferences.Editor editor = getSharedPreferences(context).edit();
            editor.putBoolean("notification_sound_enabled", notificationSoundEnabled);
            editor.putBoolean("vibration_enabled", vibrationEnabled);
            editor.putBoolean("dark_mode_enabled", darkModeEnabled);
            editor.putString("server_address", serverAddress);
            editor.apply();
        }

}
