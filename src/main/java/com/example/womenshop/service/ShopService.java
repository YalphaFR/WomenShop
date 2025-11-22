package com.example.womenshop.service;

import com.example.womenshop.repository.IShopRepository;

public class ShopService {

    private final IShopRepository repo;

    public ShopService(IShopRepository repo) {
        this.repo = repo;
    }

    public double getCapital() {
        return repo.getCapital();
    }

    public void setCapital(double v) {
        repo.setCapital(v);
    }

    public void addToCapital(double v) {
        repo.addToCapital(v);
    }
}

