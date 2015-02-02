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
                    <a class="ui ribbon label">Product Detail</a>
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
                    <a class="ui ribbon label">Issues</a>
                    <button class="ui button positive">New Issue</button>
                    <div id="bugs" class="ui styled accordion fluid">
                        <div class="bug">
                            <div class="title">
                                <i class="dropdown icon"></i>[Confirmed] Bug #1
                            </div> <!-- .title -->
                            <div class="content">
                                <img class="ui wireframe image transition hidden" src="http://semantic-ui.com/images/wireframe/paragraph.png">
                            </div> <!-- .content -->
                        </div> <!-- .bug -->
                    </div> <!-- #bugs -->
                </div> <!-- .segment -->
                <div class="ui segment">
                    <a class="ui ribbon label">Questionnaire</a>
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
            <div class="ui form content">
                <div class="two required fields">
                    <div class="field">
                        <label for="title">Title</label>
                        <input id="title" placeholder="" type="text" />
                    </div> <!-- .field -->
                    <div class="field">
                        <label for="version">Software Version</label>
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
            </div> <!-- .content -->
            <div class="actions">
                <button class="ui green button">Submit new issue</button>
                <button class="ui button">Cancel</button>
            </div> <!-- .actions -->
        </div> <!-- #bug-modal -->
    </div> <!-- .dimmer -->
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="<c:url value="/assets/js/site.js" />"></script>
    <script type="text/javascript">
        $(function() {
            $('.accordion').accordion();
            $('.tabular.menu .item').tab();
        });
    </script>
    <script type="text/javascript">
        $('.positive').click(function() {
            $('.modal').modal({
                closable  : false
            }).modal('show');
        });
    </script>
    <!-- MarkDown Editor -->
    <script type="text/javascript" src="<c:url value="/assets/js/markdown.editor.min.js" />"></script>
    <script type="text/javascript">
        $(function() {
            var converter = Markdown.getSanitizingConverter();
            converter.hooks.chain("preBlockGamut", function (text, rbg) {
                return text.replace(/^ {0,3}""" *\n((?:.*?\n)+?) {0,3}""" *$/gm, function (whole, inner) {
                    return "<blockquote>" + rbg(inner) + "</blockquote>\n";
                });
            });

            var editor = new Markdown.Editor(converter);
            editor.run();
        });
    </script>
</body>
</html>