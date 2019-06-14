package com.example.suvestapp;

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
