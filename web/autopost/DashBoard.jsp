<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 01/12/2016
  Time: 11:31 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="panel">
    <div class="panel-heading nopaddingbottom">
        <h4 class="panel-title">Cài đặt</h4>
        <p>Cập nhật domain trung gian trước khi cài đặt.</p>
    </div>
    <div class="panel-body">
        <hr>

        <form id="basicForm" method="post" action="/web/dashboard/save" class="form-horizontal">
            <%--<div class="form-group">
                <label class="col-sm-3 control-label">Chọn domain trung gian <span class="text-danger">*</span></label>
                <div class="col-sm-8">
                    <select id="select1" class="form-control" style="width: 100%" data-placeholder="Basic Select2 Box">
                        <option value="">&nbsp;</option>
                        <option value="bienthanh.net">bienthanh.net</option>
                        <option value="bienthanh.net">bienthanh.net</option>
                        <option value="bienthanh.net">bienthanh.net</option>
                    </select>
                </div>
            </div>--%>


            <div class="form-group">
                <label class="col-sm-3 control-label">Link cần kéo views <span class="text-danger">*</span></label>
                <div class="col-sm-8">
                    <input type="text" name="targetUrl" class="form-control" placeholder="Nhập Link Spam..." required />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">Tiêu đề <span class="text-danger">*</span></label>
                <div class="col-sm-8">
                    <input type="text" name="title" class="form-control" placeholder="Nhập tiêu đề bài viết..." required />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">Mô tả <span class="text-danger">*</span></label>
                <div class="col-sm-8">
                    <input type="text" name="description" class="form-control" placeholder="Nhập mô tả bài viết..." required />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">Link ảnh<span class="text-danger">*</span></label>
                <div class="col-sm-8">
                    <input type="text" name="imageUrl" class="form-control" placeholder="" required />
                </div>
            </div>


            <%--<div class="alert alert-success">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <strong>Thành Công!</strong> Link<a href="" class="alert-link"> Đã Được tạo Thành Công</a>.
            </div>
            <div class="alert alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <strong>Thất Bại!</strong> Có lỗi khi<a href="" class="alert-link"> tạo link</a> Hãy thử lại!
            </div>
            <hr>--%>

            <div class="row">
                <div class="col-sm-9 col-sm-offset-3">
                    <button type="submit" class="btn btn-success btn-quirk btn-wide mr5">Tạo</button>
                    <button type="reset" class="btn btn-quirk btn-wide btn-default">Nhập lại</button>
                </div>
            </div>

        </form>
    </div>
</div>
<div class="panel">
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table nomargin">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Link</th>
                    <th>Tiêu đề</th>
                    <th>Mô tả</th>
                    <th>Link ảnh</th>
                    <th>Click</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="fakeLink" items="${fakeLinkList}">
                    <tr style="cursor: pointer;">
                        <td >${fakeLink.getId()}</td>
                        <td onclick="getLink('${fakeLink.getId()}', '${fakeLink.getTitle()}')">${fakeLink.getTargetUrl()}</td>
                        <td onclick="getLink('${fakeLink.getId()}', '${fakeLink.getTitle()}')">${fakeLink.getTitle()}</td>
                        <td onclick="getLink('${fakeLink.getId()}', '${fakeLink.getTitle()}')">${fakeLink.getDescription()}</td>
                        <td onclick="getLink('${fakeLink.getId()}', '${fakeLink.getTitle()}')">${fakeLink.getImageUrl()}</td>
                        <td onclick="getLink('${fakeLink.getId()}', '${fakeLink.getTitle()}')">${fakeLink.getClick()}</td>
                        <td>
                            <ul class="table-options">
                                    <%--<li><a href=""><i class="fa fa-pencil"></i></a></li>--%>
                                <li><a href="/web/dashboard/disable?id=${fakeLink.getId()}"><i class="fa fa-trash"></i></a></li>
                            </ul>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div><!-- table-responsive -->
    </div>
</div>

<script src="/resources/js/jquery-ui.js"></script>

<script>
    function httpGetAsync(url, callback)
    {
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.onreadystatechange = function() {
            if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
                callback(xmlHttp.responseText);
                console.log(xmlHttp.responseText);
            }
        }
        xmlHttp.open("GET", url, true); // true for asynchronous
        xmlHttp.send(null);
    }

    function getLink(id, title) {
        var dialog = document.getElementById("dialog-form"),
        dimmer = document.createElement("div");
        dimmer.style.width =  window.innerWidth + 'px';
        dimmer.style.height = window.innerHeight + 'px';
        dimmer.className = 'dimmer';

        dimmer.onclick = function(){
            document.body.removeChild(this);
            dialog.style.visibility = 'hidden';
        }

        document.body.appendChild(dimmer);

        dialog.style.visibility = 'visible';
        dialog.style.top = window.innerHeight/2 - 400/2 + 'px';
        dialog.style.left = window.innerWidth/2 - 800/2 + 'px';

        while (dialog.firstChild) {
            dialog.removeChild(dialog.firstChild);
        }
        httpGetAsync("/web/dashboard/genLink?id=" + id, function (data) {
            //var array = JSON.parse(data).toString().split(',').join("\n")
            var array = JSON.parse(data);
            for (var i = 0; i < array.length; i++) {
                var textArea = document.createElement("textarea");
                var btn = document.createElement("button");
                textArea.value = title + "\n" + array[i];
                textArea.id = "link" + i;
                textArea.className  = "listLink";
                btn.innerHTML = "Copy";
                btn.className = "btnCopy";
                btn.onclick = (function() {
                    var current = i;
                    return function() {
                        copy("link" + current);
                    }
                })();
                dialog.appendChild(textArea);
                dialog.appendChild(btn);
            }
        });
    }

    function copy(id) {
        var copyTextarea = document.getElementById(id);
        copyTextarea.select();

        try {
            var successful = document.execCommand('copy');
            /*var msg = successful ? 'successful' : 'unsuccessful';
            console.log('Copying text command was ' + msg);*/
        } catch (err) {
            console.log('Oops, unable to copy');
        }
    }
</script>