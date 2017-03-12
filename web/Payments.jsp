<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 12/03/2017
  Time: 12:41 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table class="table">
    <thead>
    <tr>
        <th>Date</th>
        <th>Amount</th>
        <th>Currency</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="row" items="${payments}">
        <tr>
            <th>${row.getId()}</th>
            <th>${row.getPaymentAmount()}</th>
            <th>${row.getPaymentAmountCurrencyCode()}</th>
        </tr>
    </c:forEach>
    </tbody>
</table>