package com.example.suvestapp;

/** Category
 *
 * Enum which contains constants representing all the Categories of items used in the application.
 */

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
