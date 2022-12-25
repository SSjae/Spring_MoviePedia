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
<link rel="stylesheet" href="${cp}/resources/css/import.css">
<link rel="stylesheet" href="${cp}/resources/css/movie.css">
</head>
<body class="movieAll">
	<c:import url="../import/header.jsp"></c:import>
	<section class="all-layer">
		<div class="all-head">
			<div class="text">
				${title }<span>${movies.size() }</span>
			</div>
		</div>
		<div class="all-main">
			<div class="allMovie">
				<c:forEach items="${movies}" var="movie">
					<div class="allMovie-main">
						<a href="${cp}/movie/movieInfo?moviecode=${movie.moviecode}">
							<img src="${movie.movieimg }" alt="img">
							<span class="title">${movie.movieKtitle }</span>
							<span class="rate">평균 ★${movie.moviestar }</span>
						</a>
					</div>			
				</c:forEach>
			</div>
		</div>
	</section> 
	<c:import url="../import/footer.jsp">
		<c:param name="mtotal" value="${mtotal}"/>
		<c:param name="rtotal" value="${rtotal}"/>
	</c:import>
</body>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script> 
<script type="text/javascript" src="${cp}/resources/js/movie.js"></script>
</html>