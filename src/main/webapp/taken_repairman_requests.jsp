<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Assigned Repair Requests</title>
  <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<%@ include file="header.jsp" %>
<h2>Your Assigned Requests</h2>
<p class="goto"><a href="pending_repairman_requests">View Pending Requests</a></p>

<%
    List<Map<String,String>> assigned = (List<Map<String,String>>) request.getAttribute("assignedRequests");
    if (assigned != null && !assigned.isEmpty()) {
%>
<table border="1">
  <tr>
    <th>ID</th><th>User</th><th>Description</th><th>Date Requested</th><th>Status</th><th>Appointment Date</th>
  </tr>
<%
    for (Map<String,String> r : assigned) {
%>
  <tr>
    <td><%= r.get("id") %></td>
    <td><%= r.get("user_name") %></td>
    <td><%= r.get("description") %></td>
    <td><%= r.get("date_requested") %></td>
    <td><%= r.get("status") %></td>
    <td><%= r.get("appointment_date") %></td>
  </tr>
<%
    }
%>
</table>
<%
    } else {
%>
<p>No assigned requests.</p>
<%
    }
%>
</body>
</html>
