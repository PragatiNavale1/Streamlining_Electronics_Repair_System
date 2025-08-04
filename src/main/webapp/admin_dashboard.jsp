<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.sql.*, com.hrms.dao.RequestDAO, com.hrms.util.DBConnection" %>
<%
    String email = (String) session.getAttribute("email");
    String role = (String) session.getAttribute("role");

    if (email == null || !"admin".equals(role)) {
        response.sendRedirect("login.jsp?error=Access+Denied");
        return;
    }

    List<Map<String, String>> requests = new ArrayList<>();
    List<String> repairmen = new ArrayList<>();

    try (Connection con = DBConnection.getConnection()) {
        RequestDAO dao = new RequestDAO(con);
        requests = dao.getAdminRequestsWithStatus();

        PreparedStatement ps = con.prepareStatement("SELECT email FROM users WHERE role='repairman'");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            repairmen.add(rs.getString("email"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - FixItHub</title>
    <link rel="stylesheet" href="css/admin.css">
</head>
<body>

<%@ include file="header.jsp" %>

<div class="admin-dashboard">
    <h1>Welcome Admin - Assign Repair Tasks</h1>

    <div class="request-list">
        <% if (requests.isEmpty()) { %>
            <p class="no-requests">🎉 All caught up! No repair requests found.</p>
        <% } else { %>
            <% for (Map<String, String> req : requests) { %>
                <div class="card">
                    <h3>Request #<%= req.get("id") %> - <%= req.get("product_details") %></h3>

                    <p><strong>User:</strong> <%= req.get("user_name") %> (ID: <%= req.get("user_id") %>)</p>
                    <p><strong>Description:</strong> <%= req.get("description") %></p>
                    <p><strong>Serial Number:</strong> <%= req.get("serial_number") %></p>
                    <p><strong>Product Details:</strong> <%= req.get("product_details") %></p>
                    <p><strong>Date of Purchase:</strong> <%= req.get("date_of_purchase") %></p>
                    <p><strong>Date Requested:</strong> <%= req.get("date_requested") %></p>

                    <p><strong>Status:</strong>
                        <% if ("assigned".equalsIgnoreCase(req.get("status"))) { %>
                            <span style="color: green;">Assigned</span> to <%= req.get("repairman_email") %>
                        <% } else { %>
                            <span style="color: red;">Pending</span>
                        <% } %>
                    </p>

                    <% if ("pending".equalsIgnoreCase(req.get("status"))) { %>
                        <form action="AssignRepairmanServlet" method="post" class="assign-form">
                            <input type="hidden" name="request_id" value="<%= req.get("id") %>">
                            <label>Assign to:</label>
                            <select name="repairman_email" required>
                                <option value="">-- Select Repairman --</option>
                                <% for (String r : repairmen) { %>
                                    <option value="<%= r %>"><%= r %></option>
                                <% } %>
                            </select>
                            <button type="submit">Assign</button>
                        </form>
                    <% } %>
                </div>
            <% } %>
        <% } %>
    </div>
</div>

</body>
</html>
