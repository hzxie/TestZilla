<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@propertyConfigurer.getProperty('cdn.url')" var="cdnUrl" />
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Sign Up | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="author" content="谢浩哲">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/semantic.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/accounts/join.css" />
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
                            <div class="description">Verify your email</div> <!-- .description -->
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
                <div class="ui error message hide">
                    <div class="header">Error Message</div>
                    <p></p>
                </div> <!-- .error -->
                <form id="join-form" class="ui form segment" onSubmit="onSubmit(); return false;">
                    <div class="two fields">
                        <div class="field required">
                            <label>Individual/Enterprise</label>
                            <div class="ui selection dropdown" tabindex="0">
                                <div class="default text"></div>
                                <i class="dropdown icon"></i>
                                <input id="invididual-or-enterprise" type="hidden">
                                <div class="menu transition hidden" tabindex="-1">
                                    <div class="item" data-value="individual">Individual</div>
                                    <div class="item" data-value="enterprise">Enterprise</div>
                                </div> <!-- .menu -->
                            </div> <!-- .selection -->
                        </div> <!-- .field -->
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
                            <label for="real-name">Your Name</label>
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
                    <div class="field">
                        <label>By clicking on &quot;Create Account&quot; below, you are agreeing to the <a href="<c:url value="/terms" />" target="_blank">Terms of Service</a>.</label>
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
    <script type="text/javascript" src="${cdnUrl}/js/site.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/country.js"></script>
    <script type="text/javascript">
        $(function() {
            $('.ui.checkbox').checkbox();
        });
    </script>
    <script type="text/javascript">
        $('#invididual-or-enterprise').change(function() {
            var accountTypeDropdown = $('#account-type').parent(),
                realNameLabel       = $('#real-name').parent().find('label');

            if ( $(this).val() == 'individual' ) {
                realNameLabel.html('Your Name');
            } else {
                realNameLabel.html('Company Name');
            }
        });
    </script>
    <script type="text/javascript">
        function onSubmit() {
            var username        = $('#username').val(),
                password        = $('#password').val(),
                confirmPassword = $('#confirm-password').val(),
                userGroup       = $('#account-type').val(),
                realName        = $('#real-name').val(),
                email           = $('#email').val(),
                country         = $('#country').val(),
                province        = $('#province').val(),
                city            = $('#city').val(),
                phone           = $('#phone').val(),
                website         = $('#website').val(),
                isIndividual    = $('#invididual-or-enterprise').val() == 'individual';

            return doJoinAction(username, password, confirmPassword, userGroup, realName, 
                                email, country, province, city, phone, website, isIndividual);
        }
    </script>
    <script type="text/javascript">
        function doJoinAction(username, password, confirmPassword, userGroup, realName, 
                              email, country, province, city, phone, website, isIndividual) {
            $('#join-form').addClass('loading');
            
            var postData = {
                'username': username, 
                'password': password, 
                'confirmPassword': confirmPassword, 
                'userGroup': userGroup, 
                'realName': realName, 
                'email': email, 
                'country': country, 
                'province': province, 
                'city': city, 
                'phone': phone, 
                'website': website, 
                'isIndividual': isIndividual
            };
            $.ajax({
                type: 'POST',
                url: '<c:url value="/accounts/join.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result) {
                    processResult(result);
                }
            });
        }
    </script>
    <script type="text/javascript">
        function processResult(result) {
            if ( result['isSuccessful'] ) {
                window.location.href = '<c:url value="/accounts/verifyEmail" />';
            } else {
                var errorMessage  = '';

                if ( result['isUsernameEmpty'] ) {
                    errorMessage += 'You can\'t leave <strong>Username</strong> empty.<br>';
                } else if ( !result['isUsernameLegal'] ) {
                    var username = $('#username').val();
                    if ( username.length < 6 || username.length > 16 ) {
                        errorMessage += 'The length of <strong>Username</strong> must between 6 and 16 characters.<br>';
                    } else if ( !username[0].match(/[a-z]/i) ) {
                        errorMessage += '<strong>Username</strong> must start with a letter(a-z).<br>';
                    } else {
                        errorMessage += '<strong>Username</strong> can only contain letters(a-z), numbers, and underlines(_).<br>';
                    }
                } else if ( result['isUsernameExists'] ) {
                    errorMessage += 'Someone already has that username.<br>';
                }
                if ( result['isPasswordEmpty'] ) {
                    errorMessage += 'You can\'t leave <strong>Password</strong> empty.<br>';
                } else if ( !result['isPasswordLegal'] ) {
                    errorMessage += 'The length of <strong>Password</strong> must between 6 and 16 characters.<br>';
                } else if ( !result['isPasswordMatched'] ) {
                    errorMessage += 'These passwords don\'t match.<br>';
                }
                if ( !result['isUserGroupLegal'] ) {
                    errorMessage += 'Please choose your <strong>Account Type</strong>.<br>';
                }
                if ( result['isRealNameEmpty'] ) {
                    errorMessage += 'You can\'t leave <strong>Your Name/Company Name</strong> empty.<br>';
                } else if ( !result['isRealNameLegal'] ) {
                    errorMessage += 'The length of <strong>Your Name/Company Name</strong> must not exceed 32 characters.<br>';
                }
                if ( result['isEmailEmpty'] ) {
                    errorMessage += 'You can\'t leave <strong>Email</strong> empty.<br>';
                } else if ( !result['isEmailLegal'] ) {
                    errorMessage += 'The <strong>Email</strong> seems invalid.<br>';
                } else if ( result['isEmailExists'] ) {
                    errorMessage += 'Someone already use that email.<br>';
                }
                if ( result['isCountryEmpty'] ) {
                    errorMessage += 'You can\'t leave <strong>Country</strong> empty.<br>';
                } else if ( !result['isCountryLegal'] ) {
                    errorMessage += 'The length of <strong>Country</strong> must not exceed 24 characters.<br>';
                }
                if ( result['isProvinceEmpty'] ) {
                    errorMessage += 'You can\'t leave <strong>State(Province)</strong> empty.<br>';
                } else if ( !result['isProvinceLegal'] ) {
                    errorMessage += 'The length of <strong>State(Province)</strong> must not exceed 24 characters.<br>';
                }
                if ( !result['isCityLegal'] ) {
                    errorMessage += 'The length of <strong>City</strong> must not exceed 24 characters.<br>';
                }
                if ( result['isPhoneEmpty'] ) {
                    errorMessage += 'You can\'t leave <strong>Phone</strong> empty.<br>';
                } else if ( !result['isPhoneLegal'] ) {
                    errorMessage += 'The <strong>Phone</strong> seems invalid.<br>';
                }
                if ( !result['isWebsiteLegal'] ) {
                    errorMessage += 'The <strong>Website</strong> seems invalid.<br>';
                }
                $('#join-form').removeClass('loading');
                $('.error > p').html(errorMessage);
                $('.error').css('display', 'block');
            }
        }
    </script>
</body>
</html>