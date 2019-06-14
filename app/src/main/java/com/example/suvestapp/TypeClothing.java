package com.example.suvestapp;

public enum TypeClothing {

    SWEATERS("Sweaters"), HOODIES("Hoodies"), TROUSERS("Trousers"), JACKETS("Jackets"), JEANS("Jeans"), SHIRTS("Shirts"), UNDERWEAR("Underwear"), SHORTS("Shorts"), SOCKS("Socks"),
    SWIMWEAR("Swimwear");

    private String typeclothing;

    TypeClothing(String typeclothing) {
        this.typeclothing = typeclothing;
    }

    public String getTypeclothing() {
        return typeclothing;
    }

}
