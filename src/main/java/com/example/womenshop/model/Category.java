package com.example.womenshop.model;

import com.example.womenshop.model.base.Base;

import java.time.LocalDateTime;

public class Category extends Base {
    private String name;
    private double discountRate;

    public Category(int id, LocalDateTime createdAt, String name, double discountRate) {
        super(id, createdAt);
        this.name = name;
        this.discountRate = discountRate;
    }

    // Getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getDiscountRate() { return discountRate; }
    public void setDiscountRate(double discountRate) { this.discountRate = discountRate; }


    @Override
    public String toString() {
        return super.toString() +  ", Name : " + name + ", Discount Rate : " + discountRate;
    }
}
