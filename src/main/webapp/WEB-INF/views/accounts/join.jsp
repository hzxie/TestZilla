<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@propertyConfigurer.getProperty('cdn.url')" var="cdnUrl" />
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title><spring:message code="testzilla.accounts.join.sign-up" text="Sign Up" /> | TestZilla</title>
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
                <h1><spring:message code="testzilla.accounts.join.join" text="Join" /> TestZilla</h1>
                <h2><spring:message code="testzilla.accounts.join.slogan" text="The best way to test your software." /></h2>
            </div> <!-- .column -->
        </div> <!-- .row -->
        <div class="row">
            <div id="steps" class="column">
                <div class="ui steps fluid">
                    <a class="active step">
                        <i class="icon user"></i>
                        <div class="content">
                            <div class="title"><spring:message code="testzilla.accounts.step-1" text="Step 1" /></div> <!-- .title -->
                            <div class="description"><spring:message code="testzilla.accounts.create-your-account" text="Create your account" /></div> <!-- .description -->
                        </div> <!-- .content -->
                    </a>
                    <a class="step">
                        <i class="icon mail"></i>
                        <div class="content">
                            <div class="title"><spring:message code="testzilla.accounts.step-2" text="Step 2" /></div> <!-- .title -->
                            <div class="description"><spring:message code="testzilla.accounts.verify-your-email" text="Verify your email" /></div> <!-- .description -->
                        </div> <!-- .content -->
                    </a>
                    <a class="step">
                        <i class="icon dashboard"></i>
                        <div class="content">
                            <div class="title"><spring:message code="testzilla.accounts.step-3" text="Step 3" /></div> <!-- .title -->
                            <div class="description"><spring:message code="testzilla.accounts.go-to-your-dashboard" text="Go to your dashboard" /></div> <!-- .description -->
                        </div> <!-- .content -->
                    </a>
                </div> <!-- .steps -->
            </div> <!-- #steps -->
        </div> <!-- .row -->
        <div class="row">
            <div class="twelve wide column">
                <h3><spring:message code="testzilla.accounts.join.create-your-account" text="Create your account" /></h3>
                <div class="ui error message hide">
                    <div class="header"><spring:message code="testzilla.accounts.join.error-message" text="Error Message" /></div>
                    <p></p>
                </div> <!-- .error -->
                <form id="join-form" class="ui form segment" onSubmit="onSubmit(); return false;">
                    <div class="two fields">
                        <div class="field required">
                            <label><spring:message code="testzilla.accounts.join.individual" text="Individual" />/<spring:message code="testzilla.accounts.join.enterprise" text="Enterprise" /></label>
                            <div class="ui selection dropdown" tabindex="0">
                                <div class="default text"></div>
                                <i class="dropdown icon"></i>
                                <input id="invididual-or-enterprise" type="hidden">
                                <div class="menu transition hidden" tabindex="-1">
                                    <div class="item" data-value="individual"><spring:message code="testzilla.accounts.join.individual" text="Individual" /></div>
                                    <div class="item" data-value="enterprise"><spring:message code="testzilla.accounts.join.enterprise" text="Enterprise" /></div>
                                </div> <!-- .menu -->
                            </div> <!-- .selection -->
                        </div> <!-- .field -->
                        <div class="field required">
                            <label><spring:message code="testzilla.accounts.join.account-type" text="Account Type" /></label>
                            <div class="ui selection dropdown" tabindex="0">
                                <div class="default text"></div>
                                <i class="dropdown icon"></i>
                                <input id="account-type" type="hidden">
                                <div class="menu transition hidden" tabindex="-1">
                                    <div class="item" data-value="tester"><spring:message code="testzilla.accounts.join.tester" text="Tester" /></div>
                                    <div class="item" data-value="developer"><spring:message code="testzilla.accounts.join.developer" text="Developer" /></div>
                                </div> <!-- .menu -->
                            </div> <!-- .selection -->
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two fields">
                        <div class="field required">
                            <label for="username"><spring:message code="testzilla.accounts.join.username" text="Username" /></label>
                            <input id="username" type="text" maxlength="16" />
                        </div> <!-- .field -->
                        <div class="field required">
                            <label for="email"><spring:message code="testzilla.accounts.join.email" text="Email" /></label>
                            <input id="email" type="text" maxlength="64" />
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two fields">
                        <div class="field required">
                            <label for="password"><spring:message code="testzilla.accounts.join.password" text="Password" /></label>
                            <input id="password" type="password" maxlength="16" />
                        </div> <!-- .field -->
                        <div class="field required">
                            <label for="confirm-password"><spring:message code="testzilla.accounts.join.confirm-password" text="Confirm Password" /></label>
                            <input id="confirm-password" type="password" maxlength="64" />
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two fields">
                        <div class="field required">
                            <label for="real-name"><spring:message code="testzilla.accounts.join.your-name" text="Your Name" /></label>
                            <input id="real-name" type="text" maxlength="32" />
                        </div> <!-- .field -->
                        <div class="field required">
                            <label for="email"><spring:message code="testzilla.accounts.join.phone" text="Phone" /></label>
                            <input id="phone" type="text" maxlength="24" />
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two fields">
                        <div class="field required">
                            <label for="country"><spring:message code="testzilla.accounts.join.country" text="Country" /></label>
                            <div class="ui selection dropdown" tabindex="0">
                                <div class="default text"></div>
                                <i class="dropdown icon"></i>
                                <input id="country" type="hidden">
                                <div class="menu transition hidden" tabindex="-1">
                                </div> <!-- .menu -->
                            </div> <!-- .selection -->
                        </div> <!-- .field -->
                        <div class="field required">
                            <label for="province"><spring:message code="testzilla.accounts.join.state-province" text="State(Province)" /></label>
                            <input id="province" type="text" maxlength="24" />
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two fields">
                        <div class="field">
                            <label for="city"><spring:message code="testzilla.accounts.join.city" text="City" /></label>
                            <input id="city" type="text" maxlength="24" />
                        </div> <!-- .field -->
                        <div class="field">
                            <label for="website"><spring:message code="testzilla.accounts.join.website" text="Website" /></label>
                            <input id="website" type="text" maxlength="64" />
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="field">
                        <label><spring:message code="testzilla.accounts.join.hint" text="By clicking on &quot;Create Account&quot; below, you are agreeing to the " /><a href="<c:url value="/terms" />" target="_blank"><spring:message code="testzilla.accounts.join.terms-of-service" text="Terms of Service" /></a>.</label>
                    </div> <!-- .field -->
                    <button class="ui positive button fluid" type="submit"><spring:message code="testzilla.accounts.join.create-account" text="Create Account" /></button>
                </form> <!-- #join-form -->
            </div> <!-- .column -->
            <div id="tips" class="four wide column">
                <div class="ui top attached header">
                    <h3><spring:message code="testzilla.accounts.join.youll-love" text="You'll love" /> TestZilla</h3>
                </div> <!-- .top -->
                <div class="ui attached segment">
                    <div class="ui list">
                        <div class="item">
                            <spring:message code="testzilla.accounts.join.unlimited-products" text="Unlimited Products" />
                        </div> <!-- .item -->
                        <div class="item">
                            <spring:message code="testzilla.accounts.join.better-service-for-enterprise" text="Better Service for Enterprise" /> 
                        </div> <!-- .item -->
                    </div> <!-- .list -->
                </div> <!-- .segment -->
                <div class="ui bottom attached header">
                    <div class="ui list">
                        <div class="item">
                            <i class="checkmark icon"></i> <spring:message code="testzilla.accounts.join.greate-communication" text="Great communication" />
                        </div> <!-- .item -->
                        <div class="item">
                            <i class="checkmark icon"></i> <spring:message code="testzilla.accounts.join.lower-expenditure" text="Lower expenditure" />
                        </div> <!-- .item -->
                        <div class="item">
                            <i class="checkmark icon"></i> <spring:message code="testzilla.accounts.join.direct-feedback" text="Direct feedback" />
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
            $('.ui.checkbox', '#content').checkbox();
            $('.ui.dropdown', '#content').dropdown();
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
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.username-empty" text="You can&apos;t leave <strong>Username</strong> empty." /><br>';
                } else if ( !result['isUsernameLegal'] ) {
                    var username = $('#username').val();
                    if ( username.length < 6 || username.length > 16 ) {
                        errorMessage += '<spring:message code="testzilla.accounts.join.error.username-illegal-length" text="The length of <strong>Username</strong> must between 6 and 16 characters." /><br>';
                    } else if ( !username[0].match(/[a-z]/i) ) {
                        errorMessage += '<spring:message code="testzilla.accounts.join.error.username-illegal-beginning" text="<strong>Username</strong> must start with a letter(a-z)." /><br>';
                    } else {
                        errorMessage += '<spring:message code="testzilla.accounts.join.error.username-illegal-character" text="<strong>Username</strong> can only contain letters(a-z), numbers, and underlines(_)." /><br>';
                    }
                } else if ( result['isUsernameExists'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.username-already-exists" text="Someone already has that username." /><br>';
                }
                if ( result['isPasswordEmpty'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.password-empty" text="You can&apos;t leave <strong>Password</strong> empty." /><br>';
                } else if ( !result['isPasswordLegal'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.password-illegal-length" text="The length of <strong>Password</strong> must between 6 and 16 characters." /><br>';
                } else if ( !result['isPasswordMatched'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.password-unmatch" text="These passwords don&apos;t match." /><br>';
                }
                if ( !result['isUserGroupLegal'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.account-type-empty" text="Please choose your <strong>Account Type</strong>." /><br>';
                }
                if ( result['isRealNameEmpty'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.name-empty" text="You can&apos;t leave <strong>Your Name/Company Name</strong> empty." /><br>';
                } else if ( !result['isRealNameLegal'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.name-too-long" text="The length of <strong>Your Name/Company Name</strong> must not exceed 32 characters." /><br>';
                }
                if ( result['isEmailEmpty'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.email-empty" text="You can&apos;t leave <strong>Email</strong> empty." /><br>';
                } else if ( !result['isEmailLegal'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.email-invalid" text="The <strong>Email</strong> seems invalid." /><br>';
                } else if ( result['isEmailExists'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.email-already-exists" text="Someone already use that email." /><br>';
                }
                if ( result['isCountryEmpty'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.country-empty" text="You can&apos;t leave <strong>Country</strong> empty." /><br>';
                } else if ( !result['isCountryLegal'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.country-too-long" text="The length of <strong>Country</strong> must not exceed 24 characters." /><br>';
                }
                if ( result['isProvinceEmpty'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.state-empty" text="You can&apos;t leave <strong>State(Province)</strong> empty." /><br>';
                } else if ( !result['isProvinceLegal'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.state-too-long" text="The length of <strong>State(Province)</strong> must not exceed 24 characters." /><br>';
                }
                if ( !result['isCityLegal'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.city-too-long" text="The length of <strong>City</strong> must not exceed 24 characters." /><br>';
                }
                if ( result['isPhoneEmpty'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.phone-empty" text="You can&apos;t leave <strong>Phone</strong> empty." /><br>';
                } else if ( !result['isPhoneLegal'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.phone-invalid" text="The <strong>Phone</strong> seems invalid." /><br>';
                }
                if ( !result['isWebsiteLegal'] ) {
                    errorMessage += '<spring:message code="testzilla.accounts.join.error.website-invalid" text="The <strong>Website</strong> seems invalid." /><br>';
                }
                $('#join-form').removeClass('loading');
                $('.error > p').html(errorMessage);
                $('.error').css('display', 'block');
            }
        }
    </script>
</body>
</html>