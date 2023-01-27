<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>무비피디아-관리자</title>
<link rel="stylesheet" href="${cp}/resources/css/user.css">
<style>
	
</style>
</head>
<body>
	<header class="head">
		<div class="head-logo">
			<img src="${cp}/resources/images/logo.png" alt="로고" />
		</div>
	</header>
	<main class="admin-layer">
		<div class="admin-text">
			관리자
		</div>
		<div class="text">오늘 날짜 : ${today}</div>
		<div class="text">최신화 날짜 : ${recentDate}</div>
		<button id="recent" onclick="location.href='${cp}/admin/recent'">영화 최신화 하기</button>
		<button onclick="location.href='${cp}/user/login'">로그인 화면 가기</button>
	</main>
	<div id="loading" style="margin-left: 0px;">
	    <img src="${cp}/resources/images/loadingbar.gif">
	    <p>최신화 중입니다.. 잠시 기다려주세요..</p>
	</div>
</body>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<script type="text/javascript" src="${cp}/resources/js/user.js"></script>
</html>
