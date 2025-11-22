package com.example.womenshop.service;

import com.example.womenshop.repository.IProductRepository;
import com.example.womenshop.service.*;
import com.example.womenshop.model.Product;
import com.example.womenshop.model.Transaction;
import com.example.womenshop.repository.ITransactionRepository;

import java.util.List;

public class TransactionService {
    private final ITransactionRepository repo;
    private final IProductRepository productRepository;

    public TransactionService(ITransactionRepository repo, IProductRepository productRepository) {
        this.repo = repo;
        this.productRepository = productRepository;
    }


    public void registerTransaction(Transaction t) {
        repo.addTransaction(t);
    }

    public List<Transaction> listAllTransactions() {
        List<Transaction> transactions = repo.getAllTransactions();
        for (Transaction t : transactions) {
            Product p = productRepository.getProductById(t.getProduct().getId());
            t.setProduct(p);
        }
        return transactions;
    }

    public Transaction findTransactionById(int id) {
        Transaction transaction = repo.getTransactionById(id);

        if (transaction.getProduct() != null) {

        }
        return transaction;
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
