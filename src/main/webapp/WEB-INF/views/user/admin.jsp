<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MoviePedia-admin</title>
<style>
	#loading {
  		width: 100%;
  		height: 100%;
  		top: 0;
  		left: 0;
  		position: fixed;
  		display: block;
  		opacity: 0.6;
  		background: #e4e4e4;
  		z-index: 99;
  		text-align: center;
	}
  	#loading > img {
  		position: absolute;
  		top: 44%;
  		left: 48%;
  		z-index: 100;
  	}
   	#loading > p {
    	position: absolute;
    	top: 50%;
    	left: 42%;
    	z-index: 101;
    }
</style>
</head>
<body>
	오늘 날짜 : ${today}
	최신화 날짜 : ${recentDate}
	<button id="recent" onclick="location.href='${cp}/admin/recent'">영화 최신화 하기</button>
	<button onclick="location.href='${cp}/user/login'">로그인 화면 가기</button>
	
	<div id="loading" style="margin-left: 0px;">
	    <img src="${cp}/resources/images/loadingbar.gif">
	    <p>최신화 중입니다.. 잠시 기다려주세요..</p>
	</div>
</body>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<script>
	// 로딩바 보여주기
	$(document).ready(function() {
		$('#loading').hide();
		$('#recent').click(function(){
	    	$('#loading').show();
	    	return true;
	    });
	});
</script>
</html>
