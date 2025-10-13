package se.fu.vn.dao;


import se.fu.vn.connection.DBConnect;
import se.fu.vn.enums.Enums;
import se.fu.vn.model.Users;

import java.sql.*;

public class UserDao {
    public Users findByUsername(String username) {
        String sql = "SELECT user_id, username, password, fullname FROM Users WHERE username = ?";
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
}