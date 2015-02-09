<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>Dashboard | TestZilla</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Icon -->
    <link href="<c:url value="/assets/img/favicon.ico" />" rel="shortcut icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/semantic.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/font-awesome.min.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/style.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/assets/css/accounts/dashboard.css" />" />
    <!-- JavaScript -->
    <script type="text/javascript" src="<c:url value="/assets/js/jquery-1.11.1.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/semantic.min.js" />"></script>
</head>
<body>
    <!-- Header -->
    <%@ include file="/WEB-INF/views/include/header.jsp" %>
    <!-- Content -->
    <div id="content">
        <div id="sub-header" class="ui page stackable grid segment">
            <div class="row">
                <div class="column">
                    <h1><i class="fa fa-dashboard"></i> Dashboard</h1>
                    <div class="ui breadcrumb">
                        <a href="<c:url value="/" />" class="section">Home</a>
                        <div class="divider"> / </div>
                        <div class="active section">Dashboard</div>
                    </div> <!-- .breadcrumb -->
                </div> <!-- .column -->
            </div> <!-- .row -->
            <div class="four item tabular ui menu">
                <a class="item active" data-tab="overview">Overview</a>
                <a class="item" data-tab="issues">Issues</a>
                <a class="item" data-tab="products">Products</a>
                <a class="item" data-tab="questionnaires">Questionnaires</a>
            </div> <!-- .tabular -->
        </div> <!-- #sub-header -->
        <div id="main-container" class="ui page stackable grid">
            <div id="overview-tab" class="ui tab active" data-tab="overview">
                <div class="ui stackable grid">
                    <div class="row">
                        <div class="four wide column">
                            <div class="ui card">
                                <div class="image dimmable">
                                    <img src="<c:url value="/assets/img/default-avatar.jpg" />">
                                </div> <!-- .image -->
                                <div class="content">
                                    <div class="header">${user.username}</div>
                                    <div class="meta">
                                        <a class="group">${user.realName}</a>
                                    </div> <!-- .meta -->
                                    <div class="description">
                                        <div class="ui list">
                                            <div class="item"><i class="icon mail"></i> ${user.email}</div> <!-- .item -->
                                            <div class="item"><i class="icon marker"></i> ${user.province}, ${user.country}</div> <!-- .item -->
                                        </div> <!-- .list -->
                                    </div>
                                </div> <!-- .content -->
                                <div class="extra content">
                                    <a id="edit-profile-trigger" class="right floated created" href="javascript:void(0);">Edit Profile</a>
                                </div> <!-- .extra -->
                            </div> <!-- .card -->
                        </div> <!-- .four -->
                        <div class="twelve wide column">
                            <div class="ui stackable grid">
                                <div class="two column row">
                                    <div class="column">
                                        <div class="ui statistic">
                                            <div class="value"><i class="trophy icon"></i> ${user.reputation}</div>
                                            <div class="label">Reputation</div>
                                        </div> <!-- .statistic -->
                                    </div> <!-- .column -->
                                    <div class="column">
                                        <div class="ui statistic">
                                            <div class="value"><i class="ticket icon"></i> N/a</div>
                                            <div class="label">Credits</div>
                                        </div> <!-- .statistic -->
                                    </div> <!-- .column -->
                                </div> <!-- .row -->
                                <div class="ui divider"></div> <!-- .divider -->
                                <div class="row">
                                    <div class="column">
                                        <h4 class="ui header"><i class="icon feed"></i> Feed</h4>
                                        <div class="ui feed">
                                            <div class="event">
                                                <div class="label">
                                                    <img src="http://semantic-ui.com/images/avatar/small/elliot.jpg">
                                                </div> <!-- .label -->
                                                <div class="content">
                                                    <div class="summary">
                                                    	Some text here...
                                                        <div class="date">1 Hour Ago</div>
                                                    </div> <!-- .summary -->
                                                </div> <!-- .content -->
                                            </div> <!-- .event -->
                                        </div> <!-- .feed -->
                                    </div> <!-- .column -->
                                </div> <!-- .row -->
                            </div> <!-- .grid -->
                        </div> <!-- .twelve -->
                    </div> <!-- .row -->
                </div> <!-- .grid -->
            </div> <!-- #overview-tab -->
            <div id="issues-tab" class="ui tab" data-tab="issues">
                <div class="ui stackable grid">
                    <div class="row">
                        <div class="four wide column">
                            <div class="ui vertical fluid menu">
                                <a class="item active">Received Issues</a>
                                <a class="item">Sent Issues</a>
                                <div class="item">
                                    <div class="ui transparent icon input">
                                        <input type="text" placeholder="Search...">
                                        <i class="search icon"></i>
                                    </div> <!-- .input -->
                                </div> <!-- .item -->
                            </div> <!-- .menu -->
                        </div> <!-- .four -->
                        <div class="twelve wide column">
                        	<div class="ui info message">There aren't any issues.</div>
                        </div> <!-- .twelve -->
                    </div> <!-- .row -->
                </div> <!-- .grid -->
            </div> <!-- #issues-tab -->
            <div id="products-tab" class="ui tab" data-tab="products">
                <div class="ui stackable grid">
                    <div class="row">
                        <div class="four wide column">
                            <div class="ui vertical fluid menu">
                                <a class="item active">My Products</a>
                                <a class="item">New Product</a>
                                <div class="item">
                                    <div class="ui transparent icon input">
                                        <input type="text" placeholder="Search...">
                                        <i class="search icon"></i>
                                    </div> <!-- .input -->
                                </div> <!-- .item -->
                            </div> <!-- .menu -->
                        </div> <!-- .four -->
                        <div class="twelve wide column">
                            <div class="ui info message">There aren't any products.</div>
                        </div> <!-- .twelve -->
                    </div> <!-- .row -->
                </div> <!-- .grid -->
            </div> <!-- #products-tab -->
            <div id="questionnaires-tab" class="ui tab" data-tab="questionnaires">
                <div class="ui stackable grid">
                    <div class="row">
                        <div class="four wide column">
                        	<div class="ui vertical fluid menu">
                                <a class="item active">My Questionnaires</a>
                                <a class="item">New Questionnaire</a>
                                <div class="item">
                                    <div class="ui transparent icon input">
                                        <input type="text" placeholder="Search...">
                                        <i class="search icon"></i>
                                    </div> <!-- .input -->
                                </div> <!-- .item -->
                            </div> <!-- .menu -->
                        </div> <!-- .four -->
                        <div class="twelve wide column">
                        	<div class="ui info message">There aren't any questionnaires.</div>
                        </div> <!-- .twelve -->
                    </div> <!-- .row -->
                </div> <!-- .grid -->
            </div> <!-- #questionnaires-tab -->
        </div> <!-- #main-container -->
    </div> <!-- #content -->
    <!-- Footer -->
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
    <!-- Modals -->
    <div class="ui dimmer modals page transition hidden">
        <div id="profile-modal" class="ui long test modal transition scrolling hidden">
            <i class="close icon"></i>
            <div class="header">
                <h3>Edit Profile</h3>
            </div> <!-- .header -->
            <div class="content">
                <div id="profile-error" class="ui error message hide"></div> <!-- #profile-error -->
                <div class="ui form">
                    <div class="two required fields">
                        <div class="field">
                        <c:choose>
                        <c:when test="${user.isIndividual()}">
                            <label for="real-name">Real Name</label>
                        </c:when>
                        <c:otherwise>
                            <label for="real-name">Company Name</label>
                        </c:otherwise>
                        </c:choose>
                            <input id="real-name" type="text" value="${user.realName}" />
                        </div> <!-- .field -->
                        <div class="field">
                            <label for="email">Email</label>
                            <input id="email" type="text" value="${user.email}" />
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two fields">
                        <div class="field required">
                            <label for="country">Country</label>
                            <div class="ui selection dropdown" tabindex="0">
                                <div class="text">${user.country}</div>
                                <i class="dropdown icon"></i>
                                <input id="country" type="hidden" value="${user.country}">
                                <div class="menu transition hidden" tabindex="-1">
                                </div> <!-- .menu -->
                            </div> <!-- .selection -->
                        </div> <!-- .field -->
                        <div class="field required">
                            <label for="province">State(Province)</label>
                            <input id="province" type="text" value="${user.province}" maxlength="24" />
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two fields">
                        <div class="field">
                            <label for="city">City</label>
                            <input id="city" type="text" value="${user.city}" maxlength="24" />
                        </div> <!-- .field -->
                        <div class="field"></div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two fields">
                        <div class="required field">
                            <label for="phone">Phone</label>
                            <input id="phone" type="text" value="${user.phone}" maxlength="24" />
                        </div> <!-- .field -->
                        <div class="field">
                            <label for="website">Website</label>
                            <input id="website" type="text" value="${user.website}" maxlength="64" />
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two fields">
                        <div class="field">
                            <label for="old-password">Old Password</label>
                            <input id="old-password" type="password" maxlength="16" />
                        </div> <!-- .field -->
                        <div class="field"></div> <!-- .field -->
                    </div> <!-- .fields -->
                    <div class="two fields">
                        <div class="field">
                            <label for="new-password">New Password</label>
                            <input id="new-password" type="password" maxlength="16" />
                        </div> <!-- .field -->
                        <div class="field">
                            <label for="confirm-password">Confirm Password</label>
                            <input id="confirm-password" type="password" maxlength="16" />
                        </div> <!-- .field -->
                    </div> <!-- .fields -->
                </div> <!-- .form -->
            </div> <!-- .content -->
            <div class="actions">
                <button class="ui positive button" type="submit">Save</button>
                <button class="ui negative button">Cancel</button>
            </div> <!-- .actions -->
        </div> <!-- #profile-modal -->
    </div> <!-- .dimmer -->
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript" src="<c:url value="/assets/js/site.js" />"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/country.js" />"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/moment.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/assets/js/markdown.editor.min.js" />"></script>
    <script type="text/javascript">
        $('#edit-profile-trigger').click(function() {
            $('#profile-error').addClass('hide');

            $('#profile-modal').modal({
                closable  : false,
                onDeny    : function(){
                    // Clear all fields
                    $('input[type=password]', '#profile-modal').val('');
                },
                onApprove : function() {
                    editProfile();
                    return false;
                }
            }).modal('show');
        });
    </script>
    <script type="text/javascript">
        function editProfile() {
            var realName        = $('#real-name').val(),
                email           = $('#email').val(),
                country         = $('#country').val(),
                province        = $('#province').val(),
                city            = $('#city').val(),
                phone           = $('#phone').val(),
                website         = $('#website').val(),
                oldPassword     = $('#old-password').val(),
                newPassword     = $('#new-password').val(),
                confirmPassword = $('#confirm-password').val();

            return editProfileAction(realName, email, country, province, city, phone, 
                                     website, oldPassword, newPassword, confirmPassword);
        }
    </script>
    <script type="text/javascript">
        function editProfileAction(realName, email, country, province, city, phone, 
                                   website, oldPassword, newPassword, confirmPassword) {
            $('div.form', '#profile-modal').addClass('loading');
            
            var postData = {
                'realName': realName,
                'email': email,
                'country': country,
                'province': province,
                'city': city,
                'phone': phone,
                'website': website,
                'oldPassword': oldPassword,
                'newPassword': newPassword,
                'confirmPassword': confirmPassword,
            };
            $.ajax({
                type: 'POST',
                url: '<c:url value="/accounts/editProfile.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result) {
                    $('#profile-error').addClass('hide');
                    $('div.form', '#profile-modal').removeClass('loading');

                    processResult(result);
                }
            });
        }
    </script>
    <script type="text/javascript">
        function processResult(result) {
            if ( result['isSuccessful'] ) {
                $('#profile-modal').modal('hide');
            } else {
                var errorMessage  = '';

                if ( result['isRealNameEmpty'] ) {
                    errorMessage += 'You can\'t leave <strong>Your Name/Company Name</strong> empty.<br />';
                } else if ( !result['isRealNameLegal'] ) {
                    errorMessage += 'The length of <strong>Your Name/Company Name</strong> must not exceed 32 characters.<br />';
                }
                if ( result['isEmailEmpty'] ) {
                    errorMessage += 'You can\'t leave <strong>Email</strong> empty.<br />';
                } else if ( !result['isEmailLegal'] ) {
                    errorMessage += 'The <strong>Email</strong> seems invalid.<br />';
                } else if ( result['isEmailExists'] ) {
                    errorMessage += 'Someone already use that email.<br />';
                }
                if ( result['isCountryEmpty'] ) {
                    errorMessage += 'You can\'t leave <strong>Country</strong> empty.<br />';
                } else if ( !result['isCountryLegal'] ) {
                    errorMessage += 'The length of <strong>Country</strong> must not exceed 24 characters.<br />';
                }
                if ( result['isProvinceEmpty'] ) {
                    errorMessage += 'You can\'t leave <strong>State(Province)</strong> empty.<br />';
                } else if ( !result['isProvinceLegal'] ) {
                    errorMessage += 'The length of <strong>State(Province)</strong> must not exceed 24 characters.<br />';
                }
                if ( !result['isCityLegal'] ) {
                    errorMessage += 'The length of <strong>City</strong> must not exceed 24 characters.<br />';
                }
                if ( result['isPhoneEmpty'] ) {
                    errorMessage += 'You can\'t leave <strong>Phone</strong> empty.<br />';
                } else if ( !result['isPhoneLegal'] ) {
                    errorMessage += 'The <strong>Phone</strong> seems invalid.<br />';
                }
                if ( !result['isWebsiteLegal'] ) {
                    errorMessage += 'The <strong>Website</strong> seems invalid.<br />';
                }
                if ( !result['isOldPasswordCorrect'] ) {
                    errorMessage += 'The <strong>Old Password</strong> seems not correct.<br />';
                } 
                if ( !result['isNewPasswordLegal'] ) {
                    errorMessage += 'The length of <strong>New Password</strong> must between 6 and 16 characters.<br />';
                } 
                if ( !result['isPasswordMatched'] ) {
                    errorMessage += 'The new passwords don\'t match.<br />';
                }

                $('#profile-error').html(errorMessage);
                $('#profile-error').removeClass('hide');
            }
        }
    </script>
</body>
</html>