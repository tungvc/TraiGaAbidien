<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 30/07/2016
  Time: 10:30 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="left-side sticky-left-side">

    <!--logo and iconic logo start-->
    <div class="logo">
        <h1><a href="index.html">Trại <span>Gà</span></a></h1>
    </div>
    <div class="logo-icon text-center">
        <a href="/web/dashboard"><i class="lnr lnr-home"></i> </a>
    </div>

    <!--logo and iconic logo end-->
    <div class="left-side-inner">

        <!--sidebar nav start-->
        <ul class="nav nav-pills nav-stacked custom-nav">
            <li class="active"><a href="/web/dashboard"><i class="lnr lnr-power-switch"></i><span>Dashboard</span></a></li>
            <li><a href="/web/ga_account"><i class="lnr lnr-cog"></i><span>Quản lý gà</span></a></li>
            <c:if test="${user.getName().equals(\"admin\")}">
            <li class="menu-list"><a href="#"><i class="fa fa-users"></i> <span>Tài khoản</span></a>
                <ul class="sub-menu-list">
                    <li><a href="/web/user/list">Quản lý tài khoản</a> </li>
                    <li><a href="/web/user/create">Tạo tài khoản</a></li>
                </ul>
            </li>
            </c:if>
        </ul>
        <!--sidebar nav end-->
    </div>
</div>
