<%--
  Created by IntelliJ IDEA.
  User: ABIDIEN
  Date: 11/08/2016
  Time: 9:50 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="page-wrapper" class="sign-in-wrapper">
    <form action="/web/user/save" method="post">
        <div class="graphs">
            <div class="sign-up">
                <div class="sign-u">
                    <div class="sign-up1">
                        <h4>Account* :</h4>
                    </div>
                    <div class="sign-up2">
                        <input type="text" name="email" placeholder="User name" required pattern=".{4,}" title="Minimum 4 characters"/>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="sign-u">
                    <div class="sign-up1">
                        <h4>Name* :</h4>
                    </div>
                    <div class="sign-up2">
                        <form>
                            <input type="text" name="name" placeholder="Your name" required/>
                        </form>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="sign-u">
                    <div class="sign-up1">
                        <h4>Password* :</h4>
                    </div>
                    <div class="sign-up2">
                        <input type="password" id="password" name="password" placeholder="Password" required pattern=".{6,}" title="Minimum 6 characters"/>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="sign-u">
                    <div class="sign-up1">
                        <h4>Confirm Password* :</h4>
                    </div>
                    <div class="sign-up2">
                        <input type="password" name="confirmPassword" placeholder="Confirm Password" required pattern=".{6,}" title="Minimum 6 characters" oninput="check(this)"/>
                    </div>
                    <script language='javascript' type='text/javascript'>
                        function check(input) {
                            if (input.value != document.getElementById('password').value) {
                                input.setCustomValidity('Password không trùng khớp');
                            } else {
                                // input is valid -- reset the error message
                                input.setCustomValidity('');
                            }
                        }
                    </script>
                    <div class="clearfix"></div>
                </div>
                <div class="sign-u" style="text-align: center">
                    <button type="submit" class="btn btn_5 btn-lg btn-success warning_1"
                            style="width: 200px;margin-top: 30px;">Submit
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
