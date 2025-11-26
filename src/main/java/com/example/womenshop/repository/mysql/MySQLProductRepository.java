package com.example.womenshop.repository.mysql;

import com.example.womenshop.dao.DBManager;
import com.example.womenshop.model.Category;
import com.example.womenshop.model.Clothing;
import com.example.womenshop.model.Shoes;
import com.example.womenshop.model.base.Product;
import com.example.womenshop.model.base.ProductFactory;
import com.example.womenshop.repository.IProductRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLProductRepository implements IProductRepository {

    private final DBManager db;

    public MySQLProductRepository(DBManager db) {
        this.db = db;
    }

    @Override
    public void addProduct(Product p) {
        String sql = null;
        PreparedStatement ps = null;
        try (Connection conn = db.connect()) {

            switch (p.getCategory().getName()) {
                case "Clothing":
                    sql = "INSERT INTO product (category_id, product_name, product_purchase_price, product_sale_price, product_discounted, product_stock, product_size) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    ps.setInt(1, p.getCategory().getId());
                    ps.setString(2, p.getName());
                    ps.setDouble(3, p.getPurchasePrice());
                    ps.setDouble(4, p.getSalePrice());
                    ps.setBoolean(5, p.isDiscounted());
                    ps.setInt(6, p.getStock());
                    ps.setInt(7, ((Clothing) p).getSize());
                    break;

                case "Shoes":
                    sql = "INSERT INTO product (category_id, product_name, product_purchase_price, product_sale_price, product_discounted, product_stock, product_size) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
                    ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    ps.setInt(1, p.getCategory().getId());
                    ps.setString(2, p.getName());
                    ps.setDouble(3, p.getPurchasePrice());
                    ps.setDouble(4, p.getSalePrice());
                    ps.setBoolean(5, p.isDiscounted());
                    ps.setInt(6, p.getStock());
                    ps.setInt(7, ((Shoes) p).getSize());
                    break;

                case "Accessory":
                    sql = "INSERT INTO product (category_id, product_name, product_purchase_price, product_sale_price, product_discounted, product_stock) VALUES (?, ?, ?, ?, ?, ?)";
                    ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    ps.setInt(1, p.getCategory().getId());
                    ps.setString(2, p.getName());
                    ps.setDouble(3, p.getPurchasePrice());
                    ps.setDouble(4, p.getSalePrice());
                    ps.setBoolean(5, p.isDiscounted());
                    ps.setInt(6, p.getStock());
                    break;

                default:
                    throw new IllegalStateException("Unknown category: " + p.getCategory().getName());
            }

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
        String sql = "DELETE FROM product WHERE product_id=?";
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
        List<Product> product = new ArrayList<>();
        String sql = "SELECT * FROM product p JOIN category c ON p.category_id = c.category_id  ORDER BY c.category_name, p.product_sale_price DESC;";

        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                product.add(ProductFactory.createProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> getProductsFilterByCategory(Category category) {
        List<Product> product = new ArrayList<>();
        String sql = "SELECT p.*,c.* FROM product p JOIN category c ON p.category_id = c.category_id WHERE c.category_id = ? ORDER BY c.category_name, p.product_sale_price DESC;";

        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, category.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                        product.add(ProductFactory.createProduct(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }


    @Override
    public Product getProductById(int id) {
        String sql = "SELECT * FROM product WHERE product_id = ?;";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? ProductFactory.createProduct(rs) : null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // en cas d'erreur SQL
        }
    }


    @Override
    public Product getProductByName(String name) {
        String sql = "SELECT * FROM product p WHERE product_name = ?";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? ProductFactory.createProduct(rs) : null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // en cas d'erreur SQL
        }
    }

    @Override
    public Product getBestSellingProduct() {
        String sql = "SELECT p.*, c.*, SUM(t.transaction_quantity) AS total_sold FROM product p JOIN category c ON p.category_id = c.category_id JOIN transaction t ON t.product_id = p.product_id WHERE t.transaction_type = 'SALE' GROUP BY p.product_id ORDER BY total_sold DESC LIMIT 1;";
        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.next() ? ProductFactory.createProduct(rs) : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void updateProduct(Product p) {
        String sql = null;
        PreparedStatement ps = null;
        try (Connection conn = db.connect()) {
            if (p instanceof Shoes sh) {
                sql = "UPDATE product SET category_id=?, product_name=?, product_purchase_price=?, product_sale_price=?, product_discounted=?, product_stock=?, product_size=? WHERE product_id=?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, p.getCategory().getId());
                ps.setString(2, p.getName());
                ps.setDouble(3, p.getPurchasePrice());
                ps.setDouble(4, p.getSalePrice());
                ps.setBoolean(5, p.isDiscounted());
                ps.setInt(6, p.getStock());
                ps.setInt(7, sh.getSize());
                ps.setInt(8, p.getId());
                ps.executeUpdate();
            } else if (p instanceof Clothing cloth) {
                sql = "UPDATE product SET category_id=?, product_name=?, product_purchase_price=?, product_sale_price=?, product_discounted=?, product_stock=?, product_size=? WHERE product_id=?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, p.getCategory().getId());
                ps.setString(2, p.getName());
                ps.setDouble(3, p.getPurchasePrice());
                ps.setDouble(4, p.getSalePrice());
                ps.setBoolean(5, p.isDiscounted());
                ps.setInt(6, p.getStock());
                ps.setInt(7, cloth.getSize());
                ps.setInt(8, p.getId());
                ps.executeUpdate();
            } else {
                sql = "UPDATE product SET category_id=?, product_name=?, product_purchase_price=?, product_sale_price=?, product_discounted=?, product_stock=? WHERE product_id=?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, p.getCategory().getId());
                ps.setString(2, p.getName());
                ps.setDouble(3, p.getPurchasePrice());
                ps.setDouble(4, p.getSalePrice());
                ps.setBoolean(5, p.isDiscounted());
                ps.setInt(6, p.getStock());
                ps.setInt(7, p.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}