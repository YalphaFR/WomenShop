package com.example.womenshop.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private Product product;
    private TransactionType type;
    private int quantity;
    private double amount;
    private LocalDateTime date;

    public enum TransactionType {
        PURCHASE, SALE
    }

    public Transaction(int id, Product product, TransactionType type,
                       int quantity, double amount, LocalDateTime date) {
        this.id = id;
        this.product = product;
        this.type = type;
        this.quantity = quantity;
        this.amount = amount;
        this.date = date;
    }

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}
