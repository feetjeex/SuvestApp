package com.example.suvestapp;

/** TypeWellness
 *
 * Enum which contains constants representing all the different Types of Wellness products used in the application.
 */

public enum TypeWellness {

    BODYCARE("Bodycare"), HAIRCARE("Haircare"), SKINCARE("Skincare");

    private String typewellness;

    TypeWellness(String typewellness) {
        this.typewellness = typewellness;
    }

    public String getTypewellness() {
        return typewellness;
    }
}
