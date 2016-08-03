<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 02/08/2016
  Time: 10:39 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="line-bottom-grid">
    <div class="grid_1">
        <h4>Report</h4>
        <canvas id="bar1" fullWidth=true height="50"></canvas>
        <script>
            var barChartData = {
                labels : [<c:forEach var="p" items="${resp.getPointChart()}" varStatus="loop">"${loop.index}",</c:forEach>],
                datasets : [
                    {
                        label: 'PAGE VIEWS',
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1,
                        data : [${resp.getPointChart()}]
                    }
                ]

            };
            var option = {
                fullWidth: true
            };
            //new Chart(document.getElementById("bar1").getContext("2d")).Bar(barChartData);
            new Chart(document.getElementById("bar1"),{type: 'bar', data: barChartData, option: option});
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
