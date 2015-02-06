<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Leaderboard | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/semantic.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/font-awesome.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/style.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/leaderboard/leaderboard.css" />" />
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
            <div class="column">
                <h1>Leaderboard</h1>
                <div class="ui breadcrumb">
                    <a href="<c:url value="/" />" class="section">Home</a>
                    <div class="divider"> / </div>
                    <div class="active section">Leaderboard</div>
                </div>
            </div> <!-- .column -->
        </div> <!-- .row -->
        <div class="row">
            <div class="four wide column">
                <div id="sidebar" class="ui vertical menu fluid">
                    <div id="time-range">
                        <div class="header item">
                            <i class="icon wait"></i>Time Range
                        </div> <!-- .header -->
                        <a class="item active" href="javascript:void(0);" data-value="">Last Week</a>
                        <a class="item" href="javascript:void(0);" data-value="">Last Month</a>
                        <a class="item" href="javascript:void(0);" data-value="">All Time</a>
                    </div> <!-- #time-range -->
                </div> <!-- .menu -->
            </div> <!-- .column -->
            <div class="twelve wide column">
                <table id="leaderboard" class="ui blue celled structured table">
                    <thead>
                        <tr>
                            <th colspan="5">Leaderboard (<span class="time-range">Last Week</span>)</th>
                        </tr>
                        <tr>
                            <th rowspan="2">Position</th>
                            <th rowspan="2">Username</th>
                            <th rowspan="2">Country</th>
                            <th colspan="2">Reputation</th>
                        </tr>
                        <tr>
                            <th class="time-range">Last Week</th>
                            <th>All Time</th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div> <!-- .column -->
        </div> <!-- .row -->
    </div> <!-- #content -->
    <!-- Footer -->
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="<c:url value="/assets/js/site.js" />"></script>
    <script type="text/javascript">
        $(document).ready(function() {
        });

        $('#time-range > a').click(function() {
            var timeRangeName = $(this).html(),
                timeRangeValue = $(this).attr('data-value');

            $('#time-range > a.item').removeClass('active');
            $(this).addClass('active');
            $('.time-range').html(timeRangeName);
        });
    </script>
</body>
</html>