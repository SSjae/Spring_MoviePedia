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
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css" />
</head>
<body class="movieInfo">
	<c:import url="../import/header.jsp"></c:import>
	<section class="info-layer">
		<div class="info-head">
			<div class="head">
				<div class="poster">
					<img src="${movie.movieimg}" alt="포스터">
				</div>
				<div class="text">
					<div class="text-title">${movie.movieKtitle }</div>
					<div class="text-subTitle">${movie.movieEtitle == "" ? "" : movie.movieEtitle}</div>
					<div class="text-etc">${movieRelease == "" ? "" : movieRelease += " ・ "}${movie.moviegenre } ・ ${movie.movienation }</div>
				</div>
			</div>
		</div>
		<div class="info-main">
		
		</div>
	</section> 
	<c:import url="../import/footer.jsp">
		<c:param name="mtotal" value="${mtotal}"/>
		<c:param name="rtotal" value="${rtotal}"/>
	</c:import>
</body>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<script src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script> 
<script type="text/javascript" src="${cp}/resources/js/movie.js"></script>
</html>