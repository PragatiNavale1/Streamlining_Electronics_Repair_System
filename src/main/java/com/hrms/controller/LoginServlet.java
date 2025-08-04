package com.hrms.controller;

import com.hrms.dao.UserDAO;
import com.hrms.util.DBConnection;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

public class LoginServlet extends HttpServlet {
    @Override
   
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try (Connection con = DBConnection.getConnection()) {
            UserDAO dao = new UserDAO(con);
            if (dao.login(email, password)) {
                // After successful login, get the user role
                String role = dao.getUserRole(email);

                // Create or get the current session
                HttpSession session = req.getSession(true);  // ensure a session is created
                session.setAttribute("email", email);
                session.setAttribute("role", role);

                // Debug print to ensure session attributes are being set
                System.out.println(">>> Session created: email=" + email + ", role=" + role);

                // Redirect based on user role
                if (role.equals("admin")) {
                    res.sendRedirect("admin_dashboard.jsp");
                } else if (role.equals("repairman")) {
                    res.sendRedirect("repairman_requests");
                } else {
                    res.sendRedirect("dashboard.jsp");
                }
            } else {
                res.getWriter().println("Invalid login credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error: " + e.getMessage());
        }
    }

    }

