package com.hrms.controller;

import com.hrms.dao.RequestDAO;
import com.hrms.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class RepairmanRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        try {
            // Ensure valid session and role
            if (session == null || !"repairman".equals(session.getAttribute("role"))) {
                res.sendRedirect("login.jsp?error=Access+Denied");
                return;
            }

            String repairmanEmail = (String) session.getAttribute("email");
            if (repairmanEmail == null || repairmanEmail.isEmpty()) {
                res.sendRedirect("login.jsp?error=Invalid+Session+Email");
                return;
            }

            try (Connection con = DBConnection.getConnection()) {
                RequestDAO dao = new RequestDAO(con);

                List<Map<String, String>> assignedRequests = dao.getRequestsByRepairmanEmail(repairmanEmail);
                req.setAttribute("assignedRequests", assignedRequests);

                // ✅ Forward to repairman dashboard
                RequestDispatcher dispatcher = req.getRequestDispatcher("repairman_requests.jsp");
                dispatcher.forward(req, res);
            }

        } catch (Exception e) {
            e.printStackTrace(); // For debugging in console

            // ✅ Instead of redirecting to error.jsp, show a user-friendly fallback
            res.setContentType("text/html");
            res.getWriter().write("<h2>⚠️ Unable to load assigned requests</h2>");
            res.getWriter().write("<p>Error: " + e.getMessage() + "</p>");
            res.getWriter().write("<a href='login.jsp'>Back to Login</a>");
        }
    }
}
