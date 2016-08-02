<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 30/07/2016
  Time: 10:23 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<html>
<head>
    <title><tiles:getAsString name="title"/></title>
    <%--<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>--%>
    <!-- Bootstrap Core CSS -->
    <link href="/resources/css/bootstrap.min.css" rel='stylesheet' type='text/css' />
    <!-- Custom CSS -->
    <link href="/resources/css/style.css" rel='stylesheet' type='text/css' />
    <!-- Graph CSS -->
    <link href="/resources/css/font-awesome.css" rel="stylesheet">
    <!-- jQuery -->
    <!-- lined-icons -->
    <link rel="stylesheet" href="/resources/css/icon-font.min.css" type='text/css' />
    <!-- //lined-icons -->
    <!-- chart -->
    <script src="/resources/js/Chart_2.2.1.js"></script>
    <!-- //chart -->
    <!--animate-->
    <link href="/resources/css/animate.css" rel="stylesheet" type="text/css" media="all">
    <script src="/resources/js/wow.min.js"></script>
    <script>
        new WOW().init();
    </script>
    <!--//end-animate-->
    <!----webfonts--->
    <link href='//fonts.googleapis.com/css?family=Cabin:400,400italic,500,500italic,600,600italic,700,700italic' rel='stylesheet' type='text/css'>
    <!---//webfonts--->
    <!-- Meters graphs -->
    <script src="/resources/js/jquery-1.10.2.min.js"></script>
    <!-- Placed js at the end of the document so the pages load faster -->

</head>

<body class="sticky-header left-side-collapsed" onload="initMap()">
    <div class="main-content">
        <section>
            <tiles:insertAttribute name="left-side"/>
            <div class="main-content">
                <tiles:insertAttribute name="header"/>
                <div id="page-wrapper">
                    <tiles:insertAttribute name="main-content"/>
                </div>
            </div>
            <%--<tiles:insertAttribute name="footer"/>--%>
        </section>

        <%--<script src="/resources/js/jquery.nicescroll.js"></script>--%>
        <script src="/resources/js/scripts.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="/resources/js/bootstrap.min.js"></script>
    </div>
</body>
</html>
