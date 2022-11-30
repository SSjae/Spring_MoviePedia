<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MoviePedia-admin</title>
</head>
<body>
	현재 날짜 : ${today}
	<button onclick="location.href='${cp}/admin/recent'">영화 최신화 하기</button>
</body>
</html>