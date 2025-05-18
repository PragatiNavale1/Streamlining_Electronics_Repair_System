<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Repair Requests</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<%@ include file="header.jsp" %>

<%
    String email = (String) session.getAttribute("email");
    List<Map<String, String>> requests = (List<Map<String, String>>) request.getAttribute("requests");

    out.println("<p>Logged-in user email: " + email + "</p>");
    out.println("<p>Number of requests fetched: " + (requests != null ? requests.size() : "null") + "</p>");
%>

<h2>Your Repair Requests</h2>

<% if (requests != null && !requests.isEmpty()) { %>
    <table border="1" cellpadding="8">
        <tr>
            <th>ID</th>
            <th>User ID</th>
            <th>Description</th>
            <th>Date Requested</th>
            <th>Status</th>
            <th>Taken By</th>
            <th>Appointment Date</th>
            <th>Repairman Email</th>
        </tr>
        <% for (Map<String, String> row : requests) { %>
        <tr>
            <td><%= row.get("id") %></td>
            <td><%= row.get("user_id") %></td>
            <td><%= row.get("description") %></td>
            <td><%= row.get("date_requested") %></td>
            <td><%= row.get("status") %></td>
            <td><%= row.get("taken_by") != null ? row.get("taken_by") : "-" %></td>
            <td><%= row.get("appointment_date") != null ? row.get("appointment_date") : "-" %></td>
            <td><%= row.get("repairman_email") != null ? row.get("repairman_email") : "-" %></td>
        </tr>
        <% } %>
    </table>
<% } else { %>
    <p>No requests found.</p>
<% } %>

</body>
</html>
