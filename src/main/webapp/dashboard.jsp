<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/styles.css">
</head>
<body>
<%@ include file="header.jsp" %>
<%
    String email = (String) session.getAttribute("email");

    if (email != null) {
        out.println("<p>Logged in as: " + email + "</p>");
    } else {
        out.println("<p>User is not logged in.</p>");
    }
%>

<h2>Welcome to your Dashboard</h2>
<p class="goto"><a href="view_requests">Your Repair Request</a> |  <a href="add_request.jsp">Submit Repair Request</a></p>

</body>
</html>