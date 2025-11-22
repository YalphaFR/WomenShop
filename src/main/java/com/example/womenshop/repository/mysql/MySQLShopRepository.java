package com.example.womenshop.repository.mysql;

import com.example.womenshop.dao.DBManager;
import com.example.womenshop.repository.IShopRepository;

import java.sql.*;

public class MySQLShopRepository implements IShopRepository {

    private final DBManager db;

    public MySQLShopRepository(DBManager db) {
        this.db = db;
    }

    @Override
    public double getCapital() {
        String sql = "SELECT capital FROM shop WHERE shop_id = 1";
        try (Connection conn = db.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.next() ? rs.getDouble("capital") : 0.0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public void setCapital(double value) {
        String sql = "UPDATE shop SET capital = ? WHERE shop_id = 1";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, value);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToCapital(double value) {
        String sql = "UPDATE shop SET capital = capital + ? WHERE shop_id = 1";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, value);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

