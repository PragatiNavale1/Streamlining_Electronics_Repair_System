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

    // Method to get the userId from email
    public int getUserIdByEmail(String email) throws SQLException {
        String query = "SELECT id FROM users WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    return -1; // No such user
                }
            }
        }
    }

    // Modified insertRequest to return boolean
    public boolean insertRequest(int userId, String description) {
        String query = "INSERT INTO repair_requests (user_id, description, date_requested, status) VALUES (?, ?, CURDATE(), 'pending')";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, description);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;  // Return true if at least one row is inserted
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Return false if insertion fails
    }
    
    
    // Method to fetch repair requests based on user ID
    public List<Map<String, String>> getRequestsByUserId(int userId) throws SQLException {
        List<Map<String, String>> requests = new ArrayList<>();
        String query = "SELECT id, user_id, description, date_requested, status, taken_by, appointment_date, repairman_email " +
                       "FROM repair_requests WHERE user_id = ?";

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

                    // ✅ New fields
                    row.put("taken_by", rs.getString("taken_by"));
                    Date appointmentDate = rs.getDate("appointment_date");
                    row.put("appointment_date", appointmentDate != null ? appointmentDate.toString() : null);
                    row.put("repairman_email", rs.getString("repairman_email"));

                    requests.add(row);
                }
            }
        }

        return requests;
    }

    
    public List<Map<String,String>> getPendingRequests() throws SQLException {
        List<Map<String,String>> list = new ArrayList<>();
        String sql = "SELECT r.id, r.user_id, r.description, r.date_requested, u.name AS user_name " +
                     "FROM repair_requests r " +
                     "JOIN users u ON r.user_id = u.id " +
                     "WHERE r.status = 'pending'";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String,String> row = new HashMap<>();
                row.put("id",            rs.getString("id"));
                row.put("user_id",       rs.getString("user_id"));
                row.put("user_name",     rs.getString("user_name"));
                row.put("description",   rs.getString("description"));
                row.put("date_requested",rs.getString("date_requested"));
                list.add(row);
            }
        }
        return list;
    }
    
 // Get requests assigned to a specific repairman
    public List<Map<String, String>> getRequestsByRepairmanEmail(String email) throws SQLException {
        List<Map<String, String>> list = new ArrayList<>();

        String sql = "SELECT r.id, r.user_id, u.name AS user_name, r.description, r.date_requested, " +
                     "r.status, r.appointment_date " +
                     "FROM repair_requests r " +
                     "JOIN users u ON r.user_id = u.id " +
                     "WHERE r.repairman_email = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("id", String.valueOf(rs.getInt("id")));
                    row.put("user_id", String.valueOf(rs.getInt("user_id")));
                    row.put("user_name", rs.getString("user_name"));  // NEW: user's name
                    row.put("description", rs.getString("description"));
                    row.put("date_requested", rs.getString("date_requested"));
                    row.put("status", rs.getString("status"));
                    Date apptDate = rs.getDate("appointment_date");
                    row.put("appointment_date", apptDate != null ? apptDate.toString() : "-");

                    list.add(row);
                }
            }
        }

        return list;
    }





}
