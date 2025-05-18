<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Pending Repair Requests</title>
  <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<%@ include file="header.jsp" %>
<h2>Pending Repair Requests</h2>
<p class="goto"><a href="taken_repairman_requests">View Assigned Requests</a></p>

<%
    List<Map<String,String>> list = (List<Map<String,String>>) request.getAttribute("pendingRequests");
    if (list != null && !list.isEmpty()) {
%>
<table border="1" cellpadding="5">
  <tr>
    <th>ID</th><th>User</th><th>Description</th><th>Date Requested</th><th>Take</th>
  </tr>
<%
    for (Map<String,String> r : list) {
%>
  <tr>
    <td><%= r.get("id") %></td>
    <td><%= r.get("user_name") %></td>
    <td><%= r.get("description") %></td>
    <td><%= r.get("date_requested") %></td>
    <td>
      <form action="assignTask" method="post">
        <input type="hidden" name="requestId" value="<%= r.get("id") %>"/>
        Date of Appointment:
        <input type="date" name="dateAssigned" required/>
        <button type="submit">Take</button>
      </form>
    </td>
  </tr>
<%
    }
%>
</table>
<%
    } else {
%>
<p>No pending requests.</p>
<%
    }
%>
</body>
</html>
