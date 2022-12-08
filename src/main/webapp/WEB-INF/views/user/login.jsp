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
			<div>
				<input id="useremail" type = "text" name = "useremail" placeholder="이메일" autofocus/>
				<div class="useremail-text">정확하지 않은 이메일입니다.</div>
			</div>
			<div>
				<input id="userpw" type = "password" name = "userpw" placeholder="비밀번호"/>
				<div class="userpw-text">비밀번호는 최소 8자리 이상이어야 합니다.</div>
			</div>
			<button id="form-bt" type="submit">로그인</button>
		</form>
		<a id="findPw">비밀번호를 잊어버리셨나요?</a>
		<div class="join-text">
			계정이 없으신가요? <a href="${cp}/user/join">회원가입</a>
		</div>
	</main>
</body>
<script type="text/javascript" charset="utf-8">
	sessionStorage.setItem("contextpath", "${pageContext.request.contextPath}");
</script>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<script type="text/javascript" src="${cp}/resources/js/user.js"></script>
</html>