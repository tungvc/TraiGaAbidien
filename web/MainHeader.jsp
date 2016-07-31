<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 31/07/2016
  Time: 9:42 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- header-starts -->
<div class="header-section">

    <!--toggle button start-->
    <a class="toggle-btn  menu-collapsed"><i class="fa fa-bars"></i></a>
    <!--toggle button end-->

    <!--notification menu start -->
    <div class="menu-right">
        <div class="user-panel-top">
            <div class="profile_details_left">
            </div>
            <div class="profile_details">
                <ul>
                    <li class="dropdown profile_details_drop">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                            <div class="profile_img">
                                <span style="background:url(/resources/images/1.jpg) no-repeat center"> </span>
                                <div class="user-name">
                                    <p>${user}<span>Administrator</span></p>
                                </div>
                                <i class="lnr lnr-chevron-down"></i>
                                <i class="lnr lnr-chevron-up"></i>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                        <ul class="dropdown-menu drp-mnu">
                            <li> <a href="#"><i class="fa fa-cog"></i> Settings</a> </li>
                            <li> <a href="#"><i class="fa fa-user"></i>Profile</a> </li>
                            <li> <a href="/web/login?logout=true"><i class="fa fa-sign-out"></i> Logout</a> </li>
                        </ul>
                    </li>
                    <div class="clearfix"> </div>
                </ul>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
    <!--notification menu end -->
</div>
<!-- //header-ends -->