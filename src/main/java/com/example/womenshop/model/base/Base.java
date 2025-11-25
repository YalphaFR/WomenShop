package com.example.womenshop.model.base;

import java.time.LocalDateTime;

abstract public class Base {
    private int id;
    private LocalDateTime createdAt;

    public Base(int id, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String toString() {
        return "ID : " + this.id + ", Created at : " + this.createdAt;
    }

}
