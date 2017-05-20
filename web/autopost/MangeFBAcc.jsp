<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 02/12/2016
  Time: 12:47 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- panel -->
<div class="panel">
    <div class="panel-heading">
        <h4 class="panel-title">Quản lý tài khoản facebook</h4>
    </div>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table nomargin">
                <thead>
                <tr>
                    <th>Tài khoản</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="acc" items="${accList}">
                    <tr class="">
                        <td>${acc.getName()}</td>
                        <td>
                            <ul class="table-options">
                                <%--<li><a href=""><i class="fa fa-pencil"></i></a></li>--%>
                                <li><a href="" onclick="del(${acc.getId()})"><i class="fa fa-trash"></i></a></li>
                            </ul>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div><!-- table-responsive -->
    </div>
</div>
<script>
    function del(id) {
        httpGetAsync("/web/fb_acc/disable?id=" + id, function(){
            location.reload();
        });
    }

    function httpGetAsync(theUrl, callback) {
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.onreadystatechange = function() {
            if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
                callback(xmlHttp.responseText);
        }

        xmlHttp.open("GET", theUrl, true); // true for asynchronous
        xmlHttp.send(null);
    }
</script>

