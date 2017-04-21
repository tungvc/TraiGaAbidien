<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 01/12/2016
  Time: 11:32 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <!--<link rel="shortcut icon" href="../images/favicon.png" type="image/png">-->

    <title><tiles:getAsString name="title"/></title>
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css" rel='stylesheet' type='text/css' />
    <link rel="stylesheet" href="../lib/Hover/hover.css">
    <link rel="stylesheet" href="../lib/fontawesome/css/font-awesome.css">
    <link rel="stylesheet" href="../lib/weather-icons/css/weather-icons.css">
    <link rel="stylesheet" href="../lib/ionicons/css/ionicons.css">
    <link rel="stylesheet" href="../lib/jquery-toggles/toggles-full.css">
    <link rel="stylesheet" href="../lib/morrisjs/morris.css">

    <link rel="stylesheet" href="../css/quirk.css">
    <script src="/resources/js/jquery-1.10.2.min.js"></script>
    <script src="../lib/modernizr/modernizr.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="../lib//html5shiv.js"></script>
    <script src="../lib/respond/respond.src.js"></script>
    <![endif]-->
</head>

<body>

<header>
    <div class="headerpanel">

        <div class="logopanel">
            <h2><a href="index.html">Tool Spam</a></h2>
        </div><!-- logopanel -->

        <div class="headerbar">
            <tiles:insertAttribute name="header"/>
        </div><!-- headerbar -->
    </div><!-- header-->
</header>

<section>

    <div class="leftpanel">
        <div class="leftpanelinner">

            <!-- ################## LEFT PANEL PROFILE ################## -->
            <tiles:insertAttribute name="left-side"/>

        </div><!-- leftpanelinner -->
    </div><!-- leftpanel -->

    <div class="mainpanel">

        <div class="contentpanel">

            <%--<ol class="breadcrumb breadcrumb-quirk">
                <li><a href="index.html"><i class="fa fa-home mr5"></i> Home</a></li>
                <li><a href="get.html">Spam Group</a></li>
                <li class="active">Lấy Token</li>
            </ol>--%>

            <div class="row">

                <div class="col-md-6">

                        <tiles:insertAttribute name="main-content"/>
                    </div><!-- panel -->

                </div><!-- col-md-6 -->

                <div class="col-md-6">



                </div><!-- col-md-6 -->

            </div><!--row -->
        </div><!-- panel-body -->
    </div><!-- panel -->
    </div><!-- col-md-6 -->

    </div><!-- row-->

    </div><!-- contentpanel -->

    </div><!-- mainpanel -->
    <style>
        #dialog-form{
            visibility:hidden;
            position:absolute;
            border:2px solid #3c3c3c;
            z-index:3001;
            width: 800px;
            height:400px;
            padding:20px;
            background-color: #fff;
            font-size: large;
        }
        .dimmer{
            background: #000;
            position: fixed;
            opacity: .5;
            top: 0;
            z-index:3000;
        }
        .listLink{
            width: 80%;
            font-size: small;
            float: left;
        }
        .btnCopy {
            width: 18%;
            height: 2em;
            float: left;
        }
    </style>

    <div id="dialog-form" style="visibility: hidden; display: table;" title="Create new user">
    </div>

</section>

<%--<script src="../lib/jquery/jquery.js"></script>
<script src="../lib/jquery-ui/jquery-ui.js"></script>
<script src="../lib/bootstrap/js/bootstrap.js"></script>
<script src="../lib/jquery-toggles/toggles.js"></script>

<script src="../lib/morrisjs/morris.js"></script>
<script src="../lib/raphael/raphael.js"></script>

<script src="../lib/flot/jquery.flot.js"></script>
<script src="../lib/flot/jquery.flot.resize.js"></script>
<script src="../lib/flot-spline/jquery.flot.spline.js"></script>

<script src="../lib/jquery-knob/jquery.knob.js"></script>

<script src="../js/quirk.js"></script>
<script src="../js/dashboard.js"></script>--%>

<script src="/resources/js/scripts.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="/resources/js/bootstrap.min.js"></script>

</body>
</html>
