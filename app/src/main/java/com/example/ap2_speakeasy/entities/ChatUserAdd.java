package com.example.ap2_speakeasy.entities;

public class ChatUserAdd {
    private int id;
    private User User;

    public ChatUserAdd(int id, User user) {
        this.id = id;
        User = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }
}
