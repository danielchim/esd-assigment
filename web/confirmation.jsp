<%--
  Created by IntelliJ IDEA.
  User: kahun
  Date: 6/11/2020
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    String type = request.getParameter("action");
    String id = request.getParameter("id");
    String name = request.getParameter("name");
    String tel = request.getParameter("tel");
    int age = Integer.parseInt(request.getParameter("age"));
%>
<form action="handleEdit" method="get">
    <h1>Are you sure these items?</h1>
    <ul>
        <li>ID : <%=id%></li>
        <li>Name : <%=name%></li>
        <li>Telephone : <%=tel%></li>
        <li>Age : <%=age%></li>
    </ul>
    <input type="text" name="id" id="id" value="<%=id%>" hidden>
    <input type="text" name="name" id="name" value="<%=name%>" hidden>
    <input type="text" name="tel" id="tel" value="<%=tel%>" hidden>
    <input type="text" name="age" id="age" value="<%=age%>" hidden>
    <input type="text" name="action" id="add" value="<%=type%>" hidden>
    <input type="submit" value="Submit">
</form>
</body>
</html>
