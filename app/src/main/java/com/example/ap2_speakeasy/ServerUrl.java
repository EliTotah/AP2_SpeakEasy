package com.example.ap2_speakeasy;


public class ServerUrl {
    private static ServerUrl instance;
    private String url = "http://10.0.2.2:5000/api/";

    private ServerUrl() {
        // Private constructor to prevent external instantiation
    }

    public static synchronized ServerUrl getInstance() {
        if (instance == null) {
            instance = new ServerUrl();
        }
        return instance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url=url;
}
}
