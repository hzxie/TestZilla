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
                    <h1><i class="fa fa-dashboard"></i> Dashboard</h1>
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
                <a class="item" data-tab="questionnaires">Questionnaires</a>
            </div> <!-- .tabular -->
        </div> <!-- #sub-header -->
        <div id="main-container" class="ui page stackable grid">
            <div id="overview-tab" class="ui tab active" data-tab="overview">
                <div class="ui stackable grid">
                    <div class="row">
                        <div class="four wide column">
                            <div class="ui card">
                                <div class="image dimmable">
                                    <img src="<c:url value="/assets/img/default-avatar.jpg" />">
                                </div> <!-- .image -->
                                <div class="content">
                                    <div class="header">${user.username}</div>
                                    <div class="meta">
                                        <a class="group">${user.realName}</a>
                                    </div> <!-- .meta -->
                                    <div class="description">
                                        <div class="ui list">
                                            <div class="item"><i class="icon mail"></i> ${user.email}</div> <!-- .item -->
                                            <div class="item"><i class="icon marker"></i> ${user.province}, ${user.country}</div> <!-- .item -->
                                        </div> <!-- .list -->
                                    </div>
                                </div> <!-- .content -->
                                <div class="extra content">
                                    <a class="right floated created">Edit Profile</a>
                                </div> <!-- .extra -->
                            </div> <!-- .card -->
                        </div> <!-- .four -->
                        <div class="twelve wide column">
                            <div class="ui stackable grid">
                                <div class="two column row">
                                    <div class="column">
                                        <div class="ui statistic">
                                            <div class="value"><i class="trophy icon"></i> 0</div>
                                            <div class="label">Reputation</div>
                                        </div> <!-- .statistic -->
                                    </div> <!-- .column -->
                                    <div class="column">
                                        <div class="ui statistic">
                                            <div class="value"><i class="ticket icon"></i> 0</div>
                                            <div class="label">Credits</div>
                                        </div> <!-- .statistic -->
                                    </div> <!-- .column -->
                                </div> <!-- .row -->
                                <div class="ui divider"></div> <!-- .divider -->
                                <div class="row">
                                    <div class="column">
                                        <h4 class="ui header"><i class="icon feed"></i> Feed</h4>
                                        <div class="ui feed">
                                            <div class="event">
                                                <div class="label">
                                                    <img src="http://semantic-ui.com/images/avatar/small/elliot.jpg">
                                                </div> <!-- .label -->
                                                <div class="content">
                                                    <div class="summary">
                                                        <a class="user">Elliot Fu</a> added you as a friend
                                                        <div class="date">1 Hour Ago</div>
                                                    </div> <!-- .summary -->
                                                </div> <!-- .content -->
                                            </div> <!-- .event -->
                                        </div> <!-- .feed -->
                                    </div> <!-- .column -->
                                </div> <!-- .row -->
                            </div> <!-- .grid -->
                        </div> <!-- .twelve -->
                    </div> <!-- .row -->
                </div> <!-- .grid -->
            </div> <!-- #overview-tab -->
            <div id="issues-tab" class="ui tab" data-tab="issues">
                <div class="ui stackable grid">
                    <div class="row">
                        <div class="four wide column">
                            <div class="ui vertical fluid menu">
                                <a class="item active">Received Issues</a>
                                <a class="item">Sent Issues</a>
                                <div class="item">
                                    <div class="ui transparent icon input">
                                        <input type="text" placeholder="Search...">
                                        <i class="search icon"></i>
                                    </div> <!-- .input -->
                                </div> <!-- .item -->
                            </div> <!-- .menu -->
                        </div> <!-- .four -->
                        <div class="twelve wide column">
                        	<div class="ui info message">There aren't any issues.</div>
                        </div> <!-- .twelve -->
                    </div> <!-- .row -->
                </div> <!-- .grid -->
            </div> <!-- #issues-tab -->
            <div id="products-tab" class="ui tab" data-tab="products">
                <div class="ui stackable grid">
                    <div class="row">
                        <div class="four wide column">
                            <div class="ui vertical fluid menu">
                                <a class="item active">My Products</a>
                                <a class="item">New Product</a>
                                <div class="item">
                                    <div class="ui transparent icon input">
                                        <input type="text" placeholder="Search...">
                                        <i class="search icon"></i>
                                    </div> <!-- .input -->
                                </div> <!-- .item -->
                            </div> <!-- .menu -->
                        </div> <!-- .four -->
                        <div class="twelve wide column">
                            <div class="ui info message">There aren't any products.</div>
                        </div> <!-- .twelve -->
                    </div> <!-- .row -->
                </div> <!-- .grid -->
            </div> <!-- #products-tab -->
            <div id="questionnaires-tab" class="ui tab" data-tab="questionnaires">
                <div class="ui stackable grid">
                    <div class="row">
                        <div class="four wide column">
                        	<div class="ui vertical fluid menu">
                                <a class="item active">My Questionnaires</a>
                                <a class="item">New Questionnaire</a>
                                <div class="item">
                                    <div class="ui transparent icon input">
                                        <input type="text" placeholder="Search...">
                                        <i class="search icon"></i>
                                    </div> <!-- .input -->
                                </div> <!-- .item -->
                            </div> <!-- .menu -->
                        </div> <!-- .four -->
                        <div class="twelve wide column">
                        	<div class="ui info message">There aren't any questionnaires.</div>
                        </div> <!-- .twelve -->
                    </div> <!-- .row -->
                </div> <!-- .grid -->
            </div> <!-- #questionnaires-tab -->
        </div> <!-- #main-container -->
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