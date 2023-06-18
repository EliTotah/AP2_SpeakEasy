package com.example.ap2_speakeasy.entities;

import android.widget.ImageView;
import androidx.room.PrimaryKey;

public class User {
    @PrimaryKey
    private String id;

    private String displayName;

    private String profilePic;

    public User(String id, String displayName, String profilePic) {
        this.id = id;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
