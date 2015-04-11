<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@propertyConfigurer.getProperty('cdn.url')" var="cdnUrl" />
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title><spring:message code="testzilla.contests.contests.contests" text="Contests" /> | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="author" content="谢浩哲">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/semantic.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/contests/contests.css" />
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
            <div class="column">
                <h1><spring:message code="testzilla.contests.contests.contests" text="Contests" /></h1>
                <div class="ui breadcrumb">
                    <a href="<c:url value="/" />" class="section"><spring:message code="testzilla.index.title" text="Home" /></a>
                    <div class="divider"> / </div>
                    <div class="active section"><spring:message code="testzilla.contests.contests.contests" text="Contests" /></div>
                </div> <!-- .breadcrumb -->
            </div> <!-- .column -->
        </div> <!-- .row -->
        <div class="row">
            <div class="four wide column">
                <div id="sidebar" class="ui vertical menu fluid">
                    <div class="item">
                        <form class="ui input" action="<c:url value="/search" />">
                            <input name="keyword" type="text" placeholder="<spring:message code="testzilla.accounts.dashboard.placeholder.search" text="Search" />..." />
                        </form> <!-- .input -->
                    </div> <!-- .item -->
                    <div id="product-categories">
                        <div class="header item">
                            <i class="list icon"></i><spring:message code="testzilla.contests.contests.contests-categories" text="Contests Categories" />
                        </div> <!-- .header -->
                        <a class="item active" href="javascript:void(0);" data-value="all"><spring:message code="testzilla.contests.contests.all" text="All" /></a>
                    </div> <!-- #product-categories -->
                </div> <!-- .menu -->
            </div> <!-- .column -->
            <div class="twelve wide column">
                <div id="contests" class="row">
                    <div class="column">
                        <div class="ui warning message">
                            <p><spring:message code="testzilla.contests.contests.no-contests-found" text="No contests found." /></p>
                        </div> <!-- .message -->
                        <div class="ui relaxed divided items">
                        </div> <!-- .items -->
                    </div> <!-- .column -->
                </div> <!-- .row -->
                <div id="pagination" class="row">
                    <div class="column">
                        <div class="ui pagination menu">
                        </div> <!-- .pagination -->
                    </div> <!-- .column -->
                </div> <!-- .row -->
            </div> <!-- .column -->
        </div> <!-- .row -->
    </div> <!-- #content -->
    <!-- Footer -->
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
</body>
</html>