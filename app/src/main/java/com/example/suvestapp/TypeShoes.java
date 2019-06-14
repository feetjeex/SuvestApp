package com.example.suvestapp;

public enum TypeShoes {

    SNEAKERS("Sneakers"), BOOTS("Boots"), ESPADRILLES("Espadrilles"), FLIPFLOPS("Flipflops"), SLIDERS("Sliders");

    private String typeshoes;

    TypeShoes(String typeshoes) {
        this.typeshoes = typeshoes;
    }

    public String getTypeshoes() {
        return typeshoes;
    }
}
