package com.example.suvestapp;

public enum Category {

    CLOTHING("Clothing"), SHOES("Shoes"), ACCESSORIES("Accessories"), GIFTS("Gifts"), SPORT("Sport"), WELLNESS("Wellness"), INTERIOR("Interior");

    private String category;

    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
