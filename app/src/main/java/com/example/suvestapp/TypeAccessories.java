package com.example.suvestapp;

public enum TypeAccessories {

    BAGS("Bags"), BELTS("Belts"), HATS("Hats"), JEWELRY("Jewelry"), WATCHES("Watches"), SUNGLASSES("Sunglasses"), WALLETS("Wallets"), TIES("Ties");

    private String typeaccessories;

    TypeAccessories(String typeaccessories) {
        this.typeaccessories = typeaccessories;
    }

    public String getTypeaccessories() {
        return typeaccessories;
    }
}
