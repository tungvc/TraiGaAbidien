<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 02/08/2016
  Time: 10:39 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css" media="all" href="/resources/css/daterangepicker.css"/>
<script type="text/javascript" src="/resources/js/moment.min.js"></script>
<script type="text/javascript" src="/resources/js/daterangepicker.js"></script>

<style type="text/css">
    .demo {
        position: relative;
    }

    .demo i {
        position: absolute;
        bottom: 10px;
        right: 24px;
        top: auto;
        cursor: pointer;
    }

    .btn-bootrap-success {
        border-color: #4cae4c;
        border: 1px solid transparent;
        background-color: #5cb85c;
        padding: 6px 12px;
    }
</style>
<div class="row">
    <div class="col-md-2 demo">
        <h4>Choose date</h4>
        <input type="text" id="daterange" class="form-control">
        <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
    </div>
</div>

<script>
    var start = moment("${startDate}");
    var end = moment("${endDate}");
    var adsenseId = "${adsenseId}";
    var accountId = "${accountId}";
    var adClientId = "${adClientId}";

    $('#daterange').daterangepicker({
        "showDropdowns": true,
        "ranges": {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        },
        "linkedCalendars": false,
        "alwaysShowCalendars": true,
        "startDate": start,
        "endDate": end,
        "maxDate": moment(),
        "applyClass": "btn-success btn-bootrap-success"
    }, function (start, end, label) {
        console.log(start.valueOf());
        console.log(end);
        console.log(label);
        console.log("New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')");
    });

    $('#daterange').on('apply.daterangepicker', function (ev, picker) {
        //do something, like clearing an input
        var startDate = picker.startDate.format('YYYY-MM-DD');
        var endDate = picker.endDate.format('YYYY-MM-DD');
        window.location = "/web/report?id=" + adsenseId + "&accountId=" + accountId + "&adClientId=" + adClientId + "&startDate=" + startDate + "&endDate=" + endDate;
    });
</script>

<div class="line-bottom-grid">
    <div class="grid_1">
        <h4>Report</h4>
        <canvas id="bar1" fullWidth=true height="50"></canvas>
        <script>
            var barChartData = {
                labels: [<c:forEach var="p" items="${resp.getData()}" varStatus="loop">
                    <c:if test="${!loop.first}">
                    "${p.get(0).substring(5, 10)}",
                    </c:if>
                    </c:forEach>],
                datasets: [
                    {
                        label: 'PAGE VIEWS',
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1,
                        data: [${resp.getPointChart()}]
                    }
                ]

            };
            var option = {
                fullWidth: true
            };
            //new Chart(document.getElementById("bar1").getContext("2d")).Bar(barChartData);
            new Chart(document.getElementById("bar1"), {type: 'bar', data: barChartData, option: option});
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
    <c:forEach var="row" items="${resp.getData()}" varStatus="loop">
        <tr class="${loop.first ? "success" : "active"}">
            <c:forEach var="data" items="${row}">
                <td>${data}</td>
            </c:forEach>
        </tr>
    </c:forEach>
    </tbody>
</table>
