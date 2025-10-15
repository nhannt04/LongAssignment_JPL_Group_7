package se.fu.vn.dao;

import se.fu.vn.connection.DBConnect;
import se.fu.vn.enums.Enums;
import se.fu.vn.model.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public Users findByUsername(String username) {
        String sql = "SELECT user_id, username, password, fullname, role FROM Users WHERE username = ?";
        try (Connection c = DBConnect.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Users(rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("fullname"),
                            Enums.valueOf(rs.getString("role")));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<Users> findAll() {
        List<Users> list = new ArrayList<>();
        String sql = "SELECT user_id, username, password, fullname, role FROM Users ORDER BY user_id";
        try (Connection c = DBConnect.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Users(rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("fullname"),
                        Enums.valueOf(rs.getString("role"))));
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return list;
    }
    
    public int insert(Users user) throws SQLException {
        String sql = "INSERT INTO Users (username, password, fullname, role) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnect.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getRole().toString());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Insert user failed");
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }
}