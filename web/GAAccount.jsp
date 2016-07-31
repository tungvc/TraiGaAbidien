<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 31/07/2016
  Time: 9:51 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form action="/web/ga_account">
    <button type="submit" name="add_account" class="btn btn_5 btn-lg btn-default">Add account</button>
</form>
<table class="table">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="acc" items="${accountAdsenses}">
        <tr class="active">
            <td>${acc.getId()}</td>
            <td>${acc.getName()}</td>
            <td>${acc.getDes()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>