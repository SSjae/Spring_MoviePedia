<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MoviePedia</title>
</head>
<body>
	<form action = "${cp}/user/login" method = "post">
		아이디 : <input type = "text" name = "userid"/><br>
		비밀번호 : <input type = "password" name = "userpw"/><br>
	    <input type = "submit" value ="로그인"/>
	</form>
</body>
</html>