<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Sign Up | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/semantic.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/font-awesome.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/style.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/accounts/join.css" />" />
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
            <div id="slogan" class="column">
                <h1>Join TestZilla</h1>
                <h2>The best way to test your software.</h2>
            </div> <!-- .column -->
        </div> <!-- .row -->
        <div class="row">
            <div id="steps" class="column">
                <div class="ui steps fluid">
                    <a class="active step">
                        <i class="icon user"></i>
                        <div class="content">
                            <div class="title">Step 1</div> <!-- .title -->
                            <div class="description">Create your account</div> <!-- .description -->
                        </div> <!-- .content -->
                    </a>
                    <a class="step">
                        <i class="icon mail"></i>
                        <div class="content">
                            <div class="title">Step 2</div> <!-- .title -->
                            <div class="description">Validate your email</div> <!-- .description -->
                        </div> <!-- .content -->
                    </a>
                    <a class="step">
                        <i class="icon dashboard"></i>
                        <div class="content">
                            <div class="title">Step 3</div> <!-- .title -->
                            <div class="description">Go to your dashboard</div> <!-- .description -->
                        </div> <!-- .content -->
                    </a>
                </div> <!-- .steps -->
            </div> <!-- #steps -->
        </div> <!-- .row -->
        <div class="row">
            <div class="twelve wide column">
                <h3>Create your account</h3>
                <div class="ui error message">
                    <div class="header">Error Message</div>
                    <p></p>
                </div> <!-- .error -->
                <form id="join-form" class="ui form segment" onSubmit="return false;">
                    <div class="two fields">
                        <div class="field required">
                            <label>Account Type</label>
                            <div class="ui selection dropdown" tabindex="0">
                                <div class="default text"></div>
                                <i class="dropdown icon"></i>
                                <input id="account-type" type="hidden">
                                <div class="menu transition hidden" tabindex="-1">
                                    <div class="item" data-value="tester">Tester</div>
                                    <div class="item" data-value="developer">Developer</div>
                                </div> <!-- .menu -->
                            </div> <!-- .selection -->
                        </div> <!-- .field -->
                        <div class="field required">
                            <label>Individual/Enterprise</label>
                            <div class="ui selection dropdown" tabindex="0">
                                <div class="default text"></div>
                                <i class="dropdown icon"></i>
                                <input name="hidden-field" type="hidden">
                                <div class="menu transition hidden" tabindex="-1">
                                    <div class="item" data-value="individual">Individual</div>
                                    <div class="item" data-value="enterprise">Enterprise</div>
                                </div> <!-- .menu -->
                            </div> <!-- .selection -->
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two fields">
                        <div class="field required">
                            <label for="username">Username</label>
                            <input id="username" type="text" maxlength="16" />
                        </div> <!-- .field -->
                        <div class="field required">
                            <label for="email">Email</label>
                            <input id="email" type="text" maxlength="64" />
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two fields">
                        <div class="field required">
                            <label for="password">Password</label>
                            <input id="password" type="password" maxlength="16" />
                        </div> <!-- .field -->
                        <div class="field required">
                            <label for="confirm-password">Confirm Password</label>
                            <input id="confirm-password" type="password" maxlength="64" />
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two fields">
                        <div class="field required">
                            <label for="real-name">Real Name</label>
                            <input id="real-name" type="text" maxlength="32" />
                        </div> <!-- .field -->
                        <div class="field required">
                            <label for="email">Phone</label>
                            <input id="phone" type="text" maxlength="24" />
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two fields">
                        <div class="field required">
                            <label for="country">Country</label>
                            <div class="ui selection dropdown" tabindex="0">
                                <div class="default text"></div>
                                <i class="dropdown icon"></i>
                                <input id="country" type="hidden">
                                <div class="menu transition hidden" tabindex="-1">
                                </div> <!-- .menu -->
                            </div> <!-- .selection -->
                        </div> <!-- .field -->
                        <div class="field required">
                            <label for="province">State(Province)</label>
                            <input id="province" type="text" maxlength="24" />
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two fields">
                        <div class="field">
                            <label for="city">City</label>
                            <input id="city" type="text" maxlength="24" />
                        </div> <!-- .field -->
                        <div class="field">
                            <label for="website">Website</label>
                            <input id="website" type="text" maxlength="64" />
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="inline field">
                        <div class="ui checkbox">
                            <input type="checkbox">
                            <label>By clicking on &quot;Create Account&quot; below, you are agreeing to the <a href="<c:url value="/terms" />" target="_blank">Terms of Service</a>.</label>
                        </div> <!-- .checkbox -->
                    </div> <!-- .field -->
                    <button class="ui positive button fluid" type="submit">Create Account</button>
                </form> <!-- #join-form -->
            </div> <!-- .column -->
            <div id="tips" class="four wide column">
                <div class="ui top attached header">
                    <h3>You'll love TestZilla</h3>
                </div> <!-- .top -->
                <div class="ui attached segment">
                    <div class="ui list">
                        <div class="item">
                            Unlimited Products
                        </div> <!-- .item -->
                        <div class="item">
                            Better Service for Enterprise 
                        </div> <!-- .item -->
                    </div> <!-- .list -->
                </div> <!-- .segment -->
                <div class="ui bottom attached header">
                    <div class="ui list">
                        <div class="item">
                            <i class="checkmark icon"></i> Great communication
                        </div> <!-- .item -->
                        <div class="item">
                            <i class="checkmark icon"></i> Lower expenditure
                        </div> <!-- .item -->
                        <div class="item">
                            <i class="checkmark icon"></i> Direct feedback
                        </div> <!-- .item -->
                    </div> <!-- .list -->
                </div> <!-- .bottom -->
            </div> <!-- .column -->
        </div> <!-- .row -->
    </div> <!-- #content -->
    <!-- Footer -->
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="<c:url value="/assets/js/site.js" />"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/country.js" />"></script>
    <script type="text/javascript">
        $(function() {
            $('.ui.checkbox').checkbox();
        });
    </script>
    <script type="text/javascript">
        $('#account-type').change(function() {
            if ( $(this).val() == 'tester' ) {
            } else {
            }
        });
    </script>
</body>
</html>