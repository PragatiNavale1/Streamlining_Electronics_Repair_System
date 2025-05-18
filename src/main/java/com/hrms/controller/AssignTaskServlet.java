package com.hrms.controller;

import com.hrms.dao.TaskDAO;
import com.hrms.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;

public class AssignTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        String role = session != null ? (String) session.getAttribute("role") : null;
        String email = session != null ? (String) session.getAttribute("email") : null;
        if (!"repairman".equals(role)) {
            res.sendRedirect("login.jsp?error=Repairman+login+required");
            return;
        }

        int requestId = Integer.parseInt(req.getParameter("requestId"));
        Date dateAssigned = Date.valueOf(req.getParameter("dateAssigned"));

        try (Connection con = DBConnection.getConnection()) {
            // lookup repairman_id
            int repairmanId = new com.hrms.dao.UserDAO(con).getUserIdByEmail(email);

            TaskDAO dao = new TaskDAO(con);
            boolean ok = dao.takeRequest(requestId, repairmanId, dateAssigned);
            if (ok) {
                res.sendRedirect("taken_repairman_requests?success=1");
            } else {
                res.sendRedirect("repairman_requests?error=Cannot+assign");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
