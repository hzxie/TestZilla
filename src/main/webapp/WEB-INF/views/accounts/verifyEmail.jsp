<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@propertyConfigurer.getProperty('cdn.url')" var="cdnUrl" />
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title><spring:message code="testzilla.accounts.verifyEmail.verify-your-name" text="Verify Your Email" /> | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="author" content="谢浩哲">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/semantic.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/accounts/join.css" />
    <!-- JavaScript -->
    <script type="text/javascript" src="${cdnUrl}/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/semantic.min.js"></script>
</head>
<body>
    <!-- Header -->
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <!-- Content -->
    <div id="content" class="ui page stackable grid">
        <div class="row">
            <div id="slogan" class="column">
                <h1><spring:message code="testzilla.accounts.verifyEmail.join" text="Join" /> TestZilla</h1>
                <h2><spring:message code="testzilla.accounts.verifyEmail.slogan" text="The best way to test your software." /></h2>
            </div> <!-- .column -->
        </div> <!-- .row -->
        <div class="row">
            <div id="steps" class="column">
                <div class="ui steps fluid">
                    <a class="step">
                        <i class="icon user"></i>
                        <div class="content">
                            <div class="title"><spring:message code="testzilla.accounts.step-1" text="Step 1" /></div> <!-- .title -->
                            <div class="description"><spring:message code="testzilla.accounts.create-your-account" text="Create your account" /></div> <!-- .description -->
                        </div> <!-- .content -->
                    </a>
                    <a class="active step">
                        <i class="icon mail"></i>
                        <div class="content">
                            <div class="title"><spring:message code="testzilla.accounts.step-2" text="Step 2" /></div> <!-- .title -->
                            <div class="description"><spring:message code="testzilla.accounts.verify-your-email" text="Verify your email" /></div> <!-- .description -->
                        </div> <!-- .content -->
                    </a>
                    <a class="step">
                        <i class="icon dashboard"></i>
                        <div class="content">
                            <div class="title"><spring:message code="testzilla.accounts.step-3" text="Step 3" /></div> <!-- .title -->
                            <div class="description"><spring:message code="testzilla.accounts.go-to-your-dashboard" text="Go to your dashboard" /></div> <!-- .description -->
                        </div> <!-- .content -->
                    </a>
                </div> <!-- .steps -->
            </div> <!-- #steps -->
        </div> <!-- .row -->
        <div class="row">
        	<div id="email-sent" class="column">
                <div class="ui attached segment fluid">
                    <a class="ui ribbon label"><spring:message code="testzilla.accounts.verifyEmail.email-has-been-sent" text="Email has been sent." /></a>
                    <p>
                        <spring:message code="testzilla.accounts.verifyEmail.activation-email-sent-to" text="An activation email has been sent to " /><span id="email">${email}</span>.<br />
                        <spring:message code="testzilla.accounts.verifyEmail.verification-reward-hint" text="After verified your email, you'll get 100 credits." /><br />
                        <button class="ui primary button"><spring:message code="testzilla.accounts.verifyEmail.go-to-mailbox" text="Go to MailBox" /></button>
                    </p>
                </div> <!-- .segment -->

            </div> <!-- .column -->
        </div> <!-- .row  -->
    </div> <!-- #content -->
    <!-- Footer -->
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
    <script type="text/javascript">
        $('#email-sent button.primary').click(function() {
            var email       = $('#email').html(),
                domain      = email.split('@')[1],
                redirectUrl = '';

            if ( domain == 'gmail.com' ) {
                redirectUrl = '//mail.google.com';
            } else {
                redirectUrl = '//mail.' + domain;
            }
            window.location.href = redirectUrl;
        });
    </script>
</body>
</html>