<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Verify Your Email | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/semantic.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/font-awesome.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/style.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/accounts/join.css" />" />
    <!-- JavaScript -->
    <script type="text/javascript" src="<c:url value="/assets/js/jquery-1.11.1.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/semantic.min.js" />"></script>
</head>
<body>
    <!-- Header -->
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <!-- Content -->
    <div id="content" class="ui page stackable grid">
        <div class="row">
            <div id="slogan" class="column">
                <h1>Join TestZilla</h1>
                <h2>The best way to test your software.</h2>
            </div> <!-- .column -->
        </div> <!-- .row -->
        <div class="row">
            <div id="steps" class="column">
                <div class="ui steps fluid">
                    <a class="step">
                        <i class="icon user"></i>
                        <div class="content">
                            <div class="title">Step 1</div> <!-- .title -->
                            <div class="description">Create your account</div> <!-- .description -->
                        </div> <!-- .content -->
                    </a>
                    <a class="active step">
                        <i class="icon mail"></i>
                        <div class="content">
                            <div class="title">Step 2</div> <!-- .title -->
                            <div class="description">Verify your email</div> <!-- .description -->
                        </div> <!-- .content -->
                    </a>
                    <a class="step">
                        <i class="icon dashboard"></i>
                        <div class="content">
                            <div class="title">Step 3</div> <!-- .title -->
                            <div class="description">Go to your dashboard</div> <!-- .description -->
                        </div> <!-- .content -->
                    </a>
                </div> <!-- .steps -->
            </div> <!-- #steps -->
        </div> <!-- .row -->
        <div class="row">
        <c:choose>
        <c:when test="${code == null}">
            Code is null
        </c:when>
        <c:otherwise>
            Code isn't null
        </c:otherwise>
        </c:choose>
        </div> <!-- .row  -->
    </div> <!-- #content -->
    <!-- Footer -->
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="<c:url value="/assets/js/site.js" />"></script>
</body>
</html>