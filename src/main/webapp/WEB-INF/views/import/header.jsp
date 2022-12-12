<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${cp}/resources/css/font.css">
<link rel="stylesheet" href="${cp}/resources/css/import.css">
<body>
	<header>
		<div class="head">
			<ul>
				<li class="head-img">
					<a href="${cp}/movie/main"><img src="${cp}/resources/images/logo.png" alt="로고" /></a>				
				</li>
				<li class="head-span">
					<span>영화</span>				
				</li>
				<li class="head-input">
					<input type="text" placeholder="영화, 인물을 검색해보세요."/>				
				</li>
				<li class="head-div">
					<a href="#"><img src="${cp}/resources/images/profile.png" alt="프로필" /></a>		
				</li>
			</ul>
		</div>
	</header>
</body>
</html>