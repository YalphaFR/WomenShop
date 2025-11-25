package com.example.womenshop.repository.mysql;

import com.example.womenshop.dao.DBManager;
import com.example.womenshop.model.Transaction;
import com.example.womenshop.model.base.TransactionFactory;
import com.example.womenshop.repository.ITransactionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLTransactionRepository implements ITransactionRepository {

    private final DBManager db;

    public MySQLTransactionRepository(DBManager db) {
        this.db = db;
    }

    @Override
    public void addTransaction(Transaction t) {
        String sql = "INSERT INTO transaction (product_id, transaction_type, transaction_quantity, transaction_amount, transaction_created_at) VALUES (?, ?, ?, ?, ?);";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getProduct().getId());
            ps.setString(2, t.getType().name());
            ps.setInt(3, t.getQuantity());
            ps.setDouble(4, t.getAmount());
            ps.setTimestamp(5, Timestamp.valueOf(t.getCreatedAt()));
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) t.setId(keys.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTransaction(Transaction t) {
        String sql = "UPDATE transaction SET transaction_type=?, transaction_quantity=?, transaction_amount=?, transaction_created_at=? WHERE transaction_id=?;";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getType().name());
            ps.setInt(2, t.getQuantity());
            ps.setDouble(3, t.getAmount());
            ps.setTimestamp(4, Timestamp.valueOf(t.getCreatedAt()));
            ps.setInt(5, t.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transaction = new ArrayList<>();
        String sql = "SELECT t.*, p.*, c.* FROM transaction t JOIN product p ON t.product_id = p.product_id JOIN category c ON c.category_id = p.category_id ORDER BY t.transaction_created_at DESC;";
        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                transaction.add(TransactionFactory.createTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    @Override
    public List<Transaction> getTransactionsByProductId(int productId) {
        List<Transaction> transaction = new ArrayList<>();
        String sql = "SELECT t.*, p.*,c.* FROM transaction t JOIN product p ON t.product_id = p.product_id JOIN category c ON c.category_id = p.category_id WHERE p.product_id = ? ORDER BY t.transaction_created_at DESC;";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) transaction.add(TransactionFactory.createTransaction(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    @Override
    public Transaction getTransactionById(int transactionId) {
        String sql = "SELECT t.*, p.*, c.* FROM transaction t JOIN product p ON t.product_id = p.product_id JOIN category c ON c.category_id = p.category_id WHERE t.transaction_id = ? ORDER BY t.transaction_created_at DESC;";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, transactionId);

            try (ResultSet rs = ps.executeQuery()) {
                Transaction t = null;

                if (rs.next()) { // vérifie s'il y a un résultat
                    t = TransactionFactory.createTransaction(rs);
                }
                return t;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // en cas d'erreur SQL
        }
    }

    public void deleteTransaction(Transaction transaction) {
        String sql = "DELETE FROM transaction WHERE transaction_id=?;";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, transaction.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
