package com.example.womenshop.model.base;

import com.example.womenshop.model.Category;

import java.time.LocalDateTime;

abstract public class Product extends Base {
    private Category category; // Clothes, Shoes, Accessory
    private String name;
    private double purchasePrice;
    private double salePrice;
    private double salePriceDiscounted;
    private boolean discounted;
    private int stock;

    // Récupération dans la bdd
    public Product(int id, LocalDateTime createdAt, Category category, String name, double purchasePrice, double salePrice, double salePriceDiscounted, boolean discounted, int stock) {
        super(id, createdAt);
        this.category = category;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.salePriceDiscounted = salePriceDiscounted;
        this.discounted = discounted;
        this.stock = stock;

        // si on a pas défini, prendre selon les règles des catégories d'articles
        if (discounted && salePriceDiscounted <= 0) {
            setDiscountPrice();
        }
    }

    // Création dans la bdd
    public Product(Category category, String name, double purchasePrice, double salePrice, double salePriceDiscounted, boolean discounted, int stock) {
        this(-1, LocalDateTime.now(), category, name, purchasePrice, salePrice, salePriceDiscounted, discounted, stock);
    }

    // Getters & setters
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(double purchasePrice) { this.purchasePrice = purchasePrice; }
    public double getSalePrice() { return salePrice; }
    public void setSalePrice(double salePrice) { this.salePrice = salePrice; }
    public double getSalePriceDiscounted() { return salePriceDiscounted; }
    public void setSalePriceDiscounted(double salePriceDiscounted) { this.salePriceDiscounted = salePriceDiscounted; }
    public boolean isDiscounted() { return discounted; }
    public void setDiscounted(boolean discounted) { this.discounted = discounted; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public double getFinalSalePrice() {
        double finalPrice = salePrice;
        if (discounted) {
            finalPrice = salePriceDiscounted;
        }
        return finalPrice;
    }


    private void setDiscountPrice() {
        if (category == null) return;
        salePriceDiscounted = salePrice - (salePrice * category.getDiscountRate());
    }

    @Override
    public String toString() {
        return super.toString() + ", Category : " + category.getName() + ", Name : " + name + ", Purchase price : " + purchasePrice + ", Sale price : " + salePrice + ", Discount price : " + discounted + ", Stock: " + stock;
    }
}
