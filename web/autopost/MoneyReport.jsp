<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 02/12/2016
  Time: 12:47 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<div class="panel">
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table nomargin">
                <thead>
                <tr>
                    <th class="text-center">
                        Ngày
                    </th>
                    <th>Lượt click</th>
                    <th><%--Tổng tiền--%></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="record" items="${report}">
                    <tr class="">
                        <td class="text-center">${record[0]}</td>
                        <td>${record[1]}</td>
                        <td></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div><!-- table-responsive -->
    </div>
</div>

