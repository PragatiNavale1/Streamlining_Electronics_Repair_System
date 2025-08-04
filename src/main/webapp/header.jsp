<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>FixItHub - Electronics Repair</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body class="body1">

<%
    String userEmail = (String) session.getAttribute("email");
    String sessionRole = (String) session.getAttribute("role"); // renamed to avoid conflict
%>

<nav>
  <div class="logo">
    <a href="index.jsp" style="text-decoration: none; color: white;">🔧 FixItHub</a>
  </div>

  <ul>
    <% if (userEmail == null) { %>
        <li><a href="index.jsp#about">About Us</a></li>
        <li><a href="index.jsp#services">Services</a></li>
        <li><a href="index.jsp#contact">Contact</a></li>
        <li><a href="register.jsp">Register</a></li>
        <li><a href="login.jsp">Login</a></li>
    <% } else { %>
        
        <li><a href="logout.jsp">Logout</a></li>
    <% } %>
  </ul>
</nav>

</body>
</html>
