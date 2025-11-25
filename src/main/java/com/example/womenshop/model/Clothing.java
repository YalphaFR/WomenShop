package com.example.womenshop.model;

import com.example.womenshop.model.base.Product;

import java.time.LocalDateTime;

public class Clothing extends Product {
    private int size; // must be 34–54

    // Récupération dans la bdd
    public Clothing(int id, LocalDateTime createdAt, Category category, String name, double purchasePrice,
                     double salePrice, double salePriceDiscounted, boolean discounted, int stock, int size) {
        super(id, createdAt, category, name, purchasePrice, salePrice, salePriceDiscounted, discounted, stock);
        this.size = size;
    }

    // Création
    public Clothing(Category category, String name, double purchasePrice,
                     double salePrice, double salePriceDiscounted, boolean discounted, int stock, int size) {
        super(category, name, purchasePrice, salePrice, salePriceDiscounted, discounted, stock);
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        if (size < 34 || size > 54)
            throw new IllegalArgumentException("Clothing size must be between 34 and 54");

        this.size = size;
    }
}
