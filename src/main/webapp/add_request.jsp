<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Request</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<%@ include file="header.jsp" %>

<!-- Enable file upload with enctype -->
<form action="addRequest" method="post" enctype="multipart/form-data">
    <label for="serial_number">Serial Number: <span style="color:red">*</span></label><br>
    <input type="text" name="serial_number" id="serial_number" required><br><br>

    <label for="product_details">Product Details: <span style="color:red">*</span></label><br>
    <textarea name="product_details" id="product_details" required></textarea><br><br>

    <label for="date_of_purchase">Date of Purchase: <span style="color:red">*</span></label><br>
    <input type="date" name="date_of_purchase" id="date_of_purchase" required><br><br>

    <label for="photo">Upload Photo (optional):</label><br>
    <input type="file" name="photo" id="photo" accept="image/*"><br><br>

    <label for="description">Issue/Fault Description: <span style="color:red">*</span></label><br>
    <textarea name="description" id="description" required></textarea><br><br>

    <input type="submit" value="Submit Request">
</form>

</body>
</html>
