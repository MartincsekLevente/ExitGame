package com.example.exitgame;

public class itemsToFirestore {

    private String itemNames;
    private String itemPrices;
    private int itemImages;

    public itemsToFirestore() {
    }

    public String getItemNames() {
        return itemNames;
    }

    public String getItemPrices() {
        return itemPrices;
    }

    public int getItemImages() {
        return itemImages;
    }

    public itemsToFirestore(String itemNames, String itemPrices, int itemImages) {
        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.itemImages = itemImages;
    }
}
