package com.example.womenshop.repository;

import com.example.womenshop.model.Transaction;
import java.util.List;

public interface ITransactionRepository {
    void addTransaction(Transaction t);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByProductId(int productId);
}
