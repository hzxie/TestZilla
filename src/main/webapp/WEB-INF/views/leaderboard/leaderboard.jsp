<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@propertyConfigurer.getProperty('cdn.url')" var="cdnUrl" />
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Leaderboard | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="author" content="谢浩哲">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/semantic.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/leaderboard/leaderboard.css" />
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
                        <a class="item active" href="javascript:void(0);" data-value="7">Last Week</a>
                        <a class="item" href="javascript:void(0);" data-value="30">Last Month</a>
                        <a class="item" href="javascript:void(0);" data-value="0">All Time</a>
                    </div> <!-- #time-range -->
                </div> <!-- .menu -->
            </div> <!-- .column -->
            <div class="twelve wide column">
                <div class="ui message warning hide">No records available.</div> <!-- .message -->
                <table id="leaderboard" class="ui blue celled structured table">
                    <thead>
                        <tr>
                            <th colspan="5">Leaderboard (<span class="time-range">Last Week</span>)</th>
                        </tr>
                        <tr>
                            <th>Position</th>
                            <th>Username</th>
                            <th>Country</th>
                            <th>Reputation</th>
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
    <script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
    <script type="text/javascript">
        $('#time-range > a').click(function() {
            var timeRangeName = $(this).html(),
                timeRangeValue = $(this).attr('data-value');

            $('#time-range > a.item').removeClass('active');
            $(this).addClass('active');

            $('.time-range').html(timeRangeName);
            return getReputationRanking(timeRangeValue);
        });
    </script>
    <script type="text/javascript">
        function getReputationRanking(timeRange) {
            var pageRequests = {
                'timeRange': timeRange,
            };

            $.ajax({
                type: 'GET',
                url: '<c:url value="/leaderboard/getReputationRanking.action" />',
                data: pageRequests,
                dataType: 'JSON',
                success: function(result) {
                    if ( result['isSuccessful'] ) {
                        $('div.message').addClass('hide');
                        $('table#leaderboard').removeClass('hide');

                        displayLeaderBoard(result['reputationRanking']);
                    } else {
                        $('div.message').removeClass('hide');
                        $('table#leaderboard').addClass('hide');
                    }
                }
            });
        }
    </script>
    <script type="text/javascript">
        function displayLeaderBoard(records) {
            $('#leaderboard tbody').empty();

            for ( var i = 0; i < records.length; ++ i ) {
                $('#leaderboard tbody').append(getLeaderBoardRecordContent(i + 1, records[i]['user']['username'],
                                                                           records[i]['user']['country'], records[i]['totalReputation']));
            }
        }
    </script>
    <script type="text/javascript">
        function getLeaderBoardRecordContent(position, username, country, reputation) {
            var leaderBoardRecordTemplate = '<tr>' +
                                            '    <td>%s</td>' +
                                            '    <td>%s</td>' +
                                            '    <td>%s</td>' +
                                            '    <td>%s</td>' +
                                            '</tr>';
            return leaderBoardRecordTemplate.format(position, username, country, reputation);
        }
    </script>
    <script type="text/javascript">
        $(function() {
            $('#time-range > a.item.active').click();
        });
    </script>
</body>
</html>