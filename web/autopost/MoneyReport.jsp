<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 02/12/2016
  Time: 12:47 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--<div class="panel">
    <div class="panel-heading nopaddingbottom">
        <h4 class="panel-title">Cài domain trung gian</h4>
    </div>
    <div class="panel-body">
        <hr>
        <form id="basicForm" method="post" action="/web/domain/save" class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-3 control-label">Domain <span class="text-danger">*</span></label>
                <div class="col-sm-8">
                    <input type="text" name="domain" class="form-control" placeholder="http://domain.com"
                           required/>
                </div>

            </div>
            <hr>

            <div class="row">
                <div class="col-sm-9 col-sm-offset-3">
                    <button class="btn btn-success btn-quirk btn-wide mr5" type="submit">Nhập</button>
                    <button type="reset" class="btn btn-quirk btn-wide btn-default">Reset</button>
                </div>
            </div>

        </form>

    </div><!-- panel-body -->
</div>--%>
<!-- panel -->
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


<div class="panel">
    <div class="panel-body">
        <div class="row">
            <div class="col-md-2 demo">
                <h4>Choose date</h4>
                <input type="text" id="daterange" class="form-control">
                <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
            </div>
        </div>
        <br>
        <div class="table-responsive">
            <table class="table nomargin">
                <thead>
                    <tr>
                        <th class="text-center">Ngày</th>
                        <th>Lượt click</th>
                        <th>Rate</th>
                        <th>Tổng tiền</th>
                    </tr>
                </thead>
                <c:set var="total" value="${0}"/>
                <c:set var="total1" value="${0}"/>
                <c:set var="total2" value="${0}"/>
                <tbody>
                    <c:forEach var="record" items="${report}">
                        <c:set var="total" value="${total + record[1]}" />
                        <c:set var="total1" value="${total1 + record[2]}" />
                        <c:set var="total2" value="${total2 + record[1] * record[2]}" />
                        <tr class="">
                            <td class="text-center">${record[0]}</td>
                            <td><fmt:formatNumber type="number" value="${record[1]}" /></td>
                            <td>${record[2]}</td>
                            <td><fmt:formatNumber type="number" value="${record[1] * record[2]}" maxFractionDigits="0"/></td>
                        </tr>
                    </c:forEach>
                    <tr class="success">
                        <td class="text-center">TOTAL</td>
                        <td><fmt:formatNumber type="number" value="${total}" /></td>
                        <td><fmt:formatNumber type="number" value="${total1 / report.size()}" maxFractionDigits="2"/></td>
                        <td><fmt:formatNumber type="number" value="${total2}" maxFractionDigits="0"/></td>
                    </tr>
                </tbody>
            </table>
        </div><!-- table-responsive -->
    </div>
</div>

<script>
    var start = moment("${startDate}");
    var end = moment("${endDate}");

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
        window.location = "/web/money_report?startDate=" + startDate + "&endDate=" + endDate;
    });
</script>

