package com.example.womenshop.service;

import com.example.womenshop.repository.IProductRepository;
import com.example.womenshop.repository.IShopRepository;
import com.example.womenshop.repository.ITransactionRepository;

public class ShopService {

    private final IShopRepository repo;
    private final ITransactionRepository transactionRepository;

    public ShopService(IShopRepository repo, ITransactionRepository transactionRepository) {
        this.repo = repo;
        this.transactionRepository = transactionRepository;
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

