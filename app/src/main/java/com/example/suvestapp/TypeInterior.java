package com.example.suvestapp;

/** TypeInterior
 *
 * Enum which contains constants representing all the different Types of Interior products used in the application.
 */

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
