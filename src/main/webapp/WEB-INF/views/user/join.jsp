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
	<main class="join-layer">
		<div class="logo">
			<img src="${cp}/resources/images/logo.png" alt="로고" />
		</div>
		<div class="join-text">
			회원가입
		</div>
		<form action = "${cp}/user/join" method = "post">
			<input type = "text" name = "userid" placeholder="이메일"/>
			<input type = "password" name = "userpw" placeholder="비밀번호"/>
			<input type = "text" name = "username" placeholder="이름"/>
			<input type = "text" name = "userphone" placeholder="휴대폰 번호"/>		
			<input type = "text" name = "userphone" placeholder="선호하는 장르"/>		
			<button type="submit">회원가입</button>
		</form>
	</main>
</body>
</html>