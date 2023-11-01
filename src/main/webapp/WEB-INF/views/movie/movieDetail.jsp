<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>무비피디아 - ${movie.movietitle }</title>
<link rel="stylesheet" href="${cp}/resources/css/font.css">
<link rel="stylesheet" href="${cp}/resources/css/import.css">
<link rel="stylesheet" href="${cp}/resources/css/movie.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
</head>
<body class="movieDetail">
	<c:if test="${loginUser == null}">
		<script>
			alert("로그인 후 접속 가능합니다.");
			location.href = "${cp}/user/login";
		</script>
	</c:if>
	<c:import url="../import/header.jsp"></c:import>
	<section class="detail-layer">
		<div class="detail-header">
			<img onclick="history.go(-1);" src="${cp}/resources/images/backarrow.svg" alt="뒤로가기">
			<div class="text">기본 정보</div>
		</div>
		<div class="detail-main">
			<div>
				<span class="title">제목</span><span class="content">${movie.movietitle }</span>
			</div>
			<div>
				<span class="title">제작 연도</span><span class="content">${movie.movierelease }</span>
			</div>
			<div>
				<span class="title">국가</span><span class="content">${movie.movienation }</span>
			</div>
			<div>
				<span class="title">장르</span><span class="content">${movie.moviegenre}</span>
			</div>
			<div>
				<span class="title">상영 시간</span><span class="content">${movie.movietime }</span>
			</div>
			<div>
				<span class="title">연령 등급</span><span class="content">${movie.moviegrade }</span>
			</div>
			<div class="movieSummary">
				<span class="title">내용</span>
				<div class="content">
					<div>${movie.moviesummary }</div>
				</div>
			</div>
		</div>
	</section>
</body>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="${cp}/resources/js/movie.js"></script>
<script type="text/javascript" src="${cp}/resources/js/import.js"></script>
</html>