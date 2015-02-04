<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Dashboard | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/semantic.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/font-awesome.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/style.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/accounts/dashboard.css" />" />
    <!-- JavaScript -->
    <script type="text/javascript" src="<c:url value="/assets/js/jquery-1.11.1.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/semantic.min.js" />"></script>
</head>
<body>
    <!-- Header -->
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <!-- Content -->
    <div id="content">
        <div id="sub-header" class="ui page stackable grid segment">
            <div class="row">
                <div class="column">
                    <h1>Dashboard</h1>
                    <div class="ui breadcrumb">
                        <a href="<c:url value="/" />" class="section">Home</a>
                        <div class="divider"> / </div>
                        <div class="active section">Dashboard</div>
                    </div> <!-- .breadcrumb -->
                </div> <!-- .column -->
            </div> <!-- .row -->
            <div class="four item tabular ui menu">
                <a class="item active" data-tab="overview">Overview</a>
                <a class="item" data-tab="issues">Issues</a>
                <a class="item" data-tab="products">Products</a>
                <a class="item" data-tab="questionaires">Questionaires</a>
            </div> <!-- .tabular -->
        </div> <!-- #sub-header -->
        <div id="main-content" class="ui page stackable grid">
            <div class="ui tab active" data-tab="overview">
                <div class="ui grid">
                    <div class="row">
                        <div class="four wide column">
                        </div> <!-- .four -->
                        <div class="twelve wide column">
                        </div> <!-- .twelve -->
                    </div> <!-- .row -->
                </div> <!-- .grid -->
            </div> <!-- .tab -->
            <div class="ui tab" data-tab="issues">
                <div class="ui grid">
                    <div class="row">
                        <div class="four wide column">
                        </div> <!-- .four -->
                        <div class="twelve wide column">
                        </div> <!-- .twelve -->
                    </div> <!-- .row -->
                </div> <!-- .grid -->
            </div> <!-- .tab -->
            <div class="ui tab" data-tab="products">
                <div class="ui grid">
                    <div class="row">
                        <div class="four wide column">
                        </div> <!-- .four -->
                        <div class="twelve wide column">
                        </div> <!-- .twelve -->
                    </div> <!-- .row -->
                </div> <!-- .grid -->
            </div> <!-- .tab -->
            <div class="ui tab" data-tab="questionaires">
                <div class="ui grid">
                    <div class="row">
                        <div class="four wide column">
                        </div> <!-- .four -->
                        <div class="twelve wide column">
                        </div> <!-- .twelve -->
                    </div> <!-- .row -->
                </div> <!-- .grid -->
            </div> <!-- .tab -->
        </div> <!-- #main-content -->
    </div> <!-- #content -->
    <!-- Footer -->
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="<c:url value="/assets/js/site.js" />"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/moment.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/markdown.editor.min.js" />"></script>
    <script type="text/javascript">
        $(function() {
            $('.tabular.menu .item').tab();
        });
    </script>
</body>
</html>