<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 02/12/2016
  Time: 12:47 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="panel">
    <div class="panel-heading nopaddingbottom">
        <h4 class="panel-title">Cài domain trung gian</h4>
        <p>Trỏ ip domain về: 192.168.0.1</p>
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
            <%--<div class="alert alert-success">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <strong>Thành Công!</strong> Domain<a href="" class="alert-link">Đã Được Nhập Thành Công</a>.
            </div>
            <div class="alert alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <strong>Thất Bại!</strong> Domain <a href="" class="alert-link">chưa trỏ IP về</a> Hãy thử lại!
            </div>--%>
            <hr>

            <div class="row">
                <div class="col-sm-9 col-sm-offset-3">
                    <button class="btn btn-success btn-quirk btn-wide mr5" type="submit">Nhập</button>
                    <button type="reset" class="btn btn-quirk btn-wide btn-default">Reset</button>
                </div>
            </div>

        </form>

    </div><!-- panel-body -->
</div>
<!-- panel -->
<div class="panel">
    <div class="panel-heading">
        <h4 class="panel-title">Domain Được Nhập</h4>
        <p>Quản lý domain được nhập ở đây</p>
    </div>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table nomargin">
                <thead>
                <tr>
                    <th class="text-center">
                        <label class="ckbox ckbox-primary">
                            <input type="checkbox"><span></span>
                        </label>
                    </th>
                    <th>Domain</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="domain" items="${domainList}">
                    <tr class="">
                        <td class="text-center">
                            <label class="ckbox ckbox-primary">
                                <input type="checkbox"><span></span>
                            </label>
                        </td>
                        <td>${domain.getDomain()}</td>
                        <td>
                            <ul class="table-options">
                                <%--<li><a href=""><i class="fa fa-pencil"></i></a></li>--%>
                                <li><a href="/web/domain/delete?id=${domain.getId()}"><i class="fa fa-trash"></i></a></li>
                            </ul>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div><!-- table-responsive -->
    </div>
</div>

