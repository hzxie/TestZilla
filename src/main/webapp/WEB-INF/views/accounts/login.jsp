<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="date" class="java.util.Date" />
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Sign In | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/semantic.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/font-awesome.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/style.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/accounts/login.css" />" />
    <!-- JavaScript -->
    <script type="text/javascript" src="<c:url value="/assets/js/jquery-1.11.1.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/semantic.min.js" />"></script>
</head>
<body>
    <!-- Header -->
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <!-- Content -->
    <div id="content">
        <h2>Sign In</h2>
        <div class="ui error message">
            <div class="header">Action Forbidden</div>
            <p>Invalid username or password.</p>
        </div> <!-- .error -->
        <c:if test="${isLogout}">
        <div class="ui positive message">
            <p>You're now logged out.</p>
        </div> <!-- .positive -->    
        </c:if>
        <form id="login-form" class="ui form attached fluid segment" onSubmit="onSubmit(); return false;">
            <div class="field">
                <label>Username/Email</label>
                <div class="ui icon input">
                    <input id="username" type="text" placeholder="Username or Email" />
                    <i class="icon user"></i>
                </div> <!-- .input -->
            </div> <!-- .field -->
            <div class="field">
                <label>Password<a href="<c:url value="/accounts/resetPassword" />">Forgot Password</a></label>
                <div class="ui icon input">
                    <input id="password" type="password" placeholder="Password" />
                    <i class="icon lock"></i>                
                </div> <!-- .input -->
            </div> <!-- .field -->
            <div class="inline field">
                <div class="ui checkbox">
                    <input id="remember-me" type="checkbox" />
                    <label>Remember Me</label>
                </div> <!-- .checkbox -->
            </div> <!-- .field -->
            <button class="ui fluid primary button" type="submit">Sign In</button>
        </form> <!-- #login-form -->
        <div class="ui bottom attached warning message">
            <i class="icon help"></i>
            Do not have an account? <a href="<c:url value="/accounts/join" />">Sign Up</a>.
        </div> <!-- .message -->
    </div> <!-- #content -->
    <!-- Footer -->
    <footer class="ui page stackable grid">
        <div id="copyright" class="column row">
            <div class="column">
                <p>Copyright&copy; <%= new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()) %> <a href="<c:url value="/" />">TestZilla</a>. All rights reserved.</p>
            </div> <!-- .column -->
        </div> <!-- #copyright -->
    </footer>
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="<c:url value="/assets/js/site.js" />"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/md5.js" />"></script>
    <script type="text/javascript">
        $(function() {
            $('.ui.checkbox').checkbox();
        });
    </script>
    <script type="text/javascript">
        function onSubmit() {
            var username   = $('#username').val(),
                password   = md5($('#password').val()),
                rememberMe = $('input#remember-me').is(':checked');

            $('#password').val(password);
            return doLoginAction(username, password, rememberMe);
        }
    </script>
    <script type="text/javascript">
        function doLoginAction(username, password, rememberMe) {
            $('#login-form').addClass('loading');
            
            var postData = {
                'username': username,
                'password': password,
                'rememberMe': rememberMe
            };
            $.ajax({
                type: 'POST',
                url: '<c:url value="/accounts/login.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result) {
                    processResult(result);
                }
            });
        }
    </script>
    <script type="text/javascript">
        function processResult(result) {
            if ( result['isSuccessful'] ) {
                window.location.href = '<c:url value="/" />';
            } else {
                $('#login-form').removeClass('loading');
                $('.positive').css('display', 'none');
                $('.error').css('display', 'block');
                $('#password').val('');
            }
        }
    </script>
</body>
</html>