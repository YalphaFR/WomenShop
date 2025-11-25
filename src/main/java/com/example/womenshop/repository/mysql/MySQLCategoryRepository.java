package com.example.womenshop.repository.mysql;

import com.example.womenshop.dao.DBManager;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Transaction;
import com.example.womenshop.model.base.CategoryFactory;
import com.example.womenshop.repository.ICategoryRepository;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class MySQLCategoryRepository implements ICategoryRepository {

    private final DBManager db;

    public MySQLCategoryRepository(DBManager db) {
        this.db = db;
    }

    @Override
    public void addCategory(Category c) {
        String sql = "INSERT INTO category (category_name, category_discount_rate) VALUES (?, ?);";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getName());
            ps.setDouble(2, c.getDiscountRate());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                c.setId(keys.getInt(1)); // MISE À JOUR DU PRODUCT
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCategory(Category c) {
        String sql = "UPDATE category SET category_name=?, category_discount_rate=? WHERE product_id=?;";
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
        String sql = "DELETE FROM category WHERE category_id=?;";
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
        List<Category> category = new ArrayList<>();
        String sql = "SELECT * FROM category ORDER BY category_name DESC;";

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                category.add(CategoryFactory.createCategory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM category WHERE category_id = ?;";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            Category c = null;

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // vérifie s'il y a un résultat
                     c = CategoryFactory.createCategory(rs);
                }
                return c;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // en cas d'erreur SQL
        }
    }
}

