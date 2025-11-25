package com.example.womenshop.model.base;


import com.example.womenshop.model.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionFactory {
    public static Transaction createTransaction(ResultSet rs) throws SQLException {
        Product product = ProductFactory.createProduct(rs);

        return new Transaction(
                rs.getInt("transaction_id"),
                rs.getTimestamp("transaction_created_at").toLocalDateTime(),
                product,
                Transaction.TransactionType.valueOf(rs.getString("transaction_type")),
                rs.getInt("transaction_quantity"),
                rs.getDouble("transaction_amount")
        );
    }
}
