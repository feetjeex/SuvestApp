package com.example.suvestapp;

import java.io.Serializable;

public class PreferenceHelper implements Serializable {

    private String type, color, retailer, format;

    public PreferenceHelper(String type, String color, String retailer, String format) {
        this.type = type;
        this.color = color;
        this.retailer = retailer;
        this.format = format;
    }

    public void preferenceHelperResetter () {
        this.color = "none";
        this.retailer = "none";
        this.format = "none";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
