package com.example.womenshop.model;

import com.example.womenshop.model.base.Product;

import java.time.LocalDateTime;

public class Accessory extends Product {

    // Récupération dans la bdd
    public Accessory(int id, LocalDateTime createdAt, Category category, String name, double purchasePrice,
                     double salePrice, boolean discounted, int stock) {
        super(id, createdAt, category, name, purchasePrice, salePrice, discounted, stock);
    }

    // Création
    public Accessory(Category category, String name, double purchasePrice,
                     double salePrice, boolean discounted, int stock) {
        super(category, name, purchasePrice, salePrice, discounted, stock);
    }
}
