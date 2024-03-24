package com.kushan.abclab.model;

public class AppointmentInfo {
    private String date;
    private String price;

    // Constructor
    public AppointmentInfo(String date, String price) {
        this.date = date;
        this.price = price;
    }

    // Getters
    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }
}

