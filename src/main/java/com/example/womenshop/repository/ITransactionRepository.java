package com.example.womenshop.repository;

import com.example.womenshop.model.Transaction;
import java.util.List;

public interface ITransactionRepository {
    void addTransaction(Transaction t);
    void updateTransaction(Transaction t);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByProductId(int productId);
    Transaction getTransactionById(int transactionId);
    void deleteTransaction(Transaction transaction);
}
