<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@propertyConfigurer.getProperty('cdn.url')" var="cdnUrl" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="date" class="java.util.Date" />
    <footer class="ui page stackable grid">
        <div class="three column row">
            <div class="column">
                <h4><spring:message code="testzilla.include.footer.overview" text="Overview" /></h4>
                <div class="ui list">
                    <a href="<c:url value="/about" />" class="item"><spring:message code="testzilla.include.footer.about-us" text="About Us" /></a>
                    <a href="<c:url value="/terms" />" class="item"><spring:message code="testzilla.include.footer.terms-and-privacy" text="Terms and Privacy" /></a>
                </div> <!-- .list -->
            </div> <!-- .column -->
            <div class="column">
                <h4><spring:message code="testzilla.include.footer.contact" text="Contact" /></h4>
                <div class="ui list">
                    <div class="item">
                        <i class="fa fa-envelope"></i> support@testzilla.org
                    </div> <!-- .item -->
                </div> <!-- .list -->
            </div> <!-- .column -->
            <div class="column">
                <h4><spring:message code="testzilla.include.footer.stay-conntected" text="Stay Connected" /></h4>
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
            <div class="four wide column">
            	<a href="https://www.upyun.com/" target="_blank">
            		<img src="${cdnUrl}/img/upyun.png" alt="UPYUN">
            	</a>
            </div> <!-- .column -->
            <div class="twelve wide right aligned column">
                <p>Copyright&copy; <%= new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()) %> <a href="<c:url value="/" />">TestZilla</a>. All rights reserved.</p>
                <p>Developed and designed by <a href="http://zjhzxhz.com" target="_blank">Trunk Shell</a>. </p>
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