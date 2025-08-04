<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - FixItHub</title>
    <link rel="stylesheet" href="css/login.css">
   

</head>
<body>

<%@ include file="header.jsp" %>

<div class="register-wrapper">
    <div class="register-box">
        <h2>Create Your Account</h2>
        <form action="register" method="POST">
            <input type="text" name="name" placeholder="Full Name" required>
            <input type="email" name="email" placeholder="Email" required>

            <select name="role" required>
                <option value="" disabled selected>Select Role</option>
                <option value="user">User</option>
                <option value="repairman">Repairman</option>
                <option value="admin">Admin</option>
            </select>

            <input type="password" name="password" placeholder="Password" required>
            <button type="submit">Register</button>
        </form>
        <p>Already have an account? <a href="login.jsp">Login here</a></p>
    </div>
</div>

</body>
</html>
