<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.sql.*, com.hrms.dao.RequestDAO, com.hrms.util.DBConnection" %>
<%
    String email = (String) session.getAttribute("email");
    String role = (String) session.getAttribute("role");

    if (email == null || !"admin".equals(role)) {
        response.sendRedirect("login.jsp?error=Access+Denied");
        return;
    }

    List<Map<String, String>> pendingRequests = new ArrayList<>();
    List<Map<String, String>> repairmen = new ArrayList<>();

    try (Connection con = DBConnection.getConnection()) {
        RequestDAO dao = new RequestDAO(con);
        pendingRequests = dao.getPendingRequests();

        PreparedStatement ps = con.prepareStatement("SELECT email, name FROM users WHERE role='repairman'");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Map<String, String> rm = new HashMap<>();
            rm.put("email", rs.getString("email"));
            rm.put("name", rs.getString("name"));
            repairmen.add(rm);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Assign Repair Tasks - Admin | FixItHub</title>
    <link rel="stylesheet" href="css/admin.css">
</head>
<body>

<%@ include file="header.jsp" %>

<div class="admin-dashboard">
    <h1>Welcome Admin - Assign Repair Tasks</h1>

    <div class="request-list">
        <% if (pendingRequests.isEmpty()) { %>
            <p class="no-requests">🎉 All caught up! No pending repair requests.</p>
        <% } else { %>
            <% for (Map<String, String> req : pendingRequests) { %>
                <div class="card">
                    <h3>Request #<%= req.get("id") %> - <%= req.get("product_details") %></h3>

                    <p><strong>Description:</strong> <%= req.get("description") %></p>
                    <p><strong>Submitted By:</strong> <%= req.get("user_name") %></p>
                    <p><strong>Date Requested:</strong> <%= req.get("date_requested") %></p>
                    <p><strong>Serial Number:</strong> <%= req.get("serial_number") %></p>
                    <p><strong>Date of Purchase:</strong> <%= req.get("date_of_purchase") %></p>

                    <form action="AssignRepairmanServlet" method="post" class="assign-form">
                        <input type="hidden" name="request_id" value="<%= req.get("id") %>">
                        <label>Assign to:</label>
                        <select name="repairman_email" required>
                            <option value="">-- Select Repairman --</option>
                            <% for (Map<String, String> rm : repairmen) { %>
                                <option value="<%= rm.get("email") %>"><%= rm.get("name") %> (<%= rm.get("email") %>)</option>
                            <% } %>
                        </select>
                        <button type="submit">Assign</button>
                    </form>
                </div>
            <% } %>
        <% } %>
    </div>
</div>

</body>
</html>
