package com.example.womenshop.model.base;

import com.example.womenshop.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductFactory {

    public static Product createProduct(ResultSet rs) throws SQLException {
        Category category = CategoryFactory.createCategory(rs);
        Product p = null;

        switch (category.getName()) {
            case "Clothing":
                p = new Clothing(
                        rs.getInt("product_id"),
                        rs.getTimestamp("product_created_at").toLocalDateTime(),
                        category,
                        rs.getString("product_name"),
                        rs.getDouble("product_purchase_price"),
                        rs.getDouble("product_sale_price"),
                        rs.getBoolean("product_discounted"),
                        rs.getInt("product_stock"),
                        rs.getInt("product_size")
                );
                break;

            case "Shoes":
                p = new Shoes(
                        rs.getInt("product_id"),
                        rs.getTimestamp("product_created_at").toLocalDateTime(),
                        category,
                        rs.getString("product_name"),
                        rs.getDouble("product_purchase_price"),
                        rs.getDouble("product_sale_price"),
                        rs.getBoolean("product_discounted"),
                        rs.getInt("product_stock"),
                        rs.getInt("product_size")
                );
                break;

            case "Accessory":
                p =  new Accessory(
                        rs.getInt("product_id"),
                        rs.getTimestamp("product_created_at").toLocalDateTime(),
                        category,
                        rs.getString("product_name"),
                        rs.getDouble("product_purchase_price"),
                        rs.getDouble("product_sale_price"),
                        rs.getBoolean("product_discounted"),
                        rs.getInt("product_stock")
                );
                break;

            default:
                throw new IllegalStateException("Unknown category: " + category);
        }
        return p;
    }
}

