<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<html>
<body>
	<header>
		<div class="head">
			<ul>
				<li class="head-img">
					<a href="${cp}/movie/main"><img src="${cp}/resources/images/logo.png" alt="로고" /></a>				
				</li>
				<li class="head-menu">
					<a href="${cp}/movie/main"><span>홈</span></a>
					<a href="${cp}/movie/genre"><span>장르</span></a>		
					<a href="${cp}/user/reviewLike"><span>평가 및 보고싶어요</span></a>
					<a href="${cp}/user/myPage"><span>마이페이지</span></a>
				</li>
				<li class="head-input">
					<form action="${cp}/movie/search" method="get">
						<input name="keyword" id="search" type="text" placeholder="영화, 인물을 검색해보세요."/>						
					</form>
				</li>
				<li class="head-span">
					<span>${loginUser.username}님 환영합니다.</span>	
				</li>
				<li class="head-div">
					<a href="${cp}/user/myPage"><img src="${cp}/resources/images/profile.png" alt="프로필" /></a>		
				</li>
			</ul>
		</div>
	</header>
</body>
</html>