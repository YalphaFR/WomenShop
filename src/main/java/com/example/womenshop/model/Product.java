package com.example.womenshop.model;

public class Product {
    private int id;
    private Category category; // Clothes, Shoes, Accessory
    private String name;
    private double purchasePrice;
    private double salePrice;
    private boolean discounted;
    private double salePriceDiscounted;
    private int stock;

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
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

    public Product(int id, Category category, String name, double purchasePrice, double salePrice, boolean discounted, int stock) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.discounted = discounted;
        this.stock = stock;

        setDiscountPrice();
    }

    public Product(Category category, String name, double purchasePrice, double salePrice, boolean discounted, int stock) {
        this(-1, category, name, purchasePrice, salePrice, discounted, stock);
    }

    public Product(Category category, String name, double purchasePrice, double salePrice, int stock) {
        this(category, name, purchasePrice, salePrice, false, stock);
    }

    public double getFinalSalePrice() {
        if (discounted) {
            return salePriceDiscounted;
        }
        return salePrice;
    }


    private void setDiscountPrice() {
        if (category == null) return;
        switch(category.getName().toLowerCase()) {
            case "clothes": salePriceDiscounted = salePrice * 0.7; break;
            case "shoes": salePriceDiscounted = salePrice * 0.8; break;
            case "accessory": salePriceDiscounted = salePrice * 0.5; break;
        }
    }

    @Override
    public String toString() {
        return "ID : " + id + ", Category : " + category.getName() + ", Name : " + name + ", Purchase price : " + purchasePrice + ", Sale price : " + salePrice + ", Discount price : " + discounted + ", Stock: " + stock;
    }
}
