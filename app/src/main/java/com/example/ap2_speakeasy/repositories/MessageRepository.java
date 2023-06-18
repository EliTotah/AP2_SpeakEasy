package com.example.ap2_speakeasy.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;

import com.example.ap2_speakeasy.API.ChatAPI;
import com.example.ap2_speakeasy.API.MessageAPI;
import com.example.ap2_speakeasy.Dao.AppDB;
import com.example.ap2_speakeasy.Dao.ContactDao;
import com.example.ap2_speakeasy.Dao.MessageDao;
import com.example.ap2_speakeasy.DatabaseManager;
import com.example.ap2_speakeasy.entities.Contact;
import com.example.ap2_speakeasy.entities.Message;

import java.util.List;

public class MessageRepository {
    private MessageDao messageDao;
    private MessageListData messageListData;
    private MessageAPI messageAPI;
    private String token;
    private String id;
    private int chatID;
    private AppDB db;

    public MessageRepository(String token, int chatID) {
        this.token = token;
        this.db = DatabaseManager.getDatabase();
        this.messageDao = db.messageDao();
        this.messageListData = new MessageListData();
        this.messageAPI = new MessageAPI();
        this.chatID = chatID;
    }

    public LiveData<List<Message>> getAll() {
        return messageListData;
    }
    public void insertMessage(String content) {
        messageAPI.createMessage(token,chatID,content);
        messageAPI.getMessages(messageListData,token, chatID);
    }

    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData() {
            super();
            setValue(messageDao.getAllMessagesWithContact(chatID));
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                messageAPI.getMessages(this,token, chatID);
            }).start();
        }
    }
}
