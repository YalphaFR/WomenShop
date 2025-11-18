package com.example.womenshop.repository.mysql;

import com.example.womenshop.dao.DBManager;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Product;
import com.example.womenshop.repository.CategoryRepository;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class MySQLCategoryRepository implements CategoryRepository {

    private final DBManager db;

    public MySQLCategoryRepository(DBManager db) {
        this.db = db;
    }

    @Override
    public void addCategory(Category c) {
        String sql = "INSERT INTO categories (categories_name, categories_discount_rate) VALUES (?, ?)";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getName());
            ps.setDouble(2, c.getDiscountRate());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                c.setId(keys.getInt(1)); // <<< MISE À JOUR DU PRODUCT
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCategory(Category c) {
        String sql = "UPDATE categories SET categories_name=?, categories_discount_rate=? WHERE products_id=?";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setDouble(2, c.getDiscountRate());
            ps.setInt(3, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCategory(int id) {
        String sql = "DELETE FROM categories WHERE categories_id=?";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY categories_name DESC";

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("categories_id"),
                        rs.getString("categories_name"),
                        rs.getDouble("categories_discount_rate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM categories WHERE p.products_id = ?";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // vérifie s'il y a un résultat
                    return new Category(
                            rs.getInt("categories_id"),
                            rs.getString("categories_name"),
                            rs.getDouble("categories_discount_rate")
                    );
                } else {
                    return null; // aucune catégorie trouvée pour cet id
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // en cas d'erreur SQL
        }
    }
}

