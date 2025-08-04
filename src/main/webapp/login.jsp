<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - FixItHub</title>
    <link rel="stylesheet" href="css/login.css">
   
</head>
<body>

<%@ include file="header.jsp" %>

<div class="login-wrapper">
    <div class="login-box">
        <h2>Login to Your Account</h2>
        <form action="login" method="post">
            <input type="text" name="email" placeholder="Enter Email" required>
            <input type="password" name="password" placeholder="Enter Password" required>
            <input type="submit" value="Login">
        </form>
        <p>Don’t have an account? <a href="register.jsp">Register here</a></p>
    </div>
</div>

</body>
</html>
