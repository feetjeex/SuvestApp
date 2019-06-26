package com.example.suvestapp;

/** TypeSport
 *
 * Enum which contains constants representing all the different Types of Sports products used in the application.
 */

public enum TypeSport {

    JACKETS("Jackets"), PANTS("Pants"), SHOES("Shoes"), SHORTS("Shorts"), SWIM("Swim"), TOPS("Tops"), GOLF("Golf"), RUNNING("Running"),
    FOOTBALL("Football");

    private String typesport;

    TypeSport(String typesport) {
        this.typesport = typesport;
    }

    public String getTypesport() {
        return typesport;
    }
}
