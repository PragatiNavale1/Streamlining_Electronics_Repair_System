<%@ page import="java.util.*, java.sql.*" %>
<%@ page session="true" %>
<%@ page import="com.hrms.util.*" %>
<%
	String email = (String) session.getAttribute("email");
	String role = (String) session.getAttribute("role");
	
	if (email == null || !"repairman".equals(role)) {
	    response.sendRedirect("login.jsp?error=Access+Denied");
	    return;
	}
	
	List<Map<String, String>> assignedRequests = (List<Map<String, String>>) request.getAttribute("assignedRequests");
%>



<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Repairman Dashboard</title>
    <link rel="stylesheet" href="css/repairman.css">
</head>
<body>
<%@ include file="header.jsp" %>

<div class="admin-dashboard">
    <h1>Assigned Repair Tasks</h1>

    <% if (assignedRequests == null || assignedRequests.isEmpty()) { %>
        <p class="no-requests">No repair tasks assigned to you yet.</p>
    <% } else { %>
        <% for (Map<String, String> req : assignedRequests) { %>
            <div class="card">
                <h3>Request #<%= req.get("id") %> - <%= req.get("product_details") %></h3>

                <p><strong>User ID:</strong> <%= req.get("user_id") %></p>
                <p><strong>User Name:</strong> <%= req.get("user_name") %></p>
                <p><strong>Description:</strong> <%= req.get("description") %></p>
                <p><strong>Product Details:</strong> <%= req.get("product_details") %></p>
                <p><strong>Date Requested:</strong> <%= req.get("date_requested") %></p>
               <p><strong>Photo:</strong>
				<% if (req.get("photo_path") != null && !req.get("photo_path").isEmpty()) { %>
				    <a href="<%= req.get("photo_path") %>" target="_blank">View Photo</a>
				<% } else { %>
				    <span>No photo uploaded</span>
				<% } %>
				</p>

                <p><strong>Status:</strong>
                    <% if ("accepted".equals(req.get("status"))) { %>
                        <span style="color: green;">Accepted</span>
                    <% } else { %>
                        <span style="color: red;">Pending</span>
                    <% } %>
                </p>
                <p><strong>Appointment Date:</strong> <%= req.get("appointment_date") != null ? req.get("appointment_date") : "-" %></p>

                <% if (!"accepted".equals(req.get("status"))) { %>
					<form method="post" action="AcceptRequestServlet" class="accept-form">
					    <input type="hidden" name="request_id" value="<%= req.get("id") %>">
					
					    <div class="form-flex">
					        <input type="date" name="appointment_date" required>
					        <button type="submit" onclick="return confirm('Are you sure you want to accept and set the appointment?');">Accept & Schedule</button>
					    </div>
					</form>
                <% } %>
            </div>
        <% } %>
    <% } %>
</div>

</body>
</html>
