<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>${product.productName} | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/semantic.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/font-awesome.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/style.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/products/product.css" />" />
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
                <h1>${product.productName}</h1>
                <div class="ui breadcrumb">
                    <a href="<c:url value="/" />" class="section">Home</a>
                    <div class="divider"> / </div>
                    <a href="<c:url value="/products" />" class="section">Product Board</a>
                    <div class="divider"> / </div>
                    <div class="active section">${product.productName}</div>
                </div>
            </div> <!-- .column -->
        </div> <!-- .row -->
        <div class="row">
            <div id="product" class="twelve wide column">
                <div class="ui segment">
                    <a class="ui ribbon label"><i class="icon content"></i> Product Detail</a>
                    <table class="ui table">
                        <tbody>
                            <tr>
                                <td>Product Category</td>
                                <td>${product.productCategory.productCategoryName}</td>
                            </tr>
                            <tr>
                                <td>Latest Version</td>
                                <td>${product.latestVersion}</td>
                            </tr>
                            <tr>
                                <td>URL</td>
                                <td><a href="${product.url}" target="_blank">${product.url}</a></td>
                            </tr>
                            <tr>
                                <td>Prerequisites</td>
                                <td>${product.prerequisites}</td>
                            </tr>
                            <tr>
                                <td>Description</td>
                                <td>${product.description}</td>
                            </tr>
                        </tbody>
                    </table>
                </div> <!-- .segment -->
                <c:choose>
                <c:when test="${isLogin}">
                <div class="ui segment">
                    <a class="ui ribbon label"><i class="icon bug"></i> Issues</a>
                    <button id="new-issue-button" class="ui button positive">New Issue</button>
                    <div id="issue-message" class="ui info message">There aren't any issues.</div> <!-- #issue-message -->
                    <div id="bugs" class="ui styled accordion fluid"></div> <!-- #bugs -->
                    <div class="ui pagination menu"></div> <!-- .pagination -->
                </div> <!-- .segment -->
                <div class="ui segment">
                    <a class="ui ribbon label"><i class="icon book"></i> Questionnaire</a>
                    <div id="questionnaire-message" class="ui info message">There aren't any questionnaires.</div> <!-- #questionnaire-message -->
                </div> <!-- .segment -->
                </c:when>
                <c:otherwise>
                <div class="ui icon info message">
                    <i class="inbox icon"></i>
                    <div class="content">
                        <div class="header">
                        Want to help improve this product?
                        </div> <!-- header -->
                        <p>After <a href="<c:url value="/accounts/login?forward=products/${product.productId}" />">sign in</a>, you can report bugs of this product.</p>
                    </div> <!-- .content -->
                </div> <!-- .message -->
                </c:otherwise>
                </c:choose>
            </div> <!-- .column -->
            <div id="related-products" class="four wide column">
                <h3>Related Products</h3>
                <div class="ui divided items">
                <c:forEach var="relatedProduct" items="${relatedProducts}">
                    <c:if test="${relatedProduct.productId ne product.productId}">
                    <div class="item">
                        <div class="ui tiny image">
                            <img src="${relatedProduct.productLogo}">
                        </div> <!-- image -->
                        <div class="middle aligned content">
                            <a class="header" href="<c:url value="/products/${relatedProduct.productId}" />">${relatedProduct.productName}</a>
                            <div class="meta">
                                <span>${relatedProduct.numberOfTesters} tester(s) attended</span>
                            </div>
                        </div> <!-- .content -->
                    </div> <!-- .item -->
                    </c:if>
                </c:forEach>
                </div> <!-- .items -->
            </div> <!-- .column -->
        </div> <!-- .row -->
    </div> <!-- #content -->
    <!-- Footer -->
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
    <!-- Modals -->
    <div class="ui dimmer modals page transition hidden">
        <div id="bug-modal" class="ui long test modal transition scrolling hidden">
            <i class="close icon"></i>
            <div class="header">
                <h3>New Issue</h3>
            </div> <!-- .header -->
            <div class="content">
                <div id="create-bug-error" class="ui error message hide"></div> <!-- #create-bug-error -->
                <div class="ui form">
                    <div class="two required fields">
                        <div class="field">
                            <label for="title">Title</label>
                            <input id="title" type="text" />
                        </div> <!-- .field -->
                        <div class="field">
                            <label for="version">Product Version</label>
                            <input id="version" type="text" value="${product.latestVersion}" />
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two required fields">
                        <div class="field">
                            <label>Category</label>
                            <div class="ui selection dropdown" tabindex="0">
                                <div class="default text">Please choose...</div>
                                <i class="dropdown icon"></i>
                                <input id="bug-category" type="hidden">
                                <div class="menu transition hidden" tabindex="-1">
                                <c:forEach var="bugCategory" items="${bugCategories}">
                                    <div class="item" data-value="${bugCategory.bugCategorySlug}">${bugCategory.bugCategoryName}</div>
                                </c:forEach>
                                </div> <!-- .menu -->
                            </div> <!-- .selection -->
                        </div> <!-- .field -->
                        <div class="field">
                            <label>Severity</label>
                            <div class="ui selection dropdown" tabindex="0">
                                <div class="default text">Please choose...</div>
                                <i class="dropdown icon"></i>
                                <input id="bug-severity" type="hidden">
                                <div class="menu transition hidden" tabindex="-1">
                                <c:forEach var="bugSeverity" items="${bugSeverityList}">
                                    <div class="item" data-value="${bugSeverity.bugSeveritySlug}">${bugSeverity.bugSeverityName}</div>
                                </c:forEach>
                                </div> <!-- .menu -->
                            </div> <!-- .selection -->
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div id="markdown-editor" class="tab">
                        <div class="ui top attached tabular menu">
                            <a class="item active" data-tab="editor">Editor</a>
                            <a class="item" data-tab="preview">Preview</a>
                        </div> <!-- .tabular -->
                        <div class="ui bottom attached tab segment active" data-tab="editor">
                            <div class="wmd-panel">
                                <div id="wmd-button-bar"></div> <!-- #wmd-button-bar -->
                                <textarea id="wmd-input" class="wmd-input" placeholder="Leave a comment (Markdown is supported)"></textarea>
                            </div> <!-- .wmd-panel -->
                        </div> <!-- .segment -->
                        <div class="ui bottom attached tab segment" data-tab="preview">
                            <div id="wmd-preview" class="wmd-panel wmd-preview"></div> <!-- .wmd-preview -->
                        </div> <!-- .segment -->
                    </div> <!-- #markdown-editor -->
                </div> <!-- .form -->
            </div> <!-- .content -->
            <div class="actions">
                <button class="ui positive button" type="submit">Submit new issue</button>
                <button class="ui negative button">Cancel</button>
            </div> <!-- .actions -->
        </div> <!-- #bug-modal -->
    </div> <!-- .dimmer -->
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="<c:url value="/assets/js/site.js" />"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/moment.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/markdown.editor.min.js" />"></script>
    <script type="text/javascript">
        $(function() {
            $('.accordion').accordion();
            $('.tabular.menu .item').tab();
        });
    </script>
    <c:if test="${isLogin}">
    <script type="text/javascript">
        $('#new-issue-button').click(function() {
            $('#create-bug-error').addClass('hide');

            $('#bug-modal').modal({
                closable  : false,
                onDeny    : function(){
                    // Clear all fields
                    $('input#title, textarea', '#bug-modal').val('');
                    $('div.wmd-preview', '#bug-modal').html('');
                },
                onApprove : function() {
                    createBug();
                    return false;
                }
            }).modal('show');
        });
    </script>
    <script type="text/javascript">
        $(function() {
            converter = Markdown.getSanitizingConverter();
            converter.hooks.chain("preBlockGamut", function (text, rbg) {
                return text.replace(/^ {0,3}""" *\n((?:.*?\n)+?) {0,3}""" *$/gm, function (whole, inner) {
                    return "<blockquote>" + rbg(inner) + "</blockquote>\n";
                });
            });

            editor    = new Markdown.Editor(converter);
            editor.run();
        });
    </script>
    <script type="text/javascript">
        function getPageRequests(pageNumber) {
            var productId  = ${product.productId};
            return getBugs(productId, pageNumber);
        }
    </script>
    <script type="text/javascript">
        $(function() {
            var pageNumber = 1;
            getPageRequests(pageNumber);
        });
    </script>
    <script type="text/javascript">
        function getBugs(productId, pageNumber) {
            var pageRequests = {
                'productId': productId,
                'page': pageNumber
            };

            $.ajax({
                type: 'GET',
                url: '<c:url value="/products/getBugs.action" />',
                data: pageRequests,
                dataType: 'JSON',
                success: function(result) {
                    if ( result['isSuccessful'] ) {
                        $('#bugs').removeClass('hide');
                        $('#issue-message').addClass('hide');

                        displayBugs(result['bugs']);
                        displayPagination(pageNumber, result['totalPages']);
                    } else {
                        $('#bugs').addClass('hide');
                        $('#issue-message').removeClass('hide');
                    }
                }
            });
        }
    </script>
    <script type="text/javascript">
        function displayBugs(bugs) {
            $('#bugs').empty();

            for ( var i = 0; i < bugs.length; ++ i ) {
                $('#bugs').append(getBugContent(bugs[i]['productVersion'], bugs[i]['createTime'], 
                                                bugs[i]['bugCategory'], bugs[i]['bugStatus'], bugs[i]['hunter'], 
                                                bugs[i]['title'], bugs[i]['description'], bugs[i]['screenshots']));
            }
        }
    </script>
    <script type="text/javascript">
        function getBugContent(productVersion, createTime, bugCategory, bugStatus, hunter, bugTitle, description, screenshots) {
            var bugContentTemplate = '<div class="bug">' + 
                                     '    <div class="title">' + 
                                     '        <i class="dropdown icon"></i> %s' +
                                     '        <a class="ui tag label %s">%s</a>' +  
                                     '        <a class="ui tag label">%s</a>' +  
                                     '        <p class="overview">Created on %s @%s</p>' + 
                                     '        <div class="ui top right attached label %s">%s</div>' + 
                                     '    </div> <!-- .title -->' + 
                                     '    <div class="content">%s</div> <!-- .content -->' + 
                                     '</div> <!-- .bug -->';
            return bugContentTemplate.format(bugTitle, bugCategory['bugCategorySlug'], bugCategory['bugCategoryName'], 
                                             productVersion, getTimeElapsed(createTime), hunter['username'], bugStatus['bugStatusSlug'], 
                                             bugStatus['bugStatusName'], converter.makeHtml(description.replace(/\\n/g, '\n')));
        }
    </script>
    <script type="text/javascript">
        function getTimeElapsed(timeStamp) {
            var dateTime = moment.unix(timeStamp / 1000);
            return dateTime.fromNow();
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
    <script type="text/javascript">
        $('.pagination').delegate('a.item', 'click', function(e) {
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
        function createBug() {
            var productId      = ${product.productId},
                productVersion = $('#version', '#bug-modal').val(),
                bugCategory    = $('#bug-category', '#bug-modal').val(),
                bugSeverity    = $('#bug-severity', '#bug-modal').val(),
                title          = $('#title', '#bug-modal').val(),
                description    = $('#wmd-input', '#bug-modal').val(),
                screenshots    = null;

            return doCreateBugAction(productId, productVersion, bugCategory, bugSeverity, title, description, screenshots);
        }
    </script>
    <script type="text/javascript">
        function doCreateBugAction(productId, productVersion, bugCategory, bugSeverity, title, description, screenshots) {
            $('div.form', '#bug-modal').addClass('loading');
            
            var postData = {
                'productId': productId, 
                'version': productVersion, 
                'bugCategory': bugCategory, 
                'bugSeverity': bugSeverity, 
                'title': title, 
                'description': description, 
                'screenshots': screenshots
            };
            $.ajax({
                type: 'POST',
                url: '<c:url value="/products/createBug.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result) {
                    $('#create-bug-error').addClass('hide');
                    $('div.form', '#bug-modal').removeClass('loading');

                    processResult(result);
                }
            });
        }
    </script>
    <script type="text/javascript">
        function processResult(result) {
            if ( result['isSuccessful'] ) {
                $('#bug-modal').modal('hide');

                var pageNumber = 1;
                getPageRequests(pageNumber);
            } else {
                var errorMessage  = '';

                if ( result['isHunterEmpty'] ) {
                    errorMessage += 'Please sign in before reporting an issue.<br />';
                }
                if ( !result['isProductExists'] ) {
                    errorMessage += 'The product isn\'t exists.<br />';
                }
                if ( result['isProductVersionEmpty'] ) {
                    errorMessage += 'You can\'t leave <strong>Product Version</strong> empty.<br />';
                } else if ( !result['isProductVersionLegal'] ) {
                    errorMessage += 'The length of <strong>Product Version</strong> must not exceed 24 characters.<br />';
                }
                if ( result['isCategoryEmpty'] ) {
                    errorMessage += 'Please choose the <strong>Category</strong> of the issue.<br />';
                }
                if ( result['isSeverityEmpty'] ) {
                    errorMessage += 'Please choose the <strong>Severity</strong> of the issue.<br />';
                }
                if ( result['isTitleEmpty'] ) {
                    errorMessage += 'You can\'t leave <strong>Title</strong> empty.<br />';
                } else if ( !result['isTitleLegal'] ) {
                    errorMessage += 'The length of <strong>Title</strong> must not exceed 64 characters.<br />';
                }
                if ( result['isDescriptionEmpty'] ) {
                    errorMessage += 'You can\'t leave <strong>Description</strong> empty.<br />';
                }

                $('#create-bug-error').html(errorMessage);
                $('#create-bug-error').removeClass('hide');
            }
        }
    </script>
    </c:if>
</body>
</html>