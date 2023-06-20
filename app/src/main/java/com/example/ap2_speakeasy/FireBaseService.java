package com.example.ap2_speakeasy;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_speakeasy.API.MessageAPI;
import com.example.ap2_speakeasy.Dao.MessageDao;
import com.example.ap2_speakeasy.entities.Message;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Map;

public class FireBaseService extends FirebaseMessagingService {
    private static final int NOTIFICATION_ID = 1;
    public FireBaseService() {
    }

    @SuppressLint("WrongThread")
    @Override
    public void onMessageReceived(RemoteMessage message) {
        MutableLiveData<Message> messageMutableLiveData = SingeltonFireBase.getMessageFirebase();
        if (message.getNotification() != null) {
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(message.getNotification().getTitle())
                    .setContentText(message.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            String content = message.getNotification().getBody();
            Map<String, String> sender = Map.of("username", message.getData().get("senderUserName"));
            String created = message.getData().get("data");
            String chatID = message.getData().get("chatId");
            int chatid = Integer.parseInt(chatID);

            Message m = new Message(content, created, sender, chatid);
            NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(this);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            notificationCompat.notify(1, builder.build());
            messageMutableLiveData.postValue(m);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("1", "my channel",importance);
            notificationChannel.setDescription("demo");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}