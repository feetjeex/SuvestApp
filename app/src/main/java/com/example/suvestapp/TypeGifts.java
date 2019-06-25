package com.example.suvestapp;

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

