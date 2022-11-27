<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error Page</title>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="${cp}/resources/css/error_css.css">
</head>
<body>
	<div class="page-404">
	    <div class="outer">
	        <div class="middle">
	            <div class="inner">
	                <!--BEGIN CONTENT-->
	                <div class="inner-circle"><i class="fa fa-home"></i><span style="font-size: 6em; padding-top: 87px;">Error</span></div>
	                <span class="inner-status">Oops! Error</span>
	                <span class="inner-detail">
	                	${exception.message}<br>
	                    <a href="javascript:history.go(-1)" class="btn btn-info mtl"><i class="fa fa-home"></i>&nbsp;
	                        Return Back
	                    </a> 
	                </span>
	            </div>
	        </div>
	    </div>
	</div>
</body>
</html>