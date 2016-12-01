<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 02/12/2016
  Time: 12:47 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel-heading nopaddingbottom">
    <h4 class="panel-title">Cài domain trung gian</h4>
    <p>Trỏ ip domain về: 192.168.0.1</p>
</div>
<div class="panel-body">
    <hr>
    <form id="basicForm" action="form-validation.html" class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-3 control-label">Domain <span class="text-danger">*</span></label>
            <div class="col-sm-8">
                <input type="text" name="name" class="form-control" placeholder="Nhập domain trung gian..." required />
            </div>

        </div>
        <div class="alert alert-success">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
            <strong>Thành Công!</strong> Domain<a href="" class="alert-link">Đã Được Nhập Thành Công</a>.
        </div>
        <div class="alert alert-danger">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
            <strong>Thất Bại!</strong> Domain <a href="" class="alert-link">chưa trỏ IP về</a> Hãy thử lại!
        </div>
        <hr>

        <div class="row">
            <div class="col-sm-9 col-sm-offset-3">
                <button class="btn btn-success btn-quirk btn-wide mr5">Nhập</button>
                <button type="reset" class="btn btn-quirk btn-wide btn-default">Reset</button>
            </div>
        </div>

    </form>

</div><!-- panel-body -->
