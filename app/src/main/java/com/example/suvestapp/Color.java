package com.example.suvestapp;

public enum Color {
    BLUE("Blue"), RED("Red"), GREEN("Green"), BLACK("Black"), WHITE("White"), ORANGE("Orange"), GREY("Grey"), PURPLE("Purple"), YELLOW("Yellow"), PINK("Pink"), BROWN("Brown");

    private String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
