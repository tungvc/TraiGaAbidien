<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 02/08/2016
  Time: 10:39 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="line-bottom-grid">
    <div class="grid_1">
        <h4>Report</h4>
        <canvas id="bar1" height="100" width="600" style="width: 600px; height: 100px;"></canvas>
        <script>
            var barChartData = {
                labels : ["Mon","Tue","Wed","Thu","Fri","Sat","Mon","Tue","Wed","Thu"],
                datasets : [
                    {
                        fillColor : "#8BC34A",
                        strokeColor : "#8BC34A",
                        data : [30,45,55,70,40,25,15,8,5,2]
                    }
                ]

            };
            new Chart(document.getElementById("bar1").getContext("2d")).Bar(barChartData);
        </script>
    </div>
</div>

<table class="table">
    <thead>
    <tr>
        <c:forEach var="column" items="${resp.getHeader()}">
            <th>${column}</th>
        </c:forEach>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="row" items="${resp.getData()}">
        <tr class="active">
            <c:forEach var="data" items="${row}">
                <td>${data}</td>
            </c:forEach>
        </tr>
    </c:forEach>
    </tbody>
</table>
