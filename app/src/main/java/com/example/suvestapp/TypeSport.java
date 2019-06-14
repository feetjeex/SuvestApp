package com.example.suvestapp;

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
