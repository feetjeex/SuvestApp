package com.example.suvestapp;

public enum Retailer {

    ASOS("Asos"), ZALANDO("Zalando"), LESARA("Lesara"), MIINTO("Miinto"), KOOVS("Koovs"), NELLY("Nelly"), NIKE("Nike"),
    ADIDAS("Adidas"), LACOSTE("Lacoste"), ARMANI("Armani"), PUMA("Puma"), BERSHKA("Bershka"), RELIGION("Religion"), VANS("Vans");

    private String retailer;

    Retailer(String retailer) {
        this.retailer = retailer;
    }

    public String getRetailer() {
        return retailer;
    }
}
