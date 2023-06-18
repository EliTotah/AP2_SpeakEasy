package com.example.ap2_speakeasy.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @PrimaryKey
    private int id;
    private String content;
    private String created;
    // True if the message was sent by the current user
    private boolean sent;
    // The contact id of the user the current user is chatting with
    private int contactId;

    public Message(String content, String created, boolean sent, int contactId) {
        this.content = content;
        this.created = created;
        this.sent = sent;
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

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
