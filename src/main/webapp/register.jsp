<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
<link rel="stylesheet" href="css/styles.css">
</head>
<body>
<%@ include file="header.jsp" %>
<h2>Register</h2>
    <form action="register" method="POST">
        <input type="text" name="name" placeholder="Full Name" required><br><br>
        <input type="email" name="email" placeholder="Email" required><br><br>
        
        <!-- Role selector -->
        <label for="role">Role:</label>
        <select name="role" id="role" required>
            <option value="user">User</option>
            <option value="repairman">Repairman</option>
            <option value="admin">Admin</option>
        </select>
        <br><br>
        
        <input type="password" name="password" placeholder="Password" required><br><br>
        <button type="submit">Register</button>
    </form>
    <p>Already have an account? <a href="login.jsp">Login here</a></p>
</body>
</html>