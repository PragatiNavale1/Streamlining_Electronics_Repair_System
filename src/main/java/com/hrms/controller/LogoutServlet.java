package com.hrms.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Support GET requests for logout
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalidate session
        HttpSession session = request.getSession(false); // false means don't create if it doesn't exist
        if (session != null) {
            session.invalidate();
        }
        // Redirect to login page (or home page)
        response.sendRedirect("login.jsp");
    }

    // Optional: Also support POST if needed
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
