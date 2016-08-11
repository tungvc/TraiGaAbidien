<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 31/07/2016
  Time: 9:51 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form action="/web/ga_account">
    <button type="submit" name="add_account" class="btn btn_5 btn-lg btn-default">Add account</button>
</form>
<table class="table">
    <thead>
    <tr>
        <th>Name</th>
        <th>Account</th>
        <th>Ad Client</th>
        <th>Alert</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="acc" items="${accountAdsenses}">
        <tr class="active">
            <td>${acc.getName()}</td>
            <td>${acc.getAccountId()}</td>
            <td>${acc.getAdClientId()}</td>
            <td>${acc.getAlert()}</td>
            <td>
                <a href="/web/report?id=${acc.getId()}&accountId=${acc.getAccountId()}&adClientId=${acc.getAdClientId()}" class="btn btn_5 btn-lg btn-info">View Report</a>
                <c:if test="${acc.getUser() == user.getId()}">
                <button id="create-user" onclick="shareUser('${acc.getId()}')" class="btn btn_5 btn-lg btn-info">Share</button>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<style>
    #dialog-form{
        visibility:hidden;
        position:absolute;
        border:2px solid #3c3c3c;
        color:white;
        z-index:100;
        width: 500px;
        height:200px;
        padding:20px;
        background-color: #fff;
    }
    .dimmer{
        background: #000;
        position: absolute;
        opacity: .5;
        top: 0;
        z-index:99;
    }
</style>

<div id="dialog-form" style="visibility: hidden" title="Create new user">
    <form action="/web/ga_account/share" method="post">
        <div class="sign-u">
            <div class="sign-up1">
                <h4>Account* :</h4>
            </div>
            <div class="sign-up2">
                <input type="text" style="color: black" name="shareUser" placeholder="User name" required pattern=".{4,}" title="Minimum 4 characters"/>
            </div>
            <div class="clearfix"></div>
        </div>
        <input type="hidden" name="adsense" id="adsense" >
        <div class="sign-u" style="text-align: center">
            <button type="submit" class="btn btn_5 btn-lg btn-success warning_1"
                    style="width: 200px;margin-top: 30px;">Share
            </button>
        </div>
    </form>
</div>

<script src="/resources/js/jquery-ui.js"></script>
<script>
    function shareUser(adsenseId) {
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
        dialog.style.top = window.innerHeight/2 - 100 + 'px';
        dialog.style.left = window.innerWidth/2 - 250 + 'px';

        document.getElementById("adsense").value = adsenseId;
    }
</script>