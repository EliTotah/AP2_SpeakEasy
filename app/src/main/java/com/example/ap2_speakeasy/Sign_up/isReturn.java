package com.example.ap2_speakeasy.Sign_up;


public class isReturn {
    private static isReturn instance;
    private boolean isReturn = false;

    private isReturn() {
        // Private constructor to prevent external instantiation
    }

    public static synchronized isReturn getInstance() {
        if (instance == null) {
            instance = new isReturn();
        }
        return instance;
    }

    public boolean getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(boolean isReturn) {
        this.isReturn=isReturn;
    }
}

