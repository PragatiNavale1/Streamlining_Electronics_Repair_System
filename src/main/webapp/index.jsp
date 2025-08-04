<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome Page</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/styles.css">
    

</head>
<body>

<%@ include file="header.jsp" %>

<div class="container">
    <div class="left">
        <h1>REPAIR SERVICE</h1>
        <h3>REPAIR & SERVICE AT YOUR DOORSTEP</h3>
        <p>Why replace when you can repair? We breathe new life into electronics—because every device deserves a second chance.</p>
        <div class="buttons">
            <a href="login.jsp">Login</a>
        </div>
    </div>
    <div class="right">
        <img src="css/Home_page.png" alt="Repair Illustration">
    </div>
</div>

<!-- About Us Section -->
<div class="section theme-blue" id="about">
    <h2>About Us</h2>
    <div class="cards">
        <div class="card theme-card">
            <img src="css/repairman-team.jpg" alt="Team">
            <h3>Who We Are</h3>
            <p>We are a team of passionate engineers focused on bringing damaged electronics back to life — one component at a time.</p>
        </div>
        <div class="card theme-card">
            <img src="css/mission.png" alt="Mission">
            <h3>Our Mission</h3>
            <p>Repair, reuse, reduce e-waste. Our mission is to offer fast, affordable, and eco-friendly electronic repair solutions.</p>
        </div>
    </div>
</div>

<!-- Services Section -->
<div class="section theme-light" id="services">
    <h2>Our Services</h2>
    <div class="cards">
        <div class="card theme-card">
            <img src="css/laptop.jpg" alt="Mobile Repair">
            <h3>Mobile Repair</h3>
            <p>Screen replacement, battery issues, motherboard repair & water damage fixes.</p>
        </div>
        <div class="card theme-card">
            <img src="css/laptop-repair.webp" alt="Laptop Repair">
            <h3>Laptop & PC</h3>
            <p>Fixing slow systems, OS installation, keyboard repairs, and data recovery.</p>
        </div>
        <div class="card theme-card">
            <img src="css/appliances-rapair.avif" alt="Appliance Repair">
            <h3>Electronics & Appliances</h3>
            <p>ACs, washing machines, routers, and custom industrial electronic boards.</p>
        </div>
    </div>
</div>

<!-- Contact Us Section -->
<div class="section theme-blue" id="contact">
    <h2>Contact Us</h2>
    <div class="contact-form">
        <input type="text" placeholder="Your Name" required>
        <input type="email" placeholder="Your Email" required>
        <textarea rows="5" placeholder="Your Message" required></textarea>
        <button type="submit">Send Message</button>
    </div>
</div>

<%@ include file="footer.jsp" %>

</body>
</html>
