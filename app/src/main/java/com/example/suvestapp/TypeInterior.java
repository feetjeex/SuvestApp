package com.example.suvestapp;

public enum TypeInterior {

    LIVINGROOM("Living Room"), BEDROOM("Bedroom"), KITCHEN("Kitchen"), GARDEN("Garden");

    private String typeinterior;

    TypeInterior(String typeinterior) {
        this.typeinterior = typeinterior;
    }

    public String getTypeinterior() {
        return typeinterior;
    }
}
