<%@ page import="bean.CustomerBean" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: kahun
  Date: 6/11/2020
  Time: 8:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    ArrayList<CustomerBean> customers = (ArrayList<CustomerBean>)request.getAttribute("customers");
    out.println("<h1>Customers</h1>");
    out.println("<table border='1'>");
    out.println("<tr>");
    out.println("<th>CustId</th>  <th> name</th><th> tel</th><th> age</th >");
    out.println("</tr>");
    // loop through the customer array to display each customer record
    for (int i = 0; i < customers.size(); i++) {
        CustomerBean c = customers.get(i);
        out.println("<tr>");

        out.println("<td>" + c.getCustid() + "</td>");
        out.println("<td>" + c.getName() + "</td>");
        out.println("<td>" + c.getTel() + "</td>");
        out.println("<td>" + c.getAge() + "</td>");
        out.println("<td><a href=\"handleCustomer?action=delete&id=" + c.getCustid() + "\">delete</a></td>");
        out.println("<td><a href=\"handleCustomer?action=getEditCustomer&id=" + c.getCustid() + "\">Edit</a></td>");
        out.println("</tr>");

    }
    out.println("</table>");
%>

</body>
</html>
