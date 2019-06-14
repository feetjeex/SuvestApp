package com.example.suvestapp;

public enum TypeGifts {

    TOILETRY("Toiletry"), MUG("Mug"), CANDLE("Candle");

    private String typegifts;

    TypeGifts(String typegifts) {
        this.typegifts = typegifts;
    }

    public String getTypegifts() {
        return typegifts;
    }
}

