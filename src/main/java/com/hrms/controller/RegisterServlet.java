package com.hrms.controller;

import com.hrms.dao.UserDAO;
import com.hrms.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String name     = req.getParameter("name");
        String email    = req.getParameter("email");
        String role     = req.getParameter("role");       // NEW
        String password = req.getParameter("password");

        try (Connection con = DBConnection.getConnection()) {
            UserDAO dao = new UserDAO(con);
            boolean ok = dao.register(name, email, role, password);
            if (ok) {
                res.sendRedirect("login.jsp");
            } else {
                res.getWriter().println("Registration failed. Perhaps email already exists?");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error: " + e.getMessage());
        }
    }
}
