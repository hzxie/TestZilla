<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Home | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Icon -->
    <link href="<c:url value="/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/semantic.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/font-awesome.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/style.css" />" />
    <!-- JavaScript -->
    <script type="text/javascript" src="<c:url value="/assets/js/jquery-1.11.1.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/semantic.min.js" />"></script>
</head>
<body>
    <header class="ui page stackable grid">
        <div id="assistive-menu" class="row">
            <div class="column">
                <div class="ui secondary menu right floated">
                <c:choose>
                <c:when test="${isLogin}">
                    <a class="item" href="<c:url value="/accounts/dashboard" />">Dashboard</a>
                </c:when>
                <c:otherwise>
                    <a class="item" href="<c:url value="/accounts/login" />">Sign In</a>
                    <a class="item" href="<c:url value="/accounts/join" />">Sign Up</a>
                </c:otherwise>
                </c:choose>
                    <a class="item" href="<c:url value="/help" />">Help Center</a>
                    <a class="item" href="<c:url value="/search" />"><i class="fa fa-search"></i> Search</a>
                    <div class="ui language floating dropdown link item" id="languages">
                        <i class="world icon"></i>
                        <div class="text">English</div> <!-- .text -->
                        <div class="menu">
                            <div class="item active selected" data-value="en-US" data-english="English">English</div>
                            <div class="item" data-value="zh-CN" data-english="Chinese">简体中文</div>
                        </div> <!-- .menu -->
                    </div> <!-- .dropdown -->
                </div> <!-- .menu -->
            </div> <!-- .column -->
        </div> <!-- .row -->
        <div class="row">
            <div id="logo" class="four wide column">
                <a href="<c:url value="/" />">
                    <img src="<c:url value="/assets/img/logo.png" />" alt="Logo">
                </a>
            </div> <!-- #logo -->
            <div id="navigation" class="twelve wide column">
                <nav class="ui secondary menu right floated">
                    <a class="item" href="<c:url value="/" />">Home</a>
                    <a class="item" href="<c:url value="/products" />">Products Board</a>
                    <a class="item" href="<c:url value="/competition" />">Competition</a>
                    <a class="item" href="<c:url value="/leaderboard" />">Leaderboard</a>
                </nav>
            </div> <!-- #navigation -->
        </div> <!-- .row -->
    </header>
    <div id="content" class="ui page stackable grid">
    </div> <!-- #content -->
    <footer class="ui page stackable grid">
        <div class="three column row">
            <div class="column">
                <h4>Overview</h4>
                <div class="ui list">
                    <a href="<c:url value="/about" />" class="item">About Us</a>
                    <a href="<c:url value="/terms" />" class="item">Terms and Privacy</a>
                </div> <!-- .list -->
            </div> <!-- .column -->
            <div class="column">
                <h4>Contact</h4>
                <div class="ui list">
                    <div class="item">
                        <i class="fa fa-phone"></i> +86-21-1234 5678
                    </div> <!-- .item -->
                    <div class="item">
                        <i class="fa fa-envelope"></i> support@testzilla.org
                    </div> <!-- .item -->
                </div> <!-- .list -->
            </div> <!-- .column -->
            <div class="column">
                <h4>Stay Connected</h4>
                <div id="social-links" class="ui horizontal list">
                    <a class="item" href="<c:url value="#" />" title="Facebook"><i class="fa fa-facebook"></i></a>
                    <a class="item" href="<c:url value="#" />" title="Twitter"><i class="fa fa-twitter"></i></a>
                    <a class="item" href="<c:url value="#" />" title="LinkedIn"><i class="fa fa-linkedin"></i></a>
                    <a class="item" href="<c:url value="#" />" title="Weibo"><i class="fa fa-weibo"></i></a>
                    <a class="item" href="<c:url value="#" />" title="WeChat"><i class="fa fa-weixin"></i></a>
                </div> <!-- .list -->
            </div> <!-- .column -->
        </div> <!-- .row -->
        <div id="copyright" class="column row">
            <div class="column">
                <p>Copyright&copy; 2015 <a href="<c:url value="/" />">TestZilla</a>. All rights reserved.</p>
            </div> <!-- .column -->
        </div> <!-- #copyright -->
    </footer>
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript">
        $(function() {
            $('.ui.dropdown').dropdown();
        });
    </script>
</body>
</html>