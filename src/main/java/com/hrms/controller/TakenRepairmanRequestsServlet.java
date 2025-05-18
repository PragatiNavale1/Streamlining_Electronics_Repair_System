package com.hrms.controller;

import com.hrms.dao.RequestDAO;
import com.hrms.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class TakenRepairmanRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || !"repairman".equals(session.getAttribute("role"))) {
            res.sendRedirect(req.getContextPath() + "/login.jsp?error=Repairman+login+required");
            return;
        }

        String email = (String) session.getAttribute("email");
        if (email == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp?error=Invalid+session");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            RequestDAO dao = new RequestDAO(con);
            List<Map<String, String>> assigned = dao.getRequestsByRepairmanEmail(email);

            req.setAttribute("assignedRequests", assigned);
            RequestDispatcher rd = req.getRequestDispatcher("taken_repairman_requests.jsp");
            rd.forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Unable to load assigned requests", e);
        }
    }
}
