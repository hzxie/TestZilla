<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="date" class="java.util.Date" />
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
        <div id="copyright" class="row">
            <div class="twelve wide column">
                <p>Copyright&copy; <%= new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()) %> <a href="<c:url value="/" />">TestZilla</a>. All rights reserved.</p>
            </div> <!-- .column -->
            <div class="four wide column">
            </div> <!-- .column -->
        </div> <!-- #copyright -->
    </footer>
    <!-- Google Analytics Code -->
    <script type="text/javascript">
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
        ga('create', 'UA-56635442-3', 'auto');
        ga('send', 'pageview');
    </script>