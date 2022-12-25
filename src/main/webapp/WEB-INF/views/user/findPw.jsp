<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MoviePedia</title>
<link rel="stylesheet" href="${cp}/resources/css/font.css">
<link rel="stylesheet" href="${cp}/resources/css/user.css">
</head>
<body id="findPw-body">
	<div class="findPw-head">
		<h2>비밀번호 재설정</h2>
	</div>
	<main class="findPw-layer">
		<div class="main-text">
			<div class="text-h">비밀번호를 잊으셨나요?</div>
			<div class="text-m">가입했던 이메일을 적어주세요.</div>
			<div class="text-m">이메일 인증 후 비밀번호 재설정 가능합니다.</div>
		</div>
		<form>
			<div>
				<input id="useremail" class="findPw" type = "text" name="useremail" placeholder="이메일" autofocus/>
				<div class="useremail-text">정확하지 않은 이메일입니다.</div>
				<div class="check-text">가입되지 않은 이메일입니다.</div>
			</div>
			<div class="authdiv">
				<input id="authnum" type = "text" name = "authnum" placeholder="인증번호 확인"/>
				<button id="findPw-authbt">인증</button>
			</div>
			<div class="findPw-pw">
				<input id="userpw" type = "password" name = "userpw" placeholder="새 비밀번호"/>
				<div class="userpw-text">비밀번호는 최소 8자리 이상이어야 합니다.</div>
				<input id="userpw-re" type = "password" name = "userpw-re" placeholder="새 비밀번호 확인"/>
				<div class="userpw-re-text">비밀번호가 동일하지 않습니다.</div>
			</div>
			<button class="findPw-auth">이메일 인증</button>
			<button class="findPw-bt">비밀번호 재설정</button>
		</form>
	</main>
</body>
<script type="text/javascript" charset="utf-8">
	sessionStorage.setItem("contextpath", "${pageContext.request.contextPath}");
</script>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<script type="text/javascript" src="${cp}/resources/js/user.js"></script>
</html>