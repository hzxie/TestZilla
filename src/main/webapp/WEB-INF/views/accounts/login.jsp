<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@propertyConfigurer.getProperty('cdn.url')" var="cdnUrl" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="date" class="java.util.Date" />
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title><spring:message code="testzilla.accounts.login.title" text="Sign In" /> | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="author" content="谢浩哲">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/semantic.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/accounts/login.css" />
    <!-- JavaScript -->
    <script type="text/javascript" src="${cdnUrl}/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/semantic.min.js"></script>
</head>
<body>
    <!-- Header -->
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <!-- Content -->
    <div id="content">
        <h2><spring:message code="testzilla.accounts.login.title" text="Sign In" /></h2>
        <div class="ui error message hide">
            <div class="header"><spring:message code="testzilla.accounts.login.forbidden" text="Action Forbidden" /></div>
            <p><spring:message code="testzilla.accounts.login.message" text="Invalid username or password." /></p>
        </div> <!-- .error -->
        <c:if test="${isLogout}">
        <div class="ui positive message">
            <p><spring:message code="testzilla.accounts.login.logout" text="You're now logged out." /></p>
        </div> <!-- .positive -->    
        </c:if>
        <form id="login-form" class="ui form attached fluid segment" onSubmit="onSubmit(); return false;">
            <div class="field">
                <label><spring:message code="testzilla.accounts.login.username" text="Username/Email" /></label>
                <div class="ui icon input">
                    <input id="username" type="text" placeholder="<spring:message code="testzilla.accounts.login.username" text="Username/Email" />" />
                    <i class="icon user"></i>
                </div> <!-- .input -->
            </div> <!-- .field -->
            <div class="field">
                <label><spring:message code="testzilla.accounts.login.password" text="Password" /><a href="<c:url value="/accounts/resetPassword" />"><spring:message code="testzilla.accounts.login.forgot-password" text="Forgot Password" /></a></label>
                <div class="ui icon input">
                    <input id="password" type="password" placeholder="<spring:message code="testzilla.accounts.login.password" text="Password" />" />
                    <i class="icon lock"></i>                
                </div> <!-- .input -->
            </div> <!-- .field -->
            <div class="inline field">
                <div class="ui checkbox">
                    <input id="remember-me" type="checkbox" />
                    <label><spring:message code="testzilla.accounts.login.remember-me" text="Remember Me" /></label>
                </div> <!-- .checkbox -->
            </div> <!-- .field -->
            <button class="ui fluid primary button" type="submit"><spring:message code="testzilla.accounts.login.sign-in" text="Sign In" /></button>
        </form> <!-- #login-form -->
        <div class="ui bottom attached warning message">
            <i class="icon help"></i>
            <spring:message code="testzilla.accounts.login.have-not-account" text="Do not have an account?" /> <a href="<c:url value="/accounts/join" />"><spring:message code="testzilla.accounts.login.sign-up" text="Sign Up" /></a>.
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
    <script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/md5.js"></script>
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
                window.location.href = '<c:url value="/" />${param.forward}';
            } else {
                $('#login-form').removeClass('loading');
                $('.positive').addClass('hide');
                $('.error').removeClass('hide');
                $('#password').val('');
            }
        }
    </script>
</body>
</html>