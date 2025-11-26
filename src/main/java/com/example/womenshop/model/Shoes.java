package com.example.womenshop.model;

import com.example.womenshop.model.base.Product;

import java.time.LocalDateTime;

public class Shoes extends Product {

    private int size; // must be 36–50

    // Récupération dans la bdd
    public Shoes(int id, LocalDateTime createdAt, Category category, String name, double purchasePrice,
                    double salePrice, boolean discounted, int stock, int size) {
        super(id, createdAt, category, name, purchasePrice, salePrice, discounted, stock);
        this.size = size;
    }

    // Création
    public Shoes(Category category, String name, double purchasePrice,
                    double salePrice, boolean discounted, int stock, int size) {
        super(category, name, purchasePrice, salePrice, discounted, stock);
        this.size = size;
    }


    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        if (size < 36 || size > 50)
            throw new IllegalArgumentException("Shoes size must be between 36 and 50");

        this.size = size;
    }
}
