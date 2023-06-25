package com.example.ap2_speakeasy.FireBase;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.ap2_speakeasy.Dao.AppDB;
import com.example.ap2_speakeasy.entities.Contact;
import com.example.ap2_speakeasy.entities.Message;

public class SingeltonFireBase {
    public static MutableLiveData<Message> messageFirebase;
    public static MutableLiveData<String> contactFirebase;


    public static synchronized MutableLiveData<Message> getMessageFirebase() {
        if (messageFirebase == null) {
            messageFirebase = new MutableLiveData<>();
        }
        return messageFirebase;
    }

    public static synchronized MutableLiveData<String> getContactFirebase() {
        if (contactFirebase == null) {
            contactFirebase = new MutableLiveData<>();
        }
        return contactFirebase;
    }



}
