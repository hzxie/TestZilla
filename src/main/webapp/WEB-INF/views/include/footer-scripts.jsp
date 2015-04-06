<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!-- JavaScript for Localization -->
    <script type="text/javascript">
        $('#languages').dropdown({
            onChange: function(value, text, $selectedItem) {
                var postData = {
                    language: value
                };

                $.ajax({
                    type: 'GET',
                    url: '<c:url value="/localization" />',
                    data: postData,
                    dataType: 'JSON',
                    success: function(result) {
                        if ( result['isSuccessful'] ) {
                        	location.reload();
                        }
                    }
                });
            }
        });
    </script>
    <!-- Google Analytics Code -->
    <script type="text/javascript">
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
        ga('create', 'UA-56635442-3', 'auto');
        ga('send', 'pageview');
    </script>