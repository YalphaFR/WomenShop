package com.example.womenshop.model.base;

import com.example.womenshop.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryFactory {
    public static Category createCategory(ResultSet rs) throws SQLException {
        return new Category(
                rs.getInt("category_id"),
                rs.getTimestamp("category_created_at").toLocalDateTime(),
                rs.getString("category_name"),
                rs.getDouble("category_discount_rate")
        );
    }
}
