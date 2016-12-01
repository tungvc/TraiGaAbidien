<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 01/12/2016
  Time: 11:31 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel-heading nopaddingbottom">
    <h4 class="panel-title">Cài đặt</h4>
    <p>Cập nhật domain trung gian trước khi cài đặt.</p>
</div>
<div class="panel-body">
    <hr>

    <form id="basicForm" action="form-validation.html" class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-3 control-label">Chọn domain trung gian <span class="text-danger">*</span></label>
            <div class="col-sm-8">
                <select id="select1" class="form-control" style="width: 100%" data-placeholder="Basic Select2 Box">
                    <option value="">&nbsp;</option>
                    <option value="bienthanh.net">bienthanh.net</option>
                    <option value="bienthanh.net">bienthanh.net</option>
                    <option value="bienthanh.net">bienthanh.net</option>
                </select>
            </div>
        </div>


        <div class="form-group">
            <label class="col-sm-3 control-label">Link cần kéo views <span class="text-danger">*</span></label>
            <div class="col-sm-8">
                <input type="text" name="name" class="form-control" placeholder="Nhập Link Spam..." required />
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label">Tiêu đề <span class="text-danger">*</span></label>
            <div class="col-sm-8">
                <input type="text" name="name" class="form-control" placeholder="Nhập tiêu đề bài viết..." required />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">Mô tả <span class="text-danger">*</span></label>
            <div class="col-sm-8">
                <input type="text" name="name" class="form-control" placeholder="Nhập mô tả bài viết..." required />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">Link ảnh<span class="text-danger">*</span></label>
            <div class="col-sm-8">
                <input type="text" name="name" class="form-control" placeholder="link dạng i.imgur.com ..." required />
            </div>
        </div>


        <div class="alert alert-success">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
            <strong>Thành Công!</strong> Link<a href="" class="alert-link"> Đã Được tạo Thành Công</a>.
        </div>
        <div class="alert alert-danger">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
            <strong>Thất Bại!</strong> Có lỗi khi<a href="" class="alert-link"> tạo link</a> Hãy thử lại!
        </div>
        <hr>

        <div class="row">
            <div class="col-sm-9 col-sm-offset-3">
                <button class="btn btn-success btn-quirk btn-wide mr5">Tạo</button>
                <button type="reset" class="btn btn-quirk btn-wide btn-default">Nhập lại</button>
            </div>
        </div>

    </form>
