package com.example.ap2_speakeasy.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.ap2_speakeasy.converts.senderConverter;

import java.util.Map;

@Entity
@TypeConverters(senderConverter.class)
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private String created;
    // True if the message was sent by the current user
    private Map<String,String> sender;
    // The contact id of the user the current user is chatting with
    private int contactId;

    public Message(String content, String created, Map<String,String>  sender, int contactId) {
        this.content = content;
        this.created = created;
        this.sender = sender;
        this.contactId = contactId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Map<String,String>  getSender() {
        return sender;
    }

    public void setSent(Map<String,String>  sender) {
        this.sender = sender;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
