<%--
  Created by IntelliJ IDEA.
  User: kahun
  Date: 14/9/2020
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<h2>Task 1</h2>
<ul>
    <li><a href="editCustomer.jsp">Add customer</a></li>
    <li><a href="handleCustomer?action=list">List customer</a></li>
</ul>
<h2>Search Customer</h2>
<form action="handleCustomer?action=search" method="get">
    <input type="hidden" name="action" value="search">
    <input type="text" name="name">
    <input type="submit" value="Submit">
</form>

</body>
</html>

