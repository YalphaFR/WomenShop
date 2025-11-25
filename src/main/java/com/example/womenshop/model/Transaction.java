package com.example.womenshop.model;

import com.example.womenshop.model.base.Base;
import com.example.womenshop.model.base.Product;

import java.time.LocalDateTime;

public class Transaction extends Base {
    private Product product;
    private TransactionType type;
    private int quantity;
    private double amount;

    public enum TransactionType {
        PURCHASE, SALE
    }

    public Transaction(int id, LocalDateTime createdAt, Product product, TransactionType type,
                       int quantity, double amount) {
        super(id, createdAt);
        this.product = product;
        this.type = type;
        this.quantity = quantity;
        this.amount = amount;
    }

    public Transaction(LocalDateTime createdAt, Product product, TransactionType type, int quantity, double amount) {
        this(-1, createdAt, product,type,quantity,amount);
    }

    public Transaction(Product product, TransactionType type, int quantity, double amount) {
        this(-1, LocalDateTime.now(), product,type,quantity,amount);
    }

    // Getters & setters
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
