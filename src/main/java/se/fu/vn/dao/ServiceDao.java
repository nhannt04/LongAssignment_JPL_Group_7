package se.fu.vn.dao;


import se.fu.vn.connection.DBConnect;
import se.fu.vn.model.Services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDao {
    public List<Services> findAll() {
        List<Services> list = new ArrayList<>();
        String sql = "SELECT service_id, name, description, duration_minutes, price FROM Services ORDER BY service_id";
        try (Connection c = DBConnect.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Services(rs.getInt("service_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("duration_minutes"),
                        rs.getDouble("price")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }


    
    public int insert(Services service) throws SQLException {
        String sql = "INSERT INTO Services (name, description, duration_minutes, price) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnect.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, service.getName());
            ps.setString(2, service.getDescription());
            ps.setInt(3, service.getDuration());
            ps.setDouble(4, service.getPrice());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Insert service failed");
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }
    
    public boolean update(Services service) throws SQLException {
        String sql = "UPDATE Services SET name = ?, description = ?, duration_minutes = ?, price = ? WHERE service_id = ?";
        try (Connection c = DBConnect.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, service.getName());
            ps.setString(2, service.getDescription());
            ps.setInt(3, service.getDuration());
            ps.setDouble(4, service.getPrice());
            ps.setInt(5, service.getServiceId());
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean delete(int serviceId) throws SQLException {
        String sql = "DELETE FROM Services WHERE service_id = ?";
        try (Connection c = DBConnect.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            return ps.executeUpdate() > 0;
        }
    }
}