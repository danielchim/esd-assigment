<%--
  Created by IntelliJ IDEA.
  User: kahun
  Date: 6/11/2020
  Time: 8:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Add customer</h1>
<jsp:useBean id="c" scope="request" class="bean.CustomerBean"></jsp:useBean>
<%
    String type = c.getCustid() != null? "edit":"add";
    String id = c.getCustid() != null? c.getCustid():"";
    String name = c.getCustid() != null? c.getName():"";
    String tel = c.getCustid() != null? c.getTel():"";
    int age = c.getCustid() != null? c.getAge(): Integer.parseInt("");
%>
<form action="confirmation.jsp" method="get">
    <label for="id">ID</label>
    <input type="text" name="id" id="id" value="<%=id%>">
    <label for="name">Name</label>
    <input type="text" name="name" id="name" value="<%=name%>">
    <label for="tel">Tel</label>
    <input type="text" name="tel" id="tel" value="<%=tel%>">
    <label for="age">Age</label>
    <input type="text" name="age" id="age" value="<%=age%>">
    <input type="text" name="action" id="add" value="<%=type%>" hidden>
    <input type="submit" value="Submit">
</form>
</body>
</html>

