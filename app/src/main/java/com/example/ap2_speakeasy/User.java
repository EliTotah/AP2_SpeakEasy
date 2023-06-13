package com.example.ap2_speakeasy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userName;
    private int pictureId;
    private String lastMassage;
    private String lastMassageSendingTime;

    public User(String userName, int pictureId, String lastMassage, String lastMassageSendingTime) {
        this.userName = userName;
        this.pictureId = pictureId;
        this.lastMassage = lastMassage;
        this.lastMassageSendingTime = lastMassageSendingTime;
    }

    public int getPictureId() {
        return pictureId;
    }

    public String getLastMassage() {
        return lastMassage;
    }

    public String getLastMassageSendingTime() {
        return lastMassageSendingTime;
    }

    public String getUserName() {
        return userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
