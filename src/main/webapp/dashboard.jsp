<%@ page import="java.util.*, java.util.Map" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.hrms.util.DBConnection" %>
<%@ page import="com.hrms.dao.RequestDAO" %>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - FixItHub</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/dashboard.css">

</head>
<body>

<%@ include file="header.jsp" %>

<%
    String email = (String) session.getAttribute("email");

    // Load repair requests only if user is logged in and not already present from servlet
    if (email != null && request.getAttribute("requests") == null) {
        try {
            Connection con = com.hrms.util.DBConnection.getConnection();
            com.hrms.dao.RequestDAO dao = new com.hrms.dao.RequestDAO(con);
            int userId = dao.getUserIdByEmail(email);
            List<Map<String, String>> requests = dao.getRequestsByUserId(userId);
            request.setAttribute("requests", requests);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>


<div class="dashboard-container">
    <div class="left">
        <h1>Welcome back to REPAIR SERVICE</h1>
        <h3>REPAIR & SERVICE AT YOUR DOORSTEP!</h3>
        <p>Why replace when you can repair? We breathe new life into electronics—because every device deserves a second chance.</p>
        
        <p>
            <% if (email != null) { %>
                Logged in as: <strong><%= email %></strong>
            <% } else { %>
                <span style="color: red;">User is not logged in.</span>
            <% } %>
        </p>
        <div class="buttons">
            <a href="#repair-request">Your Repair Requests</a>
            <a href="#submit-request">Submit Repair Request</a>
        </div>
    </div>
    <div class="right">
        <img src="css/Home_page.png" alt="Dashboard Illustration">
    </div>
</div>


<!-- Repair Request Section -->
<div class="section theme-light" id="repair-request">
    <h2>Repair Requests</h2>
    <div class="cards">
        <%
            List<Map<String, String>> requests = (List<Map<String, String>>) request.getAttribute("requests");

            if (requests != null && !requests.isEmpty()) {
                for (Map<String, String> reqMap : requests) {
                    String status = reqMap.get("status");
                    String statusColor = "gray";
                    if ("Pending".equalsIgnoreCase(status)) {
                        statusColor = "red";
                    } else if ("Accepted".equalsIgnoreCase(status) || "assigned".equalsIgnoreCase(status)) {
                        statusColor = "green";
                    }
        %>
            <div class="card">
                <h3>#<%= reqMap.get("id") %> - <%= reqMap.get("product_details") %></h3>
                <p><strong>Status:</strong> <span style="color:<%= statusColor %>;"><%= status %></span></p>
                
                <p><strong>Submitted:</strong> <%= reqMap.get("date_requested") %></p>
                <p><strong>Appointment Date:</strong><br> <%= reqMap.get("appointment_date") != null ? reqMap.get("appointment_date") : "-" %></p>
            	
                <%
				    boolean canComplete = "accepted".equalsIgnoreCase(status)
				                          && reqMap.get("appointment_date") != null
				                          && !"-".equals(reqMap.get("appointment_date"));
				    if (canComplete) {
				%>
				    <form method="post" action="CompleteAppointmentServlet">
				        <input type="hidden" name="request_id" value="<%= reqMap.get("id") %>">
				        <button type="submit" class="mark-completed-btn">Mark as Completed</button>
					</form>
				<%
				    }
				%>
                
                </div>
        <%
                }
            } else {
        %>
            <p style="text-align:center; font-size:18px; color: #333;">
                🛠️ You haven't submitted any repair requests yet. Let’s get something fixed!
            </p>
        <%
            }
        %>
    </div>
</div>




<!-- Submit New Repair Request -->
<div class="section theme-blue" id="submit-request">
    <div class="request-form">
        <div class="request-box">
            <h2>Submit a New Repair Request</h2>

			<% 
			    String success = request.getParameter("success");
			    if ("1".equals(success)) { 
			%>
			    <div class="success-message">✅ Repair request submitted successfully!</div>
			<% 
			    } 
			%>

            <!-- ✅ Enhanced Form with File Upload -->
            <form action="addRequest" method="post" enctype="multipart/form-data">
                <input type="text" name="serial_number" placeholder="Serial Number" required>

                <textarea name="product_details" placeholder="Product Details" rows="3" required></textarea>
				
				<lable for="date_of_purchase" class="file-label">Date of Purchase:</lable>
                <input type="date" name="date_of_purchase" id="date of purchase" required>

                <label for="photo" class="file-label">Upload Photo (optional):</label>
                <input type="file" name="photo" id="photo" accept="image/*">

                <textarea name="description" placeholder="Describe the issue..." rows="4" required></textarea>

                <button type="submit">Submit Request</button>
            </form>

        </div>
    </div>
</div>




<%@ include file="footer.jsp" %>

</body>
</html>
