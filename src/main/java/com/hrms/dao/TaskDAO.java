package com.hrms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskDAO {
    private final Connection con;

    public TaskDAO(Connection con) {
        this.con = con;
    }

    /**
     * Assigns a request to a repairman. 
     * @return true if assignment succeeded
     */
    public boolean assignTask(int requestId, int repairmanId) {
        String sql = "INSERT INTO assignments (request_id, repairman_id, date_assigned) " +
                     "VALUES (?, ?, CURDATE())";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            ps.setInt(2, repairmanId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean takeRequest(int requestId, int repairmanId, Date assignedDate) {
        try {
            // Step 1: Get repairman details from users table
            String queryUserSql = "SELECT name, email FROM users WHERE id = ? AND role = 'repairman'";
            String repairmanName = null;
            String repairmanEmail = null;

            try (PreparedStatement ps = con.prepareStatement(queryUserSql)) {
                ps.setInt(1, repairmanId);
                var rs = ps.executeQuery();
                if (rs.next()) {
                    repairmanName = rs.getString("name");
                    repairmanEmail = rs.getString("email");
                } else {
                    // No such repairman found
                    return false;
                }
            }

            // Step 2: Update repair_requests table
            String updateRequestSql = "UPDATE repair_requests SET status = 'taken', taken_by = ?, appointment_date = ?, repairman_email = ? WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(updateRequestSql)) {
                ps.setString(1, repairmanName);
                ps.setDate(2, assignedDate);
                ps.setString(3, repairmanEmail);
                ps.setInt(4, requestId);
                ps.executeUpdate();
            }

            // Step 3: Insert into assignments table
            String insertAssignmentSql = "INSERT INTO assignments (request_id, repairman_id, date_assigned) VALUES (?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertAssignmentSql)) {
                ps.setInt(1, requestId);
                ps.setInt(2, repairmanId);
                ps.setDate(3, assignedDate);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
