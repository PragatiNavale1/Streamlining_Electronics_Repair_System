package com.hrms.controller;

import com.hrms.dao.RequestDAO;
import com.hrms.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class PendingRepairmanRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || !"repairman".equals(session.getAttribute("role"))) {
            res.sendRedirect(req.getContextPath() + "/login.jsp?error=Repairman+login+required");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            RequestDAO dao = new RequestDAO(con);
            List<Map<String, String>> pending = dao.getPendingRequests();

            req.setAttribute("pendingRequests", pending);
            RequestDispatcher rd = req.getRequestDispatcher("pending_repairman_requests.jsp");
            rd.forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Unable to load pending requests", e);
        }
    }
}
