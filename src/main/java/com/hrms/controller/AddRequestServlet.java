package com.hrms.controller;

import com.hrms.dao.RequestDAO;
import com.hrms.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

public class AddRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // 1. Check for an existing session
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            // Not logged in → send them to login
            res.sendRedirect(req.getContextPath() + "/login.jsp?error=Please+log+in+first");
            return;
        }

        // 2. Grab form data
        String description = req.getParameter("description");
        String email       = (String) session.getAttribute("email");

        // 3. Insert into database
        try (Connection con = DBConnection.getConnection()) {
            RequestDAO dao = new RequestDAO(con);
            int userId = dao.getUserIdByEmail(email);
            
            if (userId == -1) {
                // Something’s wrong — no such user
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "User not found.");
                return;
            }

            boolean inserted = dao.insertRequest(userId, description);
            if (inserted) {
                // 4. On success, redirect to a page that shows their requests
                res.sendRedirect(req.getContextPath() + "/dashboard.jsp?success=1");
            } else {
                // 5. On failure, show error
                res.sendRedirect(req.getContextPath() + "/add_request.jsp?error=Unable+to+submit+request");
            }

        } catch (Exception e) {
            // Log and show error
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }
}
