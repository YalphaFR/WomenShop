package com.example.womenshop.repository.mysql;

import com.example.womenshop.dao.DBManager;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;
import com.example.womenshop.model.Transaction;
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
        String sql = "INSERT INTO transactions (products_id, transactions_type, transactions_quantity, transactions_amount, transactions_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getProduct().getId());
            ps.setString(2, t.getType().name());
            ps.setInt(3, t.getQuantity());
            ps.setDouble(4, t.getAmount());
            ps.setTimestamp(5, Timestamp.valueOf(t.getDate()));
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) t.setId(keys.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTransaction(Transaction t) {
        String sql = "UPDATE transactions SET products_id=?, transactions_type=?, transactions_quantity=?, transactions_amount=?, transactions_date=? WHERE transactions_id=?";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getProduct().getId());
            ps.setString(2, t.getType().name());
            ps.setInt(3, t.getQuantity());
            ps.setDouble(4, t.getAmount());
            ps.setTimestamp(5, Timestamp.valueOf(t.getDate()));
            ps.executeUpdate();
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
            while (rs.next()) {
                transactions.add(mapResultSet(rs));
            }
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

    @Override
    public Transaction getTransactionById(int transactionId) {
        String sql = "SELECT * FROM transactions WHERE transactions_id = ?;";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, transactionId);

            try (ResultSet rs = ps.executeQuery()) {
                Transaction t = null;

                if (rs.next()) { // vérifie s'il y a un résultat
                    t = mapResultSet(rs);
                }
                return t;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // en cas d'erreur SQL
        }
    }

    private Transaction mapResultSet(ResultSet rs) throws SQLException {
        Product p = new Product(rs.getInt("products_id"), null, null, 0, 0, false, 0);
        return new Transaction(
                rs.getInt("transactions_id"),
                p,
                Transaction.TransactionType.valueOf(rs.getString("transactions_type")),
                rs.getInt("transactions_quantity"),
                rs.getDouble("transactions_amount"),
                rs.getTimestamp("transactions_date").toLocalDateTime()
        );
    }

    public void deleteTransaction(Transaction transaction) {
        String sql = "DELETE FROM products WHERE products_id=?";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, transaction.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
