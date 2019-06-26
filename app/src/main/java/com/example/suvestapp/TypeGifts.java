package com.example.suvestapp;

/** TypeGifts
 *
 * Enum which contains constants representing all the different Types of Gifts used in the application.
 */

public enum TypeGifts {

    TOILETRIES("Toiletries"), MUGS("Mugs"), CANDLES("Candles");

    private String typegifts;

    TypeGifts(String typegifts) {
        this.typegifts = typegifts;
    }

    public String getTypegifts() {
        return typegifts;
    }
}

