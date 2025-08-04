package com.hrms.controller;

import com.hrms.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CompleteAppointmentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (session == null || role == null) {
            res.sendRedirect("login.jsp?error=Session+expired");
            return;
        }

        String requestIdStr = req.getParameter("request_id");

        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO completed_requests (request_id) VALUES (?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, Integer.parseInt(requestIdStr));
                ps.executeUpdate();
            }

            // Reload dashboard
            if ("user".equals(role)) {
                res.sendRedirect("dashboard.jsp?completed=1");
            } else {
                res.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("dashboard.jsp?error=Could+not+complete+appointment");
        }
    }
}
