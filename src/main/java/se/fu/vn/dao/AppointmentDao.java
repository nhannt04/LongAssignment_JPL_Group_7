package se.fu.vn.dao;


import se.fu.vn.connection.DBConnect;

import se.fu.vn.model.Appointment;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {

    public List<Appointment> findAll() {
        String sql = "SELECT appointment_id, customer_id, customer_name, customer_email, service_id, appointment_time, status, created_at FROM Appointments ORDER BY appointment_time";

        List<Appointment> out = new ArrayList<>();
        try (Connection c = DBConnect.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                out.add(new Appointment(
                        rs.getInt("appointment_id"),
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_email"),
                        rs.getInt("service_id"),
                        rs.getTimestamp("appointment_time").toLocalDateTime(),
                        rs.getString("status"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    public void updateStatus(int appointmentId, String newStatus) {
        String sql = "UPDATE Appointments SET status = ? WHERE appointment_id = ?";
        try (Connection c = DBConnect.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
             ps.setString(1, newStatus);
             ps.setInt(2, appointmentId);
             ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Insert và trả về generated id
    public int insert(Appointment app) throws SQLException {
        String sql = "INSERT INTO Appointments (customer_id, customer_name, customer_email, service_id, appointment_time, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = DBConnect.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, app.getCustomerId());
            ps.setString(2, app.getCustomerName());
            ps.setString(3, app.getCustomerEmail());
            ps.setInt(4, app.getServiceId());
            ps.setTimestamp(5, Timestamp.valueOf(app.getAppointmentDate()));
            ps.setString(6, app.getStatus());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("Insert appointment failed");
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }


    public boolean hasConflict(Connection conn, int serviceId, LocalDateTime newStart, LocalDateTime newEnd) throws SQLException {
        String sql = "SELECT appointment_time, s.duration_minutes FROM Appointments a JOIN Services s ON a.service_id = s.service_id " +
                "WHERE a.service_id = ? AND a.status != ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            ps.setString(2, "CANCELLED");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LocalDateTime existStart = rs.getTimestamp("appointment_time").toLocalDateTime();
                    int duration = rs.getInt("duration_minutes");
                    LocalDateTime existEnd = existStart.plusMinutes(duration);
                    if (newStart.isBefore(existEnd) && existStart.isBefore(newEnd)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}