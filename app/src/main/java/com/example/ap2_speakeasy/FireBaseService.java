package com.example.ap2_speakeasy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FireBaseService extends FirebaseMessagingService {
    public FireBaseService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        //super.onMessageReceived(message);
    }
}