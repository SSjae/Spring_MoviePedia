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
<link rel="stylesheet" href="${cp}/resources/css/import.css">
<link rel="stylesheet" href="${cp}/resources/css/movie.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
</head>
<body class="searchResult">
	<c:if test="${loginUser == null}">
		<script>
			alert("로그인 후 접속 가능합니다.");
			location.href = "${cp}/user/login";
		</script>
	</c:if>
	<c:import url="../import/header.jsp"></c:import>
	<section class="search-layer">
		<div class="search-head">
			<div class="text">
				"${keyword}"의 검색결과
			</div>
		</div>
		<div class="search-main">
			<div class="searchResult">
				<c:forEach items="${movies}" var="movie" varStatus="status">
					<div class="main">
						<a href="${cp}/movie/movieInfo?moviecode=${movie.moviecode}">
							<img src="${movie.movieimg }" alt="img">
							<span class="title">${movie.movietitle }</span>
							<span class="year-nation">
								${movie.movierelease} ・ ${movie.movienation}
							</span>
							<span class="rate">평균 ★${movie.moviestar }</span>
						</a>
					</div>			
				</c:forEach>
			</div>
		</div>
	</section>
</body>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script> 
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="${cp}/resources/js/movie.js"></script>
<script type="text/javascript" src="${cp}/resources/js/import.js"></script>
</html>