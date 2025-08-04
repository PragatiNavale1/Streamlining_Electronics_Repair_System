package com.hrms.controller;

import com.hrms.dao.RequestDAO;
import com.hrms.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class AcceptRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (session == null || !"repairman".equals(role)) {
            res.sendRedirect("login.jsp?error=Access+Denied");
            return;
        }

        String requestIdStr = req.getParameter("request_id");
        String appointmentDateStr = req.getParameter("appointment_date");

        if (requestIdStr == null || appointmentDateStr == null || requestIdStr.isEmpty() || appointmentDateStr.isEmpty()) {
            res.sendRedirect("repairman_requests?error=Missing+parameters");
            return;
        }

        int requestId = Integer.parseInt(requestIdStr);

        try {
            try (Connection con = DBConnection.getConnection()) {
                RequestDAO dao = new RequestDAO(con);
                boolean success = dao.acceptRequestWithAppointment(requestId, appointmentDateStr);

                if (success) {
                    res.sendRedirect("repairman_requests");
                } else {
                    res.sendRedirect("repairman_requests?error=Update+failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            res.sendRedirect("repairman_requests?error=Database+Error");
        }



    }
}
