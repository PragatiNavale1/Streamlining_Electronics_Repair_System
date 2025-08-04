package com.hrms.controller;

import com.hrms.dao.RequestDAO;
import jakarta.servlet.annotation.MultipartConfig;

import com.hrms.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

import java.util.List;
import java.util.Map;

@MultipartConfig
public class AddRequestServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {
	
	    HttpSession session = req.getSession(false);
	    if (session == null || session.getAttribute("email") == null) {
	        res.sendRedirect(req.getContextPath() + "/login.jsp?error=Please+log+in+first");
	        return;
	    }
	
	    String email = (String) session.getAttribute("email");
	
	    String description = req.getParameter("description");
	    String serialNumber = req.getParameter("serial_number");
	    String productDetails = req.getParameter("product_details");
	    String dateOfPurchase = req.getParameter("date_of_purchase");
	
	    Part photoPart = req.getPart("photo");
	    String photoPath = null;
	
	    // Save photo to disk (e.g., /uploads/)
	    if (photoPart != null && photoPart.getSize() > 0) {
	        String fileName = System.currentTimeMillis() + "_" + photoPart.getSubmittedFileName();
	        String uploadPath = getServletContext().getRealPath("") + "uploads";
	        java.io.File uploadDir = new java.io.File(uploadPath);
	        if (!uploadDir.exists()) uploadDir.mkdir();
	
	        String fullPath = uploadPath + java.io.File.separator + fileName;
	        photoPart.write(fullPath);
	
	        photoPath = "uploads/" + fileName;
	    }
	
	    try (Connection con = DBConnection.getConnection()) {
	        RequestDAO dao = new RequestDAO(con);
	        int userId = dao.getUserIdByEmail(email);
	
	        if (userId == -1) {
	            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "User not found.");
	            return;
	        }
	
	        boolean inserted = dao.insertRequest(userId, description, serialNumber, productDetails, dateOfPurchase, photoPath);
	        if (inserted) {
	            List<Map<String, String>> requests = dao.getRequestsByUserId(userId);
	            req.setAttribute("requests", requests);
	            req.getRequestDispatcher("/dashboard.jsp").forward(req, res);
	        }
	        else {
	            res.sendRedirect(req.getContextPath() + "/add_request.jsp?error=Unable+to+submit+request");
	        }
	
	    } catch (Exception e) {
	        e.printStackTrace();
	        res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
	    }
	}
}
