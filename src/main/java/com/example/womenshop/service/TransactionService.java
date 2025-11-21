package com.example.womenshop.service;

import com.example.womenshop.model.Product;
import com.example.womenshop.model.Transaction;
import com.example.womenshop.repository.IProductRepository;
import com.example.womenshop.repository.ITransactionRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionService {
    private final ITransactionRepository repo;

    public TransactionService(ITransactionRepository repo) {
        this.repo = repo;
    }


    public void registerTransaction(Transaction t) {
        repo.addTransaction(t);
    }

    public List<Transaction> listAllTransactions() {
       return  repo.getAllTransactions();
    }

    public Transaction findTransactionById(int id) {
        return repo.getTransactionById(id);
    }

    public List<Transaction> findTransactionByProductId(int id) {
        return repo.getTransactionsByProductId((id));
    }

    public void updateProductDetails(Transaction t) {
        repo.updateTransaction(t);
    }

    public void removeProduct(Transaction t) {
        repo.deleteTransaction(t);
    }
}
