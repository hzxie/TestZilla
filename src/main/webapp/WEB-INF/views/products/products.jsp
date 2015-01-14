<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Products | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/semantic.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/font-awesome.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/style.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/products/products.css" />" />
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
                <h1>Products Board</h1>
                <div class="ui breadcrumb">
                    <a href="<c:url value="/" />" class="section">Home</a>
                    <div class="divider"> / </div>
                    <div class="active section">Product Board</div>
                </div>
            </div> <!-- .column -->
        </div> <!-- .row -->
        <div class="row">
            <div class="four wide column">
                <div id="sidebar" class="ui vertical menu fluid">
                    <div class="item">
                        <div class="ui input">
                            <input type="text" placeholder="Search...">
                        </div> <!-- .input -->
                    </div> <!-- .item -->
                    <div class="header item">
                        <i class="sort icon"></i>Sort by
                    </div> <!-- .header -->
                    <a class="active item">Latest</a>
                    <a class="item">Most Popular</a>
                    <div class="header item">
                        <i class="list icon"></i>Project Categories
                    </div> <!-- .header -->
                    <a class="active item">All</a>
                    <a class="item">Windows Application</a>
                    <a class="item">Mac Application</a>
                    <a class="item">Web Application</a>
                    <a class="item">iOS Application</a>
                    <a class="item">Android Application</a>
                    <a class="item">WindowsPhone Application</a>
                    <a class="item">Others</a>
                </div> <!-- .menu -->
            </div> <!-- .column -->
            <div class="twelve wide column">
                <div id="products" class="row">
                    <div class="column">
                        <div class="ui relaxed divided items">
                            <div class="item">
                                <div class="ui small image">
                                    <img src="/images/wireframe/image.png">
                                </div> <!-- .image -->
                                <div class="content">
                                    <a class="header">Content Header</a>
                                    <div class="meta">
                                        <a>Date</a>
                                        <a>Category</a>
                                    </div> <!-- .meta -->
                                    <div class="description">
                                        A description which may flow for several lines and give context to the content.
                                    </div> <!-- .description -->
                                    <div class="extra">
                                        <img src="/images/wireframe/square-image.png" class="ui circular avatar"> Username
                                    </div> <!-- .extra -->
                                </div> <!-- .content -->
                            </div> <!-- .item -->
                            <div class="item">
                                <div class="ui small image">
                                    <img src="/images/wireframe/image.png">
                                </div> <!-- .image -->
                                <div class="content">
                                    <a class="header">Content Header</a>
                                    <div class="meta">
                                        <a>Date</a>
                                        <a>Category</a>
                                    </div> <!-- .meta -->
                                    <div class="description">
                                        A description which may flow for several lines and give context to the content.
                                    </div> <!-- .description -->
                                    <div class="extra">
                                        <div class="ui right floated primary button">
                                            Primary<i class="right chevron icon"></i>
                                        </div> <!-- .button -->
                                        <div class="ui label">Limited</div> <!-- .label -->
                                    </div> <!-- .extra -->
                                </div> <!-- .content -->
                            </div> <!-- .item -->
                            <div class="item">
                                <div class="ui small image">
                                    <img src="/images/wireframe/image.png">
                                </div> <!-- .image -->
                                <div class="content">
                                    <a class="header">Content Header</a>
                                    <div class="meta">
                                        <a>Date</a>
                                        <a>Category</a>
                                    </div> <!-- .meta -->
                                    <div class="description">
                                        A description which may flow for several lines and give context to the content.
                                    </div> <!-- .description -->
                                    <div class="extra">
                                        <div class="ui right floated primary button">
                                            Primary<i class="right chevron icon"></i>
                                        </div> <!-- .button -->
                                    </div> <!-- .extra -->
                                </div> <!-- .content -->
                            </div> <!-- .item -->
                        </div> <!-- .items -->
                    </div> <!-- .column -->
                </div> <!-- .row -->
                <div id="pagination" class="row">
                    <div class="column">
                        <div class="ui pagination menu">
                            <a class="icon item"><i class="left arrow icon"></i></a>
                            <a class="active item">1</a>
                            <div class="disabled item">...</div>
                            <a class="item">10</a>
                            <a class="item">11</a>
                            <a class="item">12</a>
                            <a class="icon item"><i class="right arrow icon"></i></a>
                        </div> <!-- .pagination -->
                    </div> <!-- .column -->
                </div> <!-- .row -->
            </div> <!-- .column -->
        </div>
    </div> <!-- #content -->
    <!-- Footer -->
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="<c:url value="/assets/js/site.js" />"></script>
</body>
</html>