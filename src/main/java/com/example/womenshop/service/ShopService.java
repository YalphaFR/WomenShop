package com.example.womenshop.service;

import com.example.womenshop.repository.IProductRepository;
import com.example.womenshop.repository.IShopRepository;
import com.example.womenshop.repository.ITransactionRepository;

public class ShopService {

    private final IShopRepository repo;

    public ShopService(IShopRepository repo) {
        this.repo = repo;
    }

    public double getInitialCapital() {
        return repo.getInitialCapital();
    }

    public double getCurrentCapital() {
        return repo.getCurrentCapital();
    }

    public void addToCurrentCapital(double v) {
        repo.addToCurrentCapital(v);
    }
}

