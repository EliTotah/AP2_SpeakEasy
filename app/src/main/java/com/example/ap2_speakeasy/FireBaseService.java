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
import com.example.ap2_speakeasy.entities.Contact;
import com.example.ap2_speakeasy.entities.Message;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class FireBaseService extends FirebaseMessagingService {
    public FireBaseService() {
    }

    @SuppressLint("WrongThread")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        MutableLiveData<Message> messageMutableLiveData = SingeltonFireBase.getMessageFirebase();
        MutableLiveData<Contact> contactMutableLiveData = SingeltonFireBase.getContactFirebase();

        if (message.getNotification() != null) {
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(message.getNotification().getTitle())
                    .setContentText(message.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

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

            if(message.getData().isEmpty()) {
                return;
            }

            if(message.getData().get("action").equals("add_contact")) {
                contactMutableLiveData.postValue(message.getNotification().getBody());
            }
            else if (message.getData().get("action").equals("send_message")) {
                String content = message.getNotification().getBody();
                Map<String, String> sender = Map.of("username", message.getData().get("senderUserName"));
                String created = message.getData().get("data_date");
                String chatID = message.getData().get("chatId");
                int chatid = Integer.parseInt(chatID);

                //edit the date
                SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.US);
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

                // Parse the date string to a Date object
                Date date = new Date();
                try {
                    if (!created.isEmpty()) {
                        date = inputFormat.parse(created);
                    }
                } catch (ParseException e) {

                }
                String createdDate = outputFormat.format(date);

                Message m = new Message(content, createdDate, sender, chatid);
                messageMutableLiveData.postValue(m);
            }
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