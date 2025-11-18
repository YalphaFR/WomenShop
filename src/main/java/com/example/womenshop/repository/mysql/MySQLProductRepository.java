package com.example.womenshop.repository.mysql;

import com.example.womenshop.dao.DBManager;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;
import com.example.womenshop.repository.ProductRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLProductRepository implements ProductRepository {

    private final DBManager db;

    public MySQLProductRepository(DBManager db) {
        this.db = db;
    }

    @Override
    public void addProduct(Product p) {
        String sql = "INSERT INTO products (categories_id, products_name, products_purchase_price, products_sale_price, products_discounted, products_stock) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, p.getCategory().getId());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPurchasePrice());
            ps.setDouble(4, p.getSalePrice());
            ps.setBoolean(5, p.isDiscounted());
            ps.setInt(6, p.getStock());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                p.setId(keys.getInt(1)); // Met à jour l'objet Product avec l'ID généré
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE products_id=?";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT *  FROM products p JOIN categories c ON p.categories_id = c.categories_id   ORDER BY c.categories_name, p.products_sale_price DESC;";

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("categories_id"),
                        rs.getString("categories_name"),
                        rs.getDouble("categories_discount_rate")
                );

                products.add(new Product(
                        rs.getInt("products_id"),
                        category,
                        rs.getString("products_name"),
                        rs.getDouble("products_purchase_price"),
                        rs.getDouble("products_sale_price"),
                        rs.getBoolean("products_discounted"),
                        rs.getInt("products_stock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product getProductById(int id) {
        String sql = "SELECT p.*, c.* AS categories_name FROM products p " +
                "JOIN categories c ON p.categories_id = c.categories_id WHERE p.products_id = ?";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // vérifie s'il y a un résultat
                    Category category = new Category(
                            rs.getInt("categories_id"),
                            rs.getString("categories_name"),
                            rs.getDouble("categories_discount_rate")
                    );

                    return new Product(
                            rs.getInt("products_id"),
                            category,
                            rs.getString("products_name"),
                            rs.getDouble("products_purchase_price"),
                            rs.getDouble("products_sale_price"),
                            rs.getBoolean("products_discounted"),
                            rs.getInt("products_ stock")
                    );
                } else {
                    return null; // aucun produit trouvé pour cet id
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // en cas d'erreur SQL
        }
    }


    @Override
    public void updateProduct(Product p) {
        String sql = "UPDATE products SET categories_id=?, products_name=?, products_purchase_price=?, products_sale_price=?, products_discounted=?, products_stock=? WHERE products_id=?";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getCategory().getId());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPurchasePrice());
            ps.setDouble(4, p.getSalePrice());
            ps.setBoolean(5, p.isDiscounted());
            ps.setInt(6, p.getStock());
            ps.setInt(7, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}