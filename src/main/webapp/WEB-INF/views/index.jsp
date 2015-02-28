<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@propertyConfigurer.getProperty('cdn.url')" var="cdnUrl" />
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Home | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="author" content="谢浩哲">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/semantic.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/homepage.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/slitslider.min.css" />
    <!-- JavaScript -->
    <script type="text/javascript" src="${cdnUrl}/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/semantic.min.js"></script>
</head>
<body>
    <!-- Header -->
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <!-- Content -->
    <div id="content">
        <div id="slider" class="sl-slider-wrapper">
            <div class="sl-slider">
                <div class="sl-slide bg-1" data-orientation="horizontal" data-slice1-rotation="-25" data-slice2-rotation="-25" data-slice1-scale="2" data-slice2-scale="2">
                    <div class="sl-slide-inner">
                        <div class="ui page stackable grid">
                            <div class="row">
                                <div class="eight wide column">
                                    <h1>Test software <br />better, together.</h1>
                                    <p>The most efficient crowd test platform.</p>
                                <c:choose>
                                <c:when test="${isLogin}">
                                    <button class="ui button" onclick="window.location.href='<c:url value="/products" />'">Getting Started</button>
                                </c:when>
                                <c:otherwise>
                                    <button class="ui button" onclick="window.location.href='<c:url value="/accounts/login" />'">Getting Started</button>
                                </c:otherwise>
                                </c:choose>
                                </div> <!-- .column -->
                                <div class="eight wide column">
                                    <img src="${cdnUrl}/img/homepage/crowd-test.png" alt="Icon">
                                </div> <!-- .column -->
                            </div> <!-- .row -->
                        </div> <!-- .page -->
                    </div> <!-- .sl-slide-inner -->
                </div> <!-- .sl-slide -->
                <div class="sl-slide bg-2" data-orientation="vertical" data-slice1-rotation="10" data-slice2-rotation="-15" data-slice1-scale="1.5" data-slice2-scale="1.5">
                    <div class="sl-slide-inner">
                        <div class="ui page stackable grid">
                            <div class="row">
                                <div class="eight wide column">
                                    <h1>Partner of <br />Netease MOOC.</h1>
                                    <p>朱少民: 软件测试方法和技术实践</p>
                                    <button class="ui button" onclick="window.location.href='http://mooc.study.163.com/course/Tongji-1000002019'">Tell me more</button>
                                </div> <!-- .column -->
                            </div> <!-- .row -->
                        </div> <!-- .page -->
                    </div> <!-- .sl-slide-inner -->
                </div> <!-- .sl-slide -->
                <div class="sl-slide bg-3" data-orientation="horizontal" data-slice1-rotation="-5" data-slice2-rotation="25" data-slice1-scale="2" data-slice2-scale="1">
                    <div class="sl-slide-inner">
                        <div class="ui page stackable grid">
                            <div class="row">
                                <div class="column">
                                    <h1>Introducing a flexible, <br />premium service for Enterprise.</h1>
                                    <button class="ui button">Coming Soon</button>
                                </div> <!-- .column -->
                            </div> <!-- .row -->
                        </div> <!-- .page -->
                    </div> <!-- .sl-slide-inner -->
                </div> <!-- .sl-slide -->
            </div> <!-- .sl-slider -->
            <nav class="nav-arrows">
                <span class="nav-arrow-prev">Previous</span>
                <span class="nav-arrow-next">Next</span>
            </nav>
            <nav class="nav-dots">
                <span class="nav-dot-current"></span>
                <span></span>
                <span></span>
            </nav>
        </div> <!-- #slider -->
        <div id="worldwide" class="ui page stackable grid">
            <h1>The TestZilla Testing Community</h1>
            <img src="${cdnUrl}/img/homepage/worldwide.png" alt="Worldwide" />
            <div class="row two column">
                <div class="column">
                    <div class="ui header">
                        <i class="icon world"></i>
                        <div class="content">
                            <div class="numbers">
                                <span>1</span>
                                <span>0</span>
                                <span class="text">Countries</span>
                            </div> <!-- .numbers -->
                        </div> <!-- .content -->
                    </div> <!-- .header -->
                </div> <!-- .column -->
                <div class="column">
                    <div class="ui header">
                        <i class="icon users"></i>
                        <div class="content numbers">
                            <span>1</span>
                            <span>0</span>
                            <span>2</span>
                            <span>4</span>
                            <span class="text">Testers</span>
                        </div> <!-- .content -->
                    </div> <!-- .header -->
                </div> <!-- .column -->
            </div> <!-- .row -->
        </div> <!-- #worldwide -->
        <div id="partners" class="ui page stackable grid">
        </div> <!-- #partners -->
    </div> <!-- #content -->
    <!-- Footer -->
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/jquery.slitslider.min.js"></script>
    <script type="text/javascript">
        $(function() {
            var slider = (function() {
                var $navArrows = $('.nav-arrows'),
                    $nav       = $('.nav-dots > span'),
                    slitslider = $('#slider').slitslider({
                        onBeforeChange : function(slide, pos) {
                            $nav.removeClass('nav-dot-current');
                            $nav.eq(pos).addClass('nav-dot-current');
                        }
                    }),
                    init = function() {
                        initEvents();
                    },
                    initEvents = function() {
                        // auto navigation
                        setInterval(function() {
                            slitslider.next();
                        }, 7500);
                        // add navigation events
                        $navArrows.children(':last').on('click', function() {
                            slitslider.next();
                            return false;
                        });
                        $navArrows.children(':first').on('click', function() {
                            slitslider.previous();
                            return false;
                        });
                        $nav.each(function(i) {
                            $(this).on('click', function(event) {
                                var $dot = $(this);
                                if(!slitslider.isActive()) {
                                    $nav.removeClass('nav-dot-current');
                                    $dot.addClass('nav-dot-current');
                                }
                                slitslider.jump(i + 1);
                                return false;
                            });
                        });
                    };
                    return { init : init };
            })(jQuery);
            slider.init();
        });
    </script>
</body>
</html>