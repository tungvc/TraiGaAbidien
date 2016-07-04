<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 11/06/2016
  Time: 10:02 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, abidien.chuongga.Test" %>
<%@ page import="abidien.models.UserEntity" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  Current date is: <%=new java.util.Date()%><br/>
  <ul>
    <c:forEach var="i" items="${dm}">
    <li>
        <c:out value="${i.id} - ${i.name}"/>
    </li>
    </c:forEach>
  </ul>
  </body>
</html>
