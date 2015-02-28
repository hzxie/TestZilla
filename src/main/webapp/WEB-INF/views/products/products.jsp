<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@propertyConfigurer.getProperty('cdn.url')" var="cdnUrl" />
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Products | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="author" content="谢浩哲">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/semantic.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/products/products.css" />
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
                <h1>Products Board</h1>
                <div class="ui breadcrumb">
                    <a href="<c:url value="/" />" class="section">Home</a>
                    <div class="divider"> / </div>
                    <div class="active section">Product Board</div>
                </div> <!-- .breadcrumb -->
            </div> <!-- .column -->
        </div> <!-- .row -->
        <div class="row">
            <div class="four wide column">
                <div id="sidebar" class="ui vertical menu fluid">
                    <div class="item">
                        <form class="ui input" action="<c:url value="/search" />">
                            <input name="keyword" type="text" placeholder="Search..." />
                        </form> <!-- .input -->
                    </div> <!-- .item -->
                    <div id="sort-by">
                        <div class="header item">
                            <i class="sort icon"></i>Sort by
                        </div> <!-- .header -->
                        <a class="item active" href="javascript:void(0);" data-value="latest">Latest</a>
                        <a class="item" href="javascript:void(0);" data-value="most-popular">Most Popular</a>
                    </div> <!-- #sort-by -->
                    <div id="product-categories">
                        <div class="header item">
                            <i class="list icon"></i>Project Categories
                        </div> <!-- .header -->
                        <a class="item active" href="javascript:void(0);" data-value="all">All</a>
                        <c:forEach var="productCategory" items="${productCategories}">
                        <a class="item" href="javascript:void(0);" data-value="${productCategory.productCategorySlug}">${productCategory.productCategoryName}</a>
                        </c:forEach>
                    </div> <!-- #product-categories -->
                </div> <!-- .menu -->
            </div> <!-- .column -->
            <div class="twelve wide column">
                <div id="products" class="row">
                    <div class="column">
                        <div class="ui warning message hide">
                            <p>No products found.</p>
                        </div> <!-- .message -->
                        <div class="ui relaxed divided items">
                        </div> <!-- .items -->
                    </div> <!-- .column -->
                </div> <!-- .row -->
                <div id="pagination" class="row">
                    <div class="column">
                        <div class="ui pagination menu">
                        </div> <!-- .pagination -->
                    </div> <!-- .column -->
                </div> <!-- .row -->
            </div> <!-- .column -->
        </div> <!-- .row -->
    </div> <!-- #content -->
    <!-- Footer -->
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            var pageNumber = 1;
            return getPageRequests(pageNumber);
        });

        $('#sort-by > a').click(function() {
            $('#sort-by > a.item').removeClass('active');
            $(this).addClass('active');

            var pageNumber = 1;
            return getPageRequests(pageNumber);
        });

        $('#product-categories > a').click(function() {
            $('#product-categories > a.item').removeClass('active');
            $(this).addClass('active');

            var pageNumber = 1;
            return getPageRequests(pageNumber);
        });

        $('#pagination').delegate('a.item', 'click', function(e) {
            e.preventDefault();
            if ( $(this).hasClass('disabled') ) {
                return;
            }
            var currentPage = parseInt($('a.active', '.pagination').html(), 10);
                pageNumber  = $(this).html();
            
            $('.pagination > a.active').removeClass('active');
            $(this).addClass('active');

            if ( pageNumber.indexOf('left arrow') != -1 ) {
                pageNumber  = currentPage - 1;
            } else if ( pageNumber.indexOf('right arrow') != -1 ) {
                pageNumber  = currentPage + 1;
            }
            return getPageRequests(pageNumber);
        });
    </script>
    <script type="text/javascript">
        function getPageRequests(pageNumber) {
            var sortBy     = $('#sort-by > a.active').attr('data-value'),
                category   = $('#product-categories > a.active').attr('data-value');

            return getProducts(sortBy, category, pageNumber);
        }
    </script>
    <script type="text/javascript">
        function getProducts(sortBy, category, pageNumber) {
            var pageRequests = {
                'sortBy': sortBy,
                'category': category,
                'page': pageNumber
            };

            $.ajax({
                type: 'GET',
                url: '<c:url value="/products/getProducts.action" />',
                data: pageRequests,
                dataType: 'JSON',
                success: function(result) {
                    if ( result['isSuccessful'] ) {
                        $('#products .items').removeClass('hide');
                        $('#pagination').removeClass('hide');
                        $('.warning').addClass('hide');

                        displayProducts(result['products']);
                        displayPagination(pageNumber, result['totalPages']);
                    } else {
                        $('#products .items').addClass('hide');
                        $('#pagination').addClass('hide');
                        $('.warning').removeClass('hide');
                    }
                }
            });
        }
    </script>
    <script type="text/javascript">
        function displayProducts(products) {
            $('#products .items').empty();

            for ( var i = 0; i < products.length; ++ i ) {
                $('#products .items').append(getProductContent(products[i]['productId'], products[i]['productName'], 
                                                               products[i]['productLogo'], products[i]['productCategory'], 
                                                               products[i]['latestVersion'], products[i]['description'], products[i]['numberOfIssues']));
            }
        }
    </script>
    <script type="text/javascript">
        function getProductContent(productId, productName, productLogo, productCategory, latestVersion, description, numberOfIssues) {
            var productContentTemplate = '<div class="item">' + 
                                         '    <div class="ui small image">' + 
                                         '        <img src="%s" alt="Product Logo" />' + 
                                         '    </div> <!-- .image -->' + 
                                         '    <div class="content">' + 
                                         '        <a class="header" href="<c:url value="/products/" />%s">%s</a>' + 
                                         '        <div class="meta"><a>%s</a></div> <!-- .meta -->' + 
                                         '        <div class="description">%s</div> <!-- .description -->' + 
                                         '        <div class="extra">' + 
                                         '            <div class="ui label">%s</div> <!-- .label -->' + 
                                         '            <div class="ui label">%s issue(s) reported</div> <!-- .label -->' + 
                                         '        </div> <!-- .extra -->' + 
                                         '    </div> <!-- .content -->' + 
                                         '</div> <!-- .item -->';
            return productContentTemplate.format(productLogo, productId, productName, 
                                                 productCategory['productCategoryName'], 
                                                 description, latestVersion, numberOfIssues);
        }
    </script>
    <script type="text/javascript">
        function displayPagination(currentPage, totalPages) {
            var lowerBound = ( currentPage - 5 > 0 ? currentPage - 5 : 1 ),
                upperBound = ( currentPage + 5 < totalPages ? currentPage + 5 : totalPages );
            var paginationString  = '<a class="icon item' + ( currentPage > 1 ? '' : ' disabled' ) + '"><i class="left arrow icon"></i></a>';

            for ( var i = lowerBound; i <= upperBound; ++ i ) {
                paginationString += '<a class="item' + ( currentPage == i ? ' active' : '' ) + '">' + i + '</a>';
            }
            paginationString     += '<a class="icon item' + ( currentPage < totalPages ? '' : ' disabled' )+ '"><i class="right arrow icon"></i></a>';
            $('.pagination').html(paginationString);
        }
    </script>
</body>
</html>