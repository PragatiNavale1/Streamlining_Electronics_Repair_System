<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Your Request</title>
<link rel="stylesheet" href="css/styles.css">
</head>
<body>
<%@ include file="header.jsp" %>
<form action="addRequest" method="post">
    Description: <textarea name="description"></textarea><br>
    <input type="submit" value="Submit Request">
</form>

</body>
</html>