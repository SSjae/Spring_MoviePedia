<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>무비피디아</title>
<link rel="stylesheet" href="${cp}/resources/css/font.css">
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
			<div>
				<input id="useremail" class="join" type = "text" name = "useremail" placeholder="이메일" autofocus/>
				<div class="useremail-text">정확하지 않은 이메일입니다.</div>
				<div class="check-text">이미 가입된 이메일입니다.</div>
			</div>
			<div class="authdiv">
				<input id="authnum" type = "text" name = "authnum" placeholder="인증번호 확인"/>
				<button id="authbt">인증</button>
			</div>
			<div>
				<input id="userpw" type = "password" name = "userpw" placeholder="비밀번호"/>
				<div class="userpw-text">비밀번호는 최소 8자리 이상이어야 합니다.</div>
			</div>
			<div>
				<input id="username" type = "text" name = "username" placeholder="이름"/>
				<div class="username-text">이름은 최소 두 글자 이상이어야 합니다.</div>
			</div>
			<input type="hidden" id="prefergenre" name="prefergenre"/>
			<div class="checkbox-card">
				<fieldset>
					<legend>선호하는 장르를 선택해주세요</legend>
					<c:forEach items="${genres}" var="genre">
						<div>
							<input class="checkbox" type="checkbox" name="prefer" value="${genre}" id="${genre}">
							<label for="${genre}"><span></span>${genre}</label>
						</div>
					</c:forEach>
				</fieldset>
			</div>
			<button id="form-bt" class="join-bt" type="submit">회원가입</button>
		</form>
		<button id="auth" disabled>이메일 인증</button>
		<button onclick="location.href='${cp}/user/login'">로그인 화면 가기</button>
	</main>
</body>
<script type="text/javascript" charset="utf-8">
	sessionStorage.setItem("contextpath", "${pageContext.request.contextPath}");
</script>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<script type="text/javascript" src="${cp}/resources/js/user.js"></script>
</html>