package com.example.womenshop.repository;

public interface IShopRepository {
    double getInitialCapital();

    double getCurrentCapital();
    void addToCurrentCapital(double value);
}