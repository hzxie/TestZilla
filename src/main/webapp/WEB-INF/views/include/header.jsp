<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@propertyConfigurer.getProperty('cdn.url')" var="cdnUrl" />
    <header class="ui page stackable grid">
        <div id="assistive-menu" class="row">
            <div class="column">
                <div class="ui secondary menu right floated">
                <c:choose>
                <c:when test="${isLogin}">
                    <a class="item" href="<c:url value="/accounts/dashboard" />"><i class="fa fa-user"></i> ${user.username}</a>
                    <a class="item" href="<c:url value="/accounts/login?logout=true" />"><spring:message code="testzilla.include.header.logout" text="Logout" /></a>
                </c:when>
                <c:otherwise>
                    <a class="item" href="<c:url value="/accounts/login" />"><spring:message code="testzilla.include.header.sign-in" text="Sign In" /></a>
                    <a class="item" href="<c:url value="/accounts/join" />"><spring:message code="testzilla.include.header.sign-up" text="Sign Up" /></a>
                </c:otherwise>
                </c:choose>
                    <a class="item" href="<c:url value="/help" />"><spring:message code="testzilla.include.header.help-center" text="Help Center" /></a>
                    <a class="item" href="<c:url value="/search" />"><i class="fa fa-search"></i> <spring:message code="testzilla.include.header.search" text="Search" /></a>
                    <div id="languages" class="ui language floating dropdown link item">
                        <i class="world icon"></i>
                        <div class="text">${language}</div> <!-- .text -->
                        <div class="menu">
                            <div class="item active selected" data-value="en_US">English</div>
                            <div class="item" data-value="zh_CN">简体中文</div>
                        </div> <!-- .menu -->
                    </div> <!-- .dropdown -->
                </div> <!-- .menu -->
            </div> <!-- .column -->
        </div> <!-- .row -->
        <div class="row">
            <div id="logo" class="four wide column">
                <a href="<c:url value="/" />">
                    <img src="${cdnUrl}/img/logo.png" alt="Logo">
                </a>
            </div> <!-- #logo -->
            <div id="navigation" class="twelve wide column">
                <nav class="ui secondary menu right floated">
                    <a class="item" href="<c:url value="/" />"><spring:message code="testzilla.include.header.home" text="Home" /></a>
                    <a class="item" href="<c:url value="/products" />"><spring:message code="testzilla.include.header.products-board" text="Products Board" /></a>
                    <a class="item" href="<c:url value="/contests" />"><spring:message code="testzilla.include.header.contests" text="Contests" /></a>
                    <a class="item" href="<c:url value="/leaderboard" />"><spring:message code="testzilla.include.header.leaderboard" text="Leaderboard" /></a>
                </nav>
            </div> <!-- #navigation -->
        </div> <!-- .row -->
    </header>
    <!-- JavaScript for Localization -->
    <script type="text/javascript">
        $('#languages').dropdown({
            onChange: function(value) {
            	var postData = {
                    language: value
                };

                $.ajax({
                    type: 'GET',
                    url: '<c:url value="/localization" />',
                    data: postData,
                    dataType: 'JSON',
                    success: function(result) {
                        if ( result['isSuccessful'] ) {
                            location.reload();
                        }
                    }
                });
            }
        });
    </script>