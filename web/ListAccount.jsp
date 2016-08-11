<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 11/08/2016
  Time: 10:30 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table class="table">
    <thead>
    <tr>
        <th>User name</th>
        <th>Name</th>
        <th>Avatar</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="row" items="${listAccount}">
        <tr>
            <th>${row.getEmail()}</th>
            <th>${row.getName()}</th>
            <th>${row.getAvatar()}</th>
        </tr>
    </c:forEach>
    </tbody>
</table>