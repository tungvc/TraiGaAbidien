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
        <th>Name</th>
        <th>Account</th>
        <th>Ad Client</th>
        <th>Alert</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="acc" items="${accountAdsenses}">
        <tr class="active">
            <td>${acc.getName()}</td>
            <td>${acc.getAccountId()}</td>
            <td>${acc.getAdClientId()}</td>
            <td>${acc.getAlert()}</td>
            <td><a href="/web/report?id=${acc.getId()}&accountId=${acc.getAccountId()}&adClientId=${acc.getAdClientId()}" class="btn btn_5 btn-lg btn-info">View Report</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>