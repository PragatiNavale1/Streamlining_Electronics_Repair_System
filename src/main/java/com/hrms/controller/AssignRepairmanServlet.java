package com.hrms.controller;

import com.hrms.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class AssignRepairmanServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (session == null || !"admin".equals(role)) {
            res.sendRedirect("login.jsp?error=Access+Denied");
            return;
        }

        String requestIdStr = req.getParameter("request_id");
        String repairmanEmail = req.getParameter("repairman_email");

        if (requestIdStr == null || repairmanEmail == null || requestIdStr.isEmpty() || repairmanEmail.isEmpty()) {
            res.sendRedirect("admin_dashboard.jsp?error=Missing+data");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
        	String sql = "UPDATE repair_requests SET status = 'assigned', repairman_email = ? WHERE id = ?";
        	try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, repairmanEmail);
                ps.setInt(2, Integer.parseInt(requestIdStr));

                int updated = ps.executeUpdate();

                if (updated > 0) {
                    res.sendRedirect("admin_dashboard.jsp?assigned=1");
                } else {
                    res.sendRedirect("admin_dashboard.jsp?error=Assign+failed");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("admin_dashboard.jsp?error=Database+error");
        }
    }
}
