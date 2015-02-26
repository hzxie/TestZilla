<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@propertyConfigurer.getProperty('cdn.url')" var="cdnUrl" />
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>About Us | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="author" content="谢浩哲">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/semantic.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/style.css" />
    <!-- JavaScript -->
    <script type="text/javascript" src="${cdnUrl}/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/semantic.min.js"></script>
</head>
<body>
    <!-- Header -->
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <!-- Content -->
    <div id="content">
        <div id="sub-header" class="ui page stackable grid segment">
            <div class="row">
                <div class="column">
                    <h1>About Us</h1>
                    <div class="ui breadcrumb">
                        <a href="<c:url value="/" />" class="section">Home</a>
                        <div class="divider"> / </div>
                        <div class="active section">About Us</div>
                    </div> <!-- .breadcrumb -->
                </div> <!-- .column -->
            </div> <!-- .row -->
        </div> <!-- #sub-header -->
        <div id="main-container" class="ui page stackable grid">
            <div class="row">
                <div class="twelve wide column">
                    <div id="main-content">
                        <h3>What's TestZilla?</h3>
                        <a class="anchor" id="what-is-testzilla"></a>
                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p>
                        <h3>How it works?</h3>
                        <a class="anchor" id="how-it-works"></a>
                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p>
                    </div> <!-- #main-content -->
                </div> <!-- .column -->
                <div class="four wide column">
                    <div class="ui sticky">
                        <div class="ui secondary vertical following fluid accordion menu">
                            <div class="item active">
                                <a class="active title"><i class="dropdown icon" tabindex="0"></i> <b>About</b></a>
                                <div class="active content menu">
                                    <a class="item" href="#what-is-testzilla">What's TestZilla?</a>
                                    <a class="item" href="#how-it-works">How it works?</a>
                                </div> <!-- .content -->
                            </div> <!-- .item -->
                        </div> <!-- .menu -->
                    </div> <!-- .sticky --> 
               </div> <!-- .column -->
            </div> <!-- .row -->
        </div> <!-- #main-container -->
    </div> <!-- #content -->
    <!-- Footer -->
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
    <script type="text/javascript">
        $('.ui.sticky').sticky({
            context: '#main-content'
        });
    </script>
</body>
</html>