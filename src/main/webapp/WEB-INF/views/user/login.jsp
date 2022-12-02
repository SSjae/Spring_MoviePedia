<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MoviePedia</title>
<link rel="stylesheet" href="${cp}/resources/css/user.css">
</head>
<body>
	<header class="head">
		<div class="head-logo">
			<img src="${cp}/resources/images/logo.png" alt="로고" />
		</div>
	</header>
	<main class="login-layer">
		<div class="logo">
			<img src="${cp}/resources/images/logo.png" alt="로고" />
		</div>
		<div class="login-text">
			로그인
		</div>
		<form action = "${cp}/user/login" method = "post">
			<input type = "text" name = "userid" placeholder="이메일"/>
			<input type = "password" name = "userpw" placeholder="비밀번호"/>
			<button type="submit">로그인</button>
		</form>
		<a href="#">비밀번호를 잊어버리셨나요?</a>
		<div class="join-text">
			계정이 없으신가요? <a href="${cp}/user/join">회원가입</a>
		</div>
	</main>
</body>
</html>