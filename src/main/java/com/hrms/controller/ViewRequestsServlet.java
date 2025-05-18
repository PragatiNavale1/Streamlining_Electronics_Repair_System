package com.hrms.controller;

import com.hrms.dao.RequestDAO;
import com.hrms.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ViewRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String email = (String) (session != null ? session.getAttribute("email") : null);

        if (email == null) {
            System.out.println("Session expired or user not logged in.");
            res.sendRedirect("login.jsp");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            RequestDAO dao = new RequestDAO(con);

            int userId = dao.getUserIdByEmail(email);
            if (userId == -1) {
                System.out.println("No user found for email: " + email);
                res.sendRedirect("login.jsp");
                return;
            } else {
                System.out.println("User email = " + email + " → userId = " + userId);
            }

            List<Map<String, String>> requests = dao.getRequestsByUserId(userId);
            System.out.println("Total requests fetched = " + requests.size());

            req.setAttribute("requests", requests);
            RequestDispatcher dispatcher = req.getRequestDispatcher("view_requests.jsp");
            dispatcher.forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error occurred: " + e.getMessage());
        }
    }
}
