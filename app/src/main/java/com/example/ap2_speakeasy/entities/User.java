package com.example.ap2_speakeasy.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String displayName;
    private int picture;
    private String lastMassage;
    private String lastMassageSendingTime;

    public Contact(String displayName, int picture, String lastMassage, String lastMassageSendingTime) {
        this.displayName = displayName;
        this.picture = picture;
        this.lastMassage = lastMassage;
        this.lastMassageSendingTime = lastMassageSendingTime;
    }

    public int getPicture() {
        return picture;
    }

    public String getLastMassage() {
        return lastMassage;
    }

    public String getLastMassageSendingTime() {
        return lastMassageSendingTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
