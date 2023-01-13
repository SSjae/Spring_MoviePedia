<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MoviePedia - ${loginUser.username }</title>
<link rel="stylesheet" href="${cp}/resources/css/font.css">
<link rel="stylesheet" href="${cp}/resources/css/import.css">
<link rel="stylesheet" href="${cp}/resources/css/user.css">
</head>
<body class="myPage">
	<c:import url="../import/header.jsp"></c:import>
	<section class="myPage-layer">
		<div class="backProfile" style="background: url(${cp}/resources/images/backProfile/backProfile_${ran}.jpg) no-repeat center; background-size: cover;">
		
		</div>
		<div class="myPage-main">
			<div class="profile">
				<img src="${cp}/resources/images/profile.png" alt="프로필" />
			</div>
			<div class="myPage-name">
				${loginUser.username}
			</div>
			<hr>
			<div class="myPage-review">
				<div class="text">평가<span>${myReview.size() }</span></div>
			</div>
			<hr>
			<div class="myPage-like">
				<div class="text">보고싶어요<span>${myLike.size() }</span></div>
			</div>
		</div>
	</section> 
	<c:import url="../import/footer.jsp">
		<c:param name="mtotal" value="${mtotal}"/>
		<c:param name="rtotal" value="${rtotal}"/>
	</c:import>
</body>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<script type="text/javascript" src="${cp}/resources/js/user.js"></script>
</html>