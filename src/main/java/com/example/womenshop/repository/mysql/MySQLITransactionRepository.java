package com.example.womenshop.repository.mysql;

import com.example.womenshop.dao.DBManager;
import com.example.womenshop.model.Transaction;
import com.example.womenshop.repository.ITransactionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLITransactionRepository implements ITransactionRepository {

    private final DBManager db;

    public MySQLITransactionRepository(DBManager db) {
        this.db = db;
    }

    @Override
    public void addTransaction(Transaction t) {
        String sql = "INSERT INTO transactions (products_id, transactions_type, transactions_quantity, transactions_amount, transactions_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getProductsId());
            ps.setString(2, t.getTransactionsType().name());
            ps.setInt(3, t.getTransactionsQuantity());
            ps.setDouble(4, t.getTransactionsAmount());
            ps.setTimestamp(5, Timestamp.valueOf(t.getTransactionsDate()));
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) t.setTransactionsId(keys.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) transactions.add(mapResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByProductId(int productId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE products_id = ?";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) transactions.add(mapResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private Transaction mapResultSet(ResultSet rs) throws SQLException {
        return new Transaction(
                rs.getInt("transactions_id"),
                rs.getInt("products_id"),
                Transaction.TransactionType.valueOf(rs.getString("transactions_type")),
                rs.getInt("transactions_quantity"),
                rs.getDouble("transactions_amount"),
                rs.getTimestamp("transactions_date").toLocalDateTime()
        );
    }
}
