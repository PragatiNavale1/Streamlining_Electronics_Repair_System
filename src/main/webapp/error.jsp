<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h2 style="color:red;">⚠️ An error occurred</h2>
    <p><%= request.getAttribute("error") != null ? request.getAttribute("error") : "Unknown error." %></p>
    <a href="index.jsp">Back to Home</a>
</body>
</html>
