package com.example.suvestapp;

/** TypeShoes
 *
 * Enum which contains constants representing all the different Types of Shoes used in the application.
 */

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
