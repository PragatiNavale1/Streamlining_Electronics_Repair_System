package com.hrms.dao;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestDAO {
    private Connection con;

    public RequestDAO(Connection con) {
        this.con = con;
    }

    // Get user ID by email
    public int getUserIdByEmail(String email) throws SQLException {
        String query = "SELECT id FROM users WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    return -1;
                }
            }
        }
    }

    // Insert a new repair request
    public boolean insertRequest(int userId, String description, String serialNumber, String productDetails, String dateOfPurchase, String photoPath) {
        String sql = "INSERT INTO repair_requests (user_id, description, date_requested, status, serial_number, product_details, date_of_purchase, photo_path) " +
                     "VALUES (?, ?, CURDATE(), 'pending', ?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, description);
            stmt.setString(3, serialNumber);
            stmt.setString(4, productDetails);
            stmt.setDate(5, java.sql.Date.valueOf(dateOfPurchase));
            stmt.setString(6, photoPath);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Fetch requests submitted by a user
    public List<Map<String, String>> getRequestsByUserId(int userId) throws SQLException {
        List<Map<String, String>> requests = new ArrayList<>();
        String query = "SELECT * FROM repair_requests WHERE user_id = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("id", String.valueOf(rs.getInt("id")));
                    row.put("user_id", String.valueOf(rs.getInt("user_id")));
                    row.put("description", rs.getString("description"));
                    row.put("date_requested", rs.getString("date_requested"));
                    row.put("status", rs.getString("status"));
                    row.put("serial_number", rs.getString("serial_number"));
                    row.put("product_details", rs.getString("product_details"));
                    row.put("date_of_purchase", rs.getDate("date_of_purchase").toString());
                    row.put("photo_path", rs.getString("photo_path"));
                    row.put("repairman_email", rs.getString("repairman_email"));
                    row.put("taken_by", rs.getString("taken_by"));
                    row.put("appointment_date", rs.getDate("appointment_date") != null ? rs.getDate("appointment_date").toString() : "-");

                    requests.add(row);
                }
            }
        }

        return requests;
    }

    // Get pending requests for admin
    public List<Map<String, String>> getPendingRequests() throws SQLException {
        List<Map<String, String>> list = new ArrayList<>();
        String sql = "SELECT r.*, u.name AS user_name FROM repair_requests r " +
                "JOIN users u ON r.user_id = u.id " +
                "WHERE r.status = 'pending' AND r.id NOT IN (SELECT request_id FROM completed_requests)";
        	try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("id", rs.getString("id"));
                row.put("user_id", rs.getString("user_id"));
                row.put("user_name", rs.getString("user_name"));
                row.put("description", rs.getString("description"));
                row.put("date_requested", rs.getString("date_requested"));
                row.put("serial_number", rs.getString("serial_number"));
                row.put("product_details", rs.getString("product_details"));
                row.put("date_of_purchase", rs.getDate("date_of_purchase").toString());
                list.add(row);
            }
        }
        return list;
    }

    // Assign a repairman to a request
    public boolean assignRepairmanToRequest(int requestId, String repairmanEmail) throws SQLException {
        String sql = "UPDATE repair_requests SET status = 'assigned', repairman_email = ? WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, repairmanEmail);
            stmt.setInt(2, requestId);
            return stmt.executeUpdate() > 0;
        }
    }

    // For repairman: Get assigned and accepted requests
    public List<Map<String, String>> getRequestsByRepairmanEmail(String email) throws SQLException {
        List<Map<String, String>> list = new ArrayList<>();

        String sql = "SELECT r.id, r.user_id, u.name AS user_name, r.product_details, r.description, " +
                     "r.date_requested, r.status, r.appointment_date, r.photo_path " +
                     "FROM repair_requests r " +
                     "JOIN users u ON r.user_id = u.id " +
                     "WHERE r.repairman_email = ? " +
                     "AND (r.status = 'assigned' OR r.status = 'accepted') " +
                     "AND r.id NOT IN (SELECT id FROM completed_requests)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("id", rs.getString("id"));
                    row.put("user_id", rs.getString("user_id"));
                    row.put("user_name", rs.getString("user_name"));
                    row.put("product_details", rs.getString("product_details"));
                    row.put("description", rs.getString("description"));
                    row.put("date_requested", rs.getString("date_requested"));
                    row.put("status", rs.getString("status"));
                    row.put("appointment_date", rs.getDate("appointment_date") != null ?
                              rs.getDate("appointment_date").toString() : "-");
                    row.put("photo_path", rs.getString("photo_path"));  // ✅ Important fix

                    list.add(row);
                }
            }
        }

        return list;
    }


    // Accept request by repairman
    public boolean acceptRequest(int requestId) throws SQLException {
        String sql = "UPDATE repair_requests SET status = 'accepted' WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, requestId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    
    public boolean acceptRequestWithAppointment(int requestId, String appointmentDate) throws SQLException {
        String sql = "UPDATE repair_requests SET status = 'accepted', appointment_date = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(appointmentDate));
            stmt.setInt(2, requestId);
            return stmt.executeUpdate() > 0;
        }
    }
    
 // Get all requests for admin (pending + assigned)
    public List<Map<String, String>> getAdminRequestsWithStatus() throws SQLException {
        List<Map<String, String>> list = new ArrayList<>();
        String sql = "SELECT r.*, u.name AS user_name FROM repair_requests r JOIN users u ON r.user_id = u.id WHERE r.status IN ('pending', 'assigned')";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("id", rs.getString("id"));
                row.put("user_id", rs.getString("user_id"));
                row.put("user_name", rs.getString("user_name"));
                row.put("description", rs.getString("description"));
                row.put("serial_number", rs.getString("serial_number"));
                row.put("product_details", rs.getString("product_details"));
                row.put("date_of_purchase", rs.getDate("date_of_purchase") != null ? rs.getDate("date_of_purchase").toString() : "-");
                row.put("date_requested", rs.getString("date_requested"));
                row.put("status", rs.getString("status"));
                row.put("repairman_email", rs.getString("repairman_email"));
                list.add(row);
            }
        }
        return list;
    }



}
