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

    public Services findById(int id) {
        String sql = "SELECT service_id, name, description, duration_minutes, price FROM Services WHERE service_id = ?";
        try (Connection c = DBConnect.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Services(rs.getInt("service_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getInt("duration_minutes"),
                            rs.getDouble("price"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }


}